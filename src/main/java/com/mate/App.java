package com.mate;

import org.apache.log4j.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static final int INCREMENT_TO = 100;

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread myRunnable = new Thread(new MyRunnable(counter, INCREMENT_TO));
        MyThread myThread = new MyThread(counter, INCREMENT_TO);
        logger.info("Starting MyRunnable");
        myRunnable.start();
        logger.info("Starting MyThread");
        myThread.start();
    }
}
