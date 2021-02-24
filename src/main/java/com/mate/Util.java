package com.mate;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private final List<Integer> list;

    public Util(int listSize, int maxNumber) {
        SecureRandom random = new SecureRandom();
        list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(random.nextInt(maxNumber));
        }
    }

    public List<Integer> getList() {
        return list;
    }
}
