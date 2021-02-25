package com.mate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 50000;
    private List<Integer> list;

    public MyTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Long compute() {
        if (list.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks(list))
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return processing();
    }

    private Collection<MyTask> createSubtasks(List<Integer> list) {
        List<MyTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new MyTask(list.subList(0, list.size() / 2)));
        dividedTasks.add(new MyTask(list.subList(list.size() / 2, list.size())));
        return dividedTasks;
    }

    private Long processing() {
        return list
                .stream()
                .mapToLong(a -> a)
                .sum();
    }
}
