package com.tiktime.controller.utils;

import java.util.List;
import java.util.Random;

public class RandomSelectorStrategy implements MapSelectorStrategy {
    private Random random = new Random();
    public String selectMapPath(List<String> maps) {
        return maps.get(0);
//        return maps.get(random.nextInt(maps.size()));
    }
}
