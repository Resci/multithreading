package com.mate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import org.apache.commons.collections4.ListUtils;
import org.apache.log4j.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static final int INCREMENT_TO = 100;
    private static final int LIST_SIZE = 1_000_000;
    private static final int MAX_NUMBER = 10;
    private static final int THREAD_NUMBER = 6;

    public static void main(String[] args) throws InterruptedException {
        threadRace();
        sumOfMillionElements();
    }

    private static void sumOfMillionElements() {
        Util util = new Util(LIST_SIZE, MAX_NUMBER);
        List<Integer> targetList = util.getList();
        logger.info("Start calculating using ExecutorService");
        long executorServiceSum = sumOfListExecutorService(targetList);
        logger.info("Sum of list using ExecutorService: " + executorServiceSum);
        logger.info("Start calculating using ForkJoinPool");
        long forkJoinSum = sumOfListForkJoinPool(targetList);
        logger.info("Sum of list using ForkJoinPool: " + forkJoinSum);
        logger.info(executorServiceSum == forkJoinSum ? "Results equals" : "Results not equals");
    }

    public static long sumOfListForkJoinPool(List<Integer> targetList) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_NUMBER);
        ForkJoinTask<Long> task = new MyTask(targetList);
        return forkJoinPool.invoke(task);
    }

    public static long sumOfListExecutorService(List<Integer> targetList) {
        List<List<Integer>> separatedList =
                ListUtils.partition(targetList, (LIST_SIZE / THREAD_NUMBER) + 1);
        List<Callable<Long>> callables = new ArrayList<>();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Callable<Long> sumOfList = new MyCallable(separatedList.get(i));
            callables.add(sumOfList);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        long executorServiceSum = 0;
        try {
            List<Future<Long>> futures = executorService.invokeAll(callables);
            for (Future<Long> future: futures) {
                executorServiceSum += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Thread interrupted", e);
        } finally {
            executorService.shutdown();
        }
        return executorServiceSum;
    }

    private static void threadRace() {
        Counter counter = new Counter();
        Thread myRunnable = new Thread(new MyRunnable(counter, INCREMENT_TO));
        logger.info("Starting MyRunnable");
        myRunnable.start();
        MyThread myThread = new MyThread(counter, INCREMENT_TO);
        logger.info("Starting MyThread");
        myThread.start();
    }
}
