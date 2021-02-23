package com.mate;

public class Counter {
    private int count;

    public Counter() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count = count + 1;
    }
}
