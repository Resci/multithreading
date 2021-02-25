package com.mate;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SumOfListExecutorServiceImplTest extends AbstractTest {
    private static SumOfListExecutorServiceImpl executorService;

    @BeforeAll
    public static void beforeAll() {
        executorService = new SumOfListExecutorServiceImpl(THREAD_NUMBER);
    }

    @Test
    public void sumOfList_Ok() {
        List<Integer> list = new ListSupplier(LIST_SIZE, MAX_NUMBER).getList();
        long expected = list.stream().mapToInt(i -> i).sum();
        long actual = executorService.sumOfList(list);
        Assertions.assertEquals(expected, actual, "Results must be equals");
    }
}
