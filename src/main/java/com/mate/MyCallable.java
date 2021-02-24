package com.mate;

import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

public class MyCallable implements Callable<Integer> {
    private static final Logger logger = Logger.getLogger(App.class);
    private final List<Integer> list;

    public MyCallable(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        long time = System.currentTimeMillis();
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        logger.info(Thread.currentThread().getName() + " time: " +(System.currentTimeMillis() - time));
        return sum;
    }
}
