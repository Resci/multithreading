package com.mate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.collections4.ListUtils;

public class SumOfListExecutorServiceImpl {
    private final int threadNumber;

    public SumOfListExecutorServiceImpl(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public long sumOfList(List<Integer> list) {
        List<List<Integer>> separatedList =
                ListUtils.partition(list, (list.size() / threadNumber) + 1);
        List<Callable<Long>> callables = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            Callable<Long> sumOfList = new SumOfListCallable(separatedList.get(i));
            callables.add(sumOfList);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
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
}
