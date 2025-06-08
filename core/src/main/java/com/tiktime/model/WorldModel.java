package com.tiktime.model;

import com.badlogic.gdx.Gdx;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class WorldModel implements EventListener, Disposable {
    private final World world;
    // TODO: magic constants
    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

    private final MapModel mapModel;
    private final BodyManager bodyManager;
    private final PlayerModel player;
    private final Set<EnemyModel> enemies = new HashSet<>();

    public void update(float delta){
        // ORDER IS IMPORTANT
        enemies.forEach(e -> {
//            Gdx.app.log("WorldModel", e.getWeaponModel().toString());
            e.update(delta, player, world);
        });
        world.step(delta, velocityIterations, positionIterations);
        bodyManager.flush();
//        Gdx.app.log("Physics", "Bodies count: " + world.getBodyCount());
    }

    public WorldModel(MapModel mapModel) {
        this(mapModel, null);
    }

    public WorldModel(MapModel mapModel, PlayerModel player) {
        this.mapModel = mapModel;
        world = new World(new Vector2(0, 0), true);
        bodyManager = new BodyManager(world);
        Vector2 playerPosition = mapModel.getPlayerSpawnPosition();
        if (player == null) {
            this.player = EntityFactory.createPlayerModel(world, bodyManager,
                playerPosition.x, playerPosition.y);
        } else {
            this.player = EntityFactory.createPlayerModelAtNextMap(world, bodyManager,
                playerPosition.x, playerPosition.y, player);
        }

        ArrayList<Vector2> enemiesPositions = mapModel.getEnemiesSpawnPositions();
        for (Vector2 p : enemiesPositions) {
            RusherEnemyModel rusherEnemyModel = EntityFactory.createRusherEnemyModel(world, bodyManager,
                p.x, p.y);

            enemies.add(rusherEnemyModel);
        }

        mapModel.createAll(world);
        subscribeOnEvents();
    }

    private void subscribeOnEvents() {
        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
    }

    private void unsubscribeOnEvents() {
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
    }

    public void setCollisionController(CollisionController collisionController){
        world.setContactListener(collisionController);
    }

    public BodyManager getBodyManager() {
        return bodyManager;
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
        unsubscribeOnEvents();
        mapModel.dispose();
    }
}
