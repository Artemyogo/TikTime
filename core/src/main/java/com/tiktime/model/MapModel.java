package com.tiktime.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.entityfactories.FixtureFactory;

import java.util.ArrayList;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class MapModel implements Disposable {
    private final TiledMap map;
    // TODO: magic constants
    private static int counter = 0;
    public MapModel(TiledMap map) {
        counter++;
        this.map = map;
    }

    public int getCounter() {
        return counter;
    }

    Vector2 getPlayerSpawnPosition() {
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        return new Vector2(properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM);
    }

    ArrayList<Vector2> getEnemiesSpawnPositions() {
        if (map.getLayers().get("enemies") == null) {
            return new ArrayList<>(0);
//            throw new NullPointerException("Map does not contain enemies");
        }

        ArrayList<Vector2> enemiesSpawnPositions = new ArrayList<>();
        for (MapObject object : map.getLayers().get("enemies").getObjects()) {

            Vector2 enemyPosition =
                new Vector2(object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM);

            enemiesSpawnPositions.add(enemyPosition);
        }

        return enemiesSpawnPositions;
    }

    ArrayList<Vector2> getAnimanEnemiesSpawnPositions() {
        if (map.getLayers().get("animanEnemies") == null) {
            return new ArrayList<>(0);
        }
        ArrayList<Vector2> enemiesSpawnPositions = new ArrayList<>();
        for (MapObject object : map.getLayers().get("enemies").getObjects()) {

            Vector2 enemyPosition =
                new Vector2(object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM);

            enemiesSpawnPositions.add(enemyPosition);
        }

        return enemiesSpawnPositions;


    }

    public void createWalls(World world) {
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        BodyFactory.createBodiesOnLayer(world, wallLayer, Category.WALL, BodyDef.BodyType.StaticBody);
    }

    public void createDoors(World world) {
        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        BodyFactory.createBodiesOnLayer(world, doorLayer, Category.DOOR, BodyDef.BodyType.StaticBody);
    }

    public void createDynamites(World world) {
        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        BodyFactory.createBodiesOnLayer(world, dynamiteLayer, Category.DYNAMITE, BodyDef.BodyType.StaticBody);
    }

    public void createAll(World world) {
        createWalls(world);
        createDoors(world);
        createDynamites(world);
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
