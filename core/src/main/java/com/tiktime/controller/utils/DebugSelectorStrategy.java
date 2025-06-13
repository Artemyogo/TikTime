package com.tiktime.controller.utils;

import com.badlogic.gdx.Gdx;

import java.util.List;
import java.util.Random;

public class DebugSelectorStrategy implements MapSelectorStrategy {
    @Override
    public String selectMapPath(List<String> maps) {
        return maps.get(7);
    }
}
