package com.tiktime.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tiktime.controller.CollisionController;
import com.tiktime.model.consts.BodyFactory;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EnemyModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;

import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.GameConfig.FloorConfig;
import com.tiktime.model.consts.GameConfig.WallConfig;
import com.tiktime.model.consts.GameConfig.EntityConfig;
import com.tiktime.model.enums.Category;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class WorldModel {
    private final World world;
    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

    private final TiledMap map;
    private PlayerModel player;
    private Array<EnemyModel> enemies;


    public void update(float delta){
        world.step(delta, velocityIterations, positionIterations);
    }

    public WorldModel(TiledMap map, CollisionController collisionController) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        this.player = new PlayerModel(world, properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM);
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");

        int width = map.getProperties().get("width", Integer.class);
        int height = map.getProperties().get("height", Integer.class);

        Gdx.app.log("WorldModel", "width: " + width + ", height: " + height);

        for (int x = 0; x < wallLayer.getWidth(); x++) {
            for (int y = 0; y < wallLayer.getHeight(); y++) {
                if (wallLayer.getCell(x, y) == null) continue;
                BodyFactory.createWallBody(world, x, y);
            }
        }
        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        for (int x = 0; x < doorLayer.getWidth(); x++) {
            for (int y = 0; y < doorLayer.getHeight(); y++) {
                if (doorLayer.getCell(x, y) == null) continue;
                BodyFactory.createDoorBody(world, x, y);
            }
        }

        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        if(dynamiteLayer != null) {
            for (int x = 0; x < dynamiteLayer.getWidth(); x++) {
                for (int y = 0; y < dynamiteLayer.getHeight(); y++) {
                    if (dynamiteLayer.getCell(x, y) == null) continue;
                    BodyFactory.createDoorBody(world, x, y);
                }
            }

        }
        world.setContactListener(collisionController);
    }



    public EntityData getPlayerData(){
        return player.getData();
//        return new EntityData();
    }

    public Vector2 getPlayerPosition(){
        return player.getPosition();
//        return new Vector2(1, 1);
    }

    public void updateMovementDirection(Vector2 movementDirection){
        player.move(movementDirection);
    }

    public World getWorld() {
        return world;
    }
}
