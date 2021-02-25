package com.mate;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AppTest {
    private static final int LIST_SIZE = 1_000_000;
    private static final int MAX_NUMBER = 10;
    private static List<Integer> list;

    @BeforeAll
    static void setUp() {
        list = new Util(LIST_SIZE, MAX_NUMBER).getList();
    }

    @Test
    void sumOfListForkJoinPool_Ok() {
        long expected = list.stream().mapToInt(i -> i).sum();
        long actual = App.sumOfListForkJoinPool(list);
        Assertions.assertEquals(expected, actual, "Results must be equals");
    }

    @Test
    void sumOfListExecutorService_Ok() {
        long expected = list.stream().mapToInt(i -> i).sum();
        long actual = App.sumOfListExecutorService(list);
        Assertions.assertEquals(expected, actual, "Results must be equals");
    }
}