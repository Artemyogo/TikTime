package com.tiktime.controller.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MapSelector {
    private List<String> maps;
    public MapSelector(){
        try {
            FileHandle dirHandle = Gdx.files.internal("maps/");
            maps = new ArrayList<>();
            for (FileHandle file : dirHandle.list()) {
                if (!file.isDirectory() && file.name().toLowerCase().endsWith(".tmx")) {
                    maps.add(String.valueOf(file.path()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("MapSelector cannot load maps " + e.getMessage());
        }
    }
    public TiledMap getMap() {
//        Random rand = new Random();
        String path = maps.get(0);
        return new TmxMapLoader().load(path);
    }
}
