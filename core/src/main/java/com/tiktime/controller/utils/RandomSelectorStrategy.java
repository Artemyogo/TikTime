package com.tiktime.controller.utils;

import java.util.List;
import java.util.Random;

public class RandomSelectorStrategy implements MapSelectorStrategy {
    private final Random random = new Random();
    @Override
    public String selectMapPath(List<String> maps) {
        return maps.get(random.nextInt(maps.size()));
    }
}
