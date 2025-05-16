package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.tiktime.Main;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.model.WorldModel;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.gameobjects.EnemyModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.*;

import java.util.ArrayList;
import java.util.List;

public class WorldController {
    private final Main game;
    private GameView gameView;
    private WorldModel worldModel;
    private MapSelector mapSelector;
    private boolean paused = false;
    private int isInDoor = 0;
    private List<Body> toDelete = new ArrayList<>();
    private WorldInputProcessor inputProcessor = new WorldInputProcessor(this);

    public WorldController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.mapSelector = new MapSelector();
        TiledMap map = mapSelector.getMap();
        this.worldModel = new WorldModel(map, new CollisionController(this));
        gameView.setController(this);

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);

        EntityData entityData = worldModel.getPlayerData();
        gameView.setPlayer(
            worldModel.getPlayerPosition().x,
            worldModel.getPlayerPosition().y,
            entityData.getWidth(),
            entityData.getHeight(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );
        gameView.setHud(entityData.getCurrentHealth(), entityData.getMaxHealth(), PlayerModel.CurrentStats.getCoins());
        Array<EnemyModel> enemies = worldModel.getEnemies();
        for (EnemyModel e: enemies) {
            gameView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                e.getData().getWidth(), e.getData().getHeight(), e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER);
        }
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setPaused(!paused);
        }

        if (paused) {
            return;
        }

        if (isInDoor >= 1 && Gdx.input.isKeyPressed(Input.Keys.E)) {
//            Gdx.app.log("WorldController", "Entered door");
            changeMap();
        }
        Vector2 direction = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction.x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction.y -= 1;
        }

        worldModel.updateMovementDirection(direction, delta);
        worldModel.update(delta);

        Vector2 playerPosition = worldModel.getPlayerPosition();
        gameView.setPlayerCoordinates(playerPosition.x, playerPosition.y);
        if (direction.equals(Vector2.Zero)) {
            gameView.setPlayerState(LivingEntityState.IDLE);
        } else {
            if (!direction.equals(new Vector2(0, 1)) && !direction.equals(new Vector2(0, -1))) {
                gameView.setPlayerDirection(getDirection(direction));
            }
            gameView.setPlayerState(LivingEntityState.RUNNING);
        }

        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameView.updatePlayerWeaponRotation(mousePosition, getWeaponPosition(playerPosition.x, playerPosition.y, WeaponType.AK47));
        gameView.setPlayerCurHealth(worldModel.getPlayerData().getCurrentHealth());
        gameView.setIsInDoor(isInDoor > 0);
        for(Body i : toDelete){
            worldModel.getWorld().destroyBody(i);
        }
        toDelete.clear();

        Array<EnemyModel> enemies = worldModel.getEnemies();
        enemies.forEach(e -> {
            gameView.setEnemyCoordinates(e.getBody().getPosition().x,
                e.getBody().getPosition().y,
                e.getId());
            if (!e.getDirection().equals(Vector2.Zero)) {
                gameView.setEnemyDirection(getDirection(e.getDirection()), e.getId());
            }
        });
    }

    public void changeMap(){
        isInDoor = 0;
        TiledMap map = mapSelector.getMap();
        EntityData playerData = worldModel.getPlayerData();
        this.worldModel = new WorldModel(map, new CollisionController(this), playerData);
        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);
        gameView.clear();
        Array<EnemyModel> enemies = worldModel.getEnemies();
        for (EnemyModel e: enemies) {
            gameView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                e.getData().getWidth(), e.getData().getHeight(), e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER);
        }
    }

    Vector3 getWeaponPosition(float x, float y, WeaponType weapon) {
        GameConfig.WeaponConfig weaponConfig = GameConfig.getInstance().getWeaponConfig(weapon);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        float xn = x + weaponConfig.getOffsetX();
        float yn = y + weaponConfig.getOffsetY();
        float width = weaponConfig.getWidth();
        float height = weaponConfig.getHeight();
        return new Vector3(xn - width / 2f, yn - height / 2f, 0);
    }

    Direction getDirection(Vector2 dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Direction argument was null");
        }

        if (Math.abs(dir.x) > 1 || Math.abs(dir.y) > 1) {
            throw new RuntimeException("Invalid direction");
        }

//        Gdx.app.log("WorldController", "Direction: " + dir);
        if (dir.x == 0)
            throw new IllegalArgumentException("Invalid direction, shouldnt change direction");

        if (dir.x > 0) {
            return Direction.EAST;
        }

        return Direction.WEST;
    }

    public void onDoorEntry(){
        isInDoor++;
    }

    public void onDoorExit(){
        isInDoor--;
    }

    public void explosion(Body body, float radius, float force) {
        TiledMapTileLayer.Cell cell = (TiledMapTileLayer.Cell) body.getUserData();
        cell.setTile(null);
        worldModel.explosion(body.getPosition().x, body.getPosition().y, radius, force);
    }

    void deleteBody(Body body){
        toDelete.add(body);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        gameView.setPause(paused);
    }


    public void goToMenu() {
        game.setScreen(new MenuScreen(game));
    }

    public void pushApart(Body A, Body B) {
        float pushMagnitude = 0.3f;
        Vector2 directionAtoB = B.getPosition().cpy().sub(A.getPosition()).nor();
        Vector2 impulseForA = directionAtoB.cpy().scl(-pushMagnitude);
        A.applyLinearImpulse(impulseForA, A.getWorldCenter(), true);
        Vector2 impulseForB = directionAtoB.cpy().scl(pushMagnitude);
        B.applyLinearImpulse(impulseForB, B.getWorldCenter(), true);
    }


}
