package com.tiktime.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.controller.world.CollisionController;
import com.tiktime.model.entities.entityfactories.EntityFactory;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.entities.livingenteties.LivingEntityModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.RusherEnemyModel;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.entityfactories.FixtureFactory;

import com.tiktime.model.entities.raycasts.InPathRaycast;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;

import java.util.HashSet;
import java.util.Set;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class WorldModel implements EventListener, Disposable {
    private final World world;
    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

    private final TiledMap map;
    private PlayerModel player;
    private Set<EnemyModel> enemies = new HashSet<>();

    public void update(float delta){
        world.step(delta, velocityIterations, positionIterations);
        for(EnemyModel enemy : enemies){
            enemy.chasePlayer(delta, player, world);
        }
    }

    public WorldModel(TiledMap map) {
        this(map, null);
    }

    public WorldModel(TiledMap map, PlayerModel player) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        if (player == null) {
            this.player = EntityFactory.createPlayerModel(world,
                properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM);
        } else {
            this.player = EntityFactory.createPlayerModelAtNextMap(world,
                properties.get("x", Float.class) / PPM, properties.get("y", Float.class) / PPM, player);
        }

        if (map.getLayers().get("enemies") != null) {
            for (MapObject object : map.getLayers().get("enemies").getObjects()) {
                RusherEnemyModel rusherEnemyModel = EntityFactory.createRusherEnemyModel(world,
                    object.getProperties().get("x", Float.class) / PPM, object.getProperties().get("y", Float.class) / PPM);

                enemies.add(rusherEnemyModel);
            }
        }

        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        BodyFactory.createBodies(world, wallLayer, FixtureFactory.getWallFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer doorLayer = (TiledMapTileLayer) map.getLayers().get("doors");
        BodyFactory.createBodies(world, doorLayer, FixtureFactory.getDoorFixture(), BodyDef.BodyType.StaticBody);

        TiledMapTileLayer dynamiteLayer = (TiledMapTileLayer) map.getLayers().get("dynamite");
        BodyFactory.createBodies(world, dynamiteLayer, FixtureFactory.getDynamiteFixture(), BodyDef.BodyType.StaticBody);

        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
    }

    public void setCollisionController(CollisionController collisionController){
        world.setContactListener(collisionController);

    }

    public PlayerModel getPlayerModel(){
        return player;
    }

    public Vector2 getPlayerPosition(){
        return player.getPosition();
    }

    public Set<EnemyModel> getEnemies(){
        return new HashSet<>(enemies);
    }

    public World getWorld() {
        return world;
    }

    public void explosion(float x, float y, float radius, float force){
        HashSet<LivingEntityModel> entities = new HashSet<>(enemies);
        entities.add(player);
        Vector2 position = new Vector2(x, y);
        entities.forEach(entity -> {
            InPathRaycast callback = new InPathRaycast(entity.getBody().getUserData());
            world.rayCast(callback, new Vector2(position), entity.getBody().getPosition());
            if (callback.isInPath()) {
                entity.applyForce(x, y, radius, force);
            }
        });
    }

    public float distance(Vector2 start, Vector2 end){
        return (float) Math.hypot(start.x - end.x, start.y - end.y);
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.type == GameEventType.ENEMY_DEATH) {
            if (!(event.data instanceof EnemyModel)) {
                throw new RuntimeException("Invalid event data");
            }

            EnemyModel enemyModel = (EnemyModel) event.data;
            enemies.remove(enemyModel);
        }
    }

    @Override
    public void dispose() {
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
    }
}
