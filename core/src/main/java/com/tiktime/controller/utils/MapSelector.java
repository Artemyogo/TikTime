package com.tiktime.controller.utils;

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
    private List<TiledMap> maps;
    public MapSelector(){
        try {
            List<Path> paths = Files.walk(Paths.get("maps/")).filter(Files::isRegularFile).filter(path -> {
                String fileName = path.getFileName().toString().toLowerCase();
                return fileName.endsWith(".tmx");
            }).collect(Collectors.toList());
            maps = new ArrayList<>();
            for (Path path : paths) {
                maps.add(new TmxMapLoader().load(String.valueOf(path)));
            }
        } catch (Exception e) {
            throw new RuntimeException("MapSelector cannot load maps");
        }
    }
    public TiledMap getMap() {
        Random rand = new Random();
        return maps.get(rand.nextInt(maps.size()));
    }
}
