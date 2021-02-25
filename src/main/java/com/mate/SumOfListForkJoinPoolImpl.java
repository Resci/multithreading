package com.mate;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class SumOfListForkJoinPoolImpl {
    private final ForkJoinPool forkJoinPool;

    public SumOfListForkJoinPoolImpl(int threadNumber) {
        forkJoinPool = new ForkJoinPool(threadNumber);
    }

    public long sumOfList(List<Integer> list) {
        ForkJoinTask<Long> task = new SumOfListRecursiveTask(list);
        return forkJoinPool.invoke(task);
    }
}
