package com.mate;

import java.util.List;
import java.util.concurrent.Callable;

public class SumOfListCallable implements Callable<Long> {
    private final List<Integer> list;

    public SumOfListCallable(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Long call() {
        long sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }
}
