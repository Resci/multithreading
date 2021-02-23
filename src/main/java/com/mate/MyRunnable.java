package com.mate;

import org.apache.log4j.Logger;

public class MyRunnable implements Runnable {
    private static final Logger logger = Logger.getLogger(MyRunnable.class);
    private final Counter counter;
    private final int incrementTo;

    public MyRunnable(Counter counter, int incrementTo) {
        this.counter = counter;
        this.incrementTo = incrementTo;
    }

    @Override
    public void run() {
        logger.info("run() start execution");
        while (counter.getCount() != incrementTo) {
            counter.increment();
            logger.info("Counter incremented. Now value is " + counter.getCount());
        }
    }
}
