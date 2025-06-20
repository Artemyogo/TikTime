package com.tiktime.model.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.MagicConstants;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.entityfactories.EntityFactory;

import java.util.ArrayList;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class MapModel implements Disposable {
    private final TiledMap map;
    private static int counter = 0;
    public MapModel(TiledMap map) {
        counter++;
        this.map = map;
    }

    public static int getCounter() {
        return counter;
    }

    public static void resetCounter() {
        counter = 0;
    }

    Vector2 getPlayerSpawnPosition() {
        MapProperties properties = map.getLayers().get(MagicConstants.OBJECTS_LAYER_NAME).getObjects().get(MagicConstants.PLAYER_SPAWN_NAME).getProperties();
        return new Vector2(properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM);
    }

    ArrayList<Vector2> getEnemiesSpawnPositions() {
        if (map.getLayers().get(MagicConstants.ENEMIES_LAYER_NAME) == null) {
            return new ArrayList<>(0);
//            throw new NullPointerException("Map does not contain enemies");
        }

        ArrayList<Vector2> enemiesSpawnPositions = new ArrayList<>();
        for (MapObject object : map.getLayers().get(MagicConstants.ENEMIES_LAYER_NAME).getObjects()) {

            Vector2 enemyPosition =
                new Vector2(object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM);

            enemiesSpawnPositions.add(enemyPosition);
        }

        return enemiesSpawnPositions;
    }

    ArrayList<Vector2> getAnimanEnemiesSpawnPositions() {
        if (map.getLayers().get(MagicConstants.ANIMAN_ENEMIES_LAYER_NAME) == null) {
            return new ArrayList<>(0);
        }
        ArrayList<Vector2> enemiesSpawnPositions = new ArrayList<>();
        for (MapObject object : map.getLayers().get(MagicConstants.ANIMAN_ENEMIES_LAYER_NAME).getObjects()) {

            Vector2 enemyPosition =
                new Vector2(object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM);

            enemiesSpawnPositions.add(enemyPosition);
        }

        return enemiesSpawnPositions;
    }

    ArrayList<Vector2> getBossEnemiesSpawnPositions() {
        if (map.getLayers().get(MagicConstants.BOSS_ENEMIES_LAYER_NAME) == null) {
            return new ArrayList<>(0);
        }
        ArrayList<Vector2> enemiesSpawnPositions = new ArrayList<>();
        for (MapObject object : map.getLayers().get(MagicConstants.BOSS_ENEMIES_LAYER_NAME).getObjects()) {

            Vector2 enemyPosition =
                new Vector2(object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM);

            enemiesSpawnPositions.add(enemyPosition);
        }

        return enemiesSpawnPositions;
    }

    public void createWalls(World world) {
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get(MagicConstants.WALLS_LAYER_NAME);
        BodyFactory.createBodiesOnLayer(world, wallLayer, Category.WALL, BodyDef.BodyType.StaticBody);
    }

    public void createDoors(World world) {
        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get(MagicConstants.DOORS_LAYER_NAME);
        BodyFactory.createBodiesOnLayer(world, doorLayer, Category.DOOR, BodyDef.BodyType.StaticBody);
    }

    public void createDynamites(World world, BodyManager bodyManager) {
        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get(MagicConstants.DYNAMITES_LAYER_NAME);
        EntityFactory.createDynamiteModels(world, bodyManager, dynamiteLayer);
//        BodyFactory.createBodiesOnLayer(world, dynamiteLayer, Category.DYNAMITE, BodyDef.BodyType.StaticBody);
    }

    public void createHealthPotions(World world, BodyManager bodyManager) {
        TiledMapTileLayer healthPotionLayer = (TiledMapTileLayer) map.getLayers().get(MagicConstants.HEALTH_POTIONS_LAYER_NAME);
        EntityFactory.createHealthPotionModels(world, bodyManager, healthPotionLayer);
    }

    public void createAll(World world, BodyManager bodyManager) {
        createWalls(world);
        createDoors(world);
        createDynamites(world, bodyManager);
        createHealthPotions(world, bodyManager);
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
