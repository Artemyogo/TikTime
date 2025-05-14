package com.tiktime.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tiktime.controller.CollisionController;
import com.tiktime.model.consts.*;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EnemyModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;

import com.tiktime.model.consts.GameConfig.FloorConfig;
import com.tiktime.model.consts.GameConfig.WallConfig;
import com.tiktime.model.consts.GameConfig.EntityConfig;
import com.tiktime.model.enums.Category;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.gameobjects.RusherEnemyModel;
import com.tiktime.model.raycasts.PlayerChaseRaycast;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class WorldModel {
    private final World world;
    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

    private final TiledMap map;
    private PlayerModel player;
    private Array<EnemyModel> enemies = new Array<>();


    public void update(float delta){
        world.step(delta, velocityIterations, positionIterations);
        for(EnemyModel enemy : enemies){
            PlayerChaseRaycast callback = new PlayerChaseRaycast();
            world.rayCast(callback, enemy.getPosition(), player.getBody().getPosition());
            if(callback.canSeePlayer()){
                enemy.move(new Vector2(getPlayerPosition()).sub(enemy.getPosition()).nor());
            }
        }
    }

    public WorldModel(TiledMap map, CollisionController collisionController) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        this.player = new PlayerModel(world, properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM);

//        Gdx.app.log("WorldModel", (map.getLayers().get("enemies") == null ? "null" : map.getLayers().get("enemies").toString()));
        if (map.getLayers().get("enemies") != null) {
//            Gdx.app.log("WorldModel",
//                String.valueOf(map.getLayers().get("enemies").getObjects().get(35).getProperties().get("x", Float.class)));
            for (MapObject object : map.getLayers().get("enemies").getObjects()) {
//                Gdx.app.log("WorldModel", object.getProperties().get("x", Float.class) + " " +
//                    object.getProperties().get("y", Float.class));
                enemies.add(new RusherEnemyModel(world, object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM));
            }
        }

        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        BodyFactory.createBodies(world, wallLayer, FixtureFactory.getWallFixture(), BodyDef.BodyType.StaticBody);

//        int width = map.getProperties().get("width", Integer.class);
//        int height = map.getProperties().get("height", Integer.class);
//
//        Gdx.app.log("WorldModel", "width: " + width + ", height: " + height);

        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        BodyFactory.createBodies(world, doorLayer, FixtureFactory.getDoorFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        BodyFactory.createBodies(world, dynamiteLayer, FixtureFactory.getDynamiteFixture(), BodyDef.BodyType.StaticBody);
        world.setContactListener(collisionController);
    }

    public WorldModel(TiledMap map, CollisionController collisionController, EntityData playerData) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        this.player = new PlayerModel(world, properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM,
            playerData);

//        Gdx.app.log("WorldModel", (map.getLayers().get("enemies") == null ? "null" : map.getLayers().get("enemies").toString()));
        if (map.getLayers().get("enemies") != null) {
//            Gdx.app.log("WorldModel",
//                String.valueOf(map.getLayers().get("enemies").getObjects().get(35).getProperties().get("x", Float.class)));
            for (MapObject object : map.getLayers().get("enemies").getObjects()) {
//                Gdx.app.log("WorldModel", object.getProperties().get("x", Float.class) + " " +
//                    object.getProperties().get("y", Float.class));
                enemies.add(new RusherEnemyModel(world, object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM));
            }
        }

        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        BodyFactory.createBodies(world, wallLayer, FixtureFactory.getWallFixture(), BodyDef.BodyType.StaticBody);

        int width = map.getProperties().get("width", Integer.class);
        int height = map.getProperties().get("height", Integer.class);

//        Gdx.app.log("WorldModel", "width: " + width + ", height: " + height);

        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        BodyFactory.createBodies(world, doorLayer, FixtureFactory.getDoorFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        BodyFactory.createBodies(world, dynamiteLayer, FixtureFactory.getDynamiteFixture(), BodyDef.BodyType.StaticBody);
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

    public Array<EnemyModel> getEnemies(){
        return new Array<>(enemies);
    }

    public void updateMovementDirection(Vector2 movementDirection){
        player.move(movementDirection);
    }

    public World getWorld() {
        return world;
    }

    public void explosion(float x, float y, float radius, float force){
        player.applyExplosion(x, y, radius, force);
    }
}
