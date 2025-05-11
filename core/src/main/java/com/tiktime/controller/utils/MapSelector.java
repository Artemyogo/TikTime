package com.tiktime.controller.utils;

import com.badlogic.gdx.Gdx;
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
//        Path mapsDir = Paths.get(Gdx.files.internal("maps").toString());
//        if (!Files.exists(mapsDir)) {
//            throw new RuntimeException("Папка 'maps' не существует!");
//        }

//        Gdx.app.log("MapSelector", Gdx.files.internal("maps/").toString());
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
            throw new RuntimeException("MapSelector cannot load maps   " + e.getMessage());
        }
    }
    public TiledMap getMap() {
        Random rand = new Random();
        return maps.get(rand.nextInt(maps.size()));
    }
}
