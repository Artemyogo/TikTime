package com.tiktime.controller.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MapSelector {
    private List<String> maps;
    public MapSelector() {
        try {
            maps = new ArrayList<>();
            Array<FileHandle> allFiles = new Array<>();
            getAllFilesInDirectory("maps/", allFiles);

            for (FileHandle file : allFiles) {
                if (file.name().toLowerCase().endsWith(".tmx")) {
                    maps.add(file.path());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("MapSelector cannot load maps: " + e.getMessage());
        }
    }

    private void getAllFilesInDirectory(String path, Array<FileHandle> result) {
        FileHandle dir = Gdx.files.internal(path);

        if (!dir.exists()) {
            Gdx.app.error("MapSelector", "Directory not found: " + path);
            return;
        }

        if (dir.isDirectory()) {
            for (FileHandle file : dir.list()) {
                if (file.isDirectory()) {
                    getAllFilesInDirectory(file.path(), result);
                } else {
                    result.add(file);
                }
            }
        } else {
            try {
                String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                JarFile jar = new JarFile(jarPath);

                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith(path) && !entry.isDirectory() && entry.getName().toLowerCase().endsWith(".tmx")) {
                        result.add(Gdx.files.internal(entry.getName()));
                    }
                }
                jar.close();
            } catch (Exception e) {
                Gdx.app.error("MapSelector", "Error reading JAR: " + e.getMessage());
            }
        }
    }
    public TiledMap getMap(MapSelectorStrategy strategy) {
        return new TmxMapLoader().load(strategy.selectMapPath(maps));
    }
}
