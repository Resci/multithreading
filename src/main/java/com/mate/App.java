package com.mate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.log4j.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static final int INCREMENT_TO = 100;
    private static final int LIST_SIZE = 1_000_000;
    private static final int MAX_NUMBER = 10;
    private static final int THREAD_NUMBER = 6;


    public static void main(String[] args) throws InterruptedException {
        //threadRace();
        logger.info("Number of threads: " + THREAD_NUMBER);
        sumOfMillionElements();
    }

    private static void sumOfMillionElements() {
        long time;
        Util util = new Util(LIST_SIZE, MAX_NUMBER);
        List<List<Integer>> separatedList = ListUtils.partition(util.getList(), (LIST_SIZE / THREAD_NUMBER) + 1);
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Callable<Integer> sumOfList = new MyCallable(separatedList.get(i));
            callables.add(sumOfList);
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        int listSum = 0;
        try {
            time = System.currentTimeMillis();
            List<Future<Integer>> futures = executorService.invokeAll(callables);
            for (Future<Integer> future: futures) {
                listSum += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Thread interrupted", e);
        }finally {
            executorService.shutdown();
        }

        logger.info("Sum with multithreading time: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        int sum = util.getList().stream().mapToInt(i -> i).sum();
        sum ++;
        logger.info("Sum with stream time: " + (System.currentTimeMillis() - time));
        sum = 0;
        List<Integer> list = util.getList();
        time = System.currentTimeMillis();
        for (Integer i: list) {
            sum += i;
        }
        logger.info("Sum with cycle time: " + (System.currentTimeMillis() - time));
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
