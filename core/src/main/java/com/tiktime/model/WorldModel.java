package com.tiktime.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tiktime.controller.CollisionController;
import com.tiktime.model.gamefactories.BodyFactory;
import com.tiktime.model.gamefactories.FixtureFactory;
import com.tiktime.model.gameobjects.*;

import com.tiktime.model.raycasts.InPathRaycast;

import static com.tiktime.view.consts.ScreenConstants.PPM;

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
            enemy.chasePlayer(delta, player, world);
        }
    }

    public WorldModel(TiledMap map, CollisionController collisionController) {
        this(map, collisionController, null);
    }

    public WorldModel(TiledMap map, CollisionController collisionController, EntityData playerData) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        if (playerData == null) {
            this.player = new PlayerModel(world, properties.get("x", Float.class) / PPM,
                properties.get("y", Float.class) / PPM);
        } else {
            this.player = new PlayerModel(world, properties.get("x", Float.class) / PPM,
                properties.get("y", Float.class) / PPM, playerData);
        }

        if (map.getLayers().get("enemies") != null) {
            for (MapObject object : map.getLayers().get("enemies").getObjects()) {
                enemies.add(new RusherEnemyModel(world, object.getProperties().get("x", Float.class) / PPM,
                    object.getProperties().get("y", Float.class) / PPM));
            }
        }

        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        BodyFactory.createBodies(world, wallLayer, FixtureFactory.getWallFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        BodyFactory.createBodies(world, doorLayer, FixtureFactory.getDoorFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        BodyFactory.createBodies(world, dynamiteLayer, FixtureFactory.getDynamiteFixture(), BodyDef.BodyType.StaticBody);
        world.setContactListener(collisionController);
    }

    public EntityData getPlayerData(){
        return player.getData();
    }

    public Vector2 getPlayerPosition(){
        return player.getPosition();
    }

    public Array<EnemyModel> getEnemies(){
        return new Array<>(enemies);
    }

    public World getWorld() {
        return world;
    }

    public void explosion(float x, float y, float radius, float force){
        Array<EntityModel> entities = new Array<>(enemies);
        entities.add(player);
        Vector2 position = new Vector2(x, y);
        entities.forEach(entity -> {
            InPathRaycast callback = new InPathRaycast(entity.getBody().getUserData());
            world.rayCast(callback, new Vector2(position), entity.getBody().getPosition());
            if (callback.isInPath()) {
                entity.applyExplosion(x, y, radius, force);
            }
        });
    }

    public float distance(Vector2 start, Vector2 end){
        return (float) Math.hypot(start.x - end.x, start.y - end.y);
    }

    public PlayerModel getPlayer(){
        return player;
    }
}
