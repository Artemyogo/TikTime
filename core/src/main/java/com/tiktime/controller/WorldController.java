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
import com.tiktime.controller.utils.MapSelectorStrategy;
import com.tiktime.controller.utils.RandomSelectorStrategy;
import com.tiktime.model.WorldModel;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.gameobjects.EnemyModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.*;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyType;
import com.tiktime.view.enteties.weapons.WeaponType;
import com.tiktime.view.world.GameView;

import java.util.ArrayList;
import java.util.List;

public class WorldController {
    private final Main game;
    private final GameView gameView;
    private WorldModel worldModel;
    private final MapSelector mapSelector;
    private final MapSelectorStrategy selectorStrategy = new RandomSelectorStrategy();
    private boolean paused = false;
    private int isInDoor = 0;
    private final List<Body> toDelete = new ArrayList<>();
    private final WorldInputProcessor inputProcessor = new WorldInputProcessor(this);
    private final PlayerController playerController;
    private final EnemyController enemyController;

    public WorldController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.mapSelector = new MapSelector();
        TiledMap map = mapSelector.getMap(selectorStrategy);
        this.worldModel = new WorldModel(map, new CollisionController(this));
        gameView.setController(this);

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);

        PlayerModel player = worldModel.getPlayerModel();

        playerController = new PlayerController(player, gameView);

        gameView.setHud(PlayerModel.CurrentStats.getCoins());
        enemyController = new EnemyController(worldModel, gameView);
    }

    public void activateInputProcessor() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public void updateMousePosition(int x, int y) {
        Vector3 mousePosition = new Vector3(x, y, 0);

        gameView.updatePlayerWeaponRotation(mousePosition,
            getWeaponPosition(
                worldModel.getPlayerModel().getBody().getPosition(),
                WeaponType.AK47));
    }

    public void update(float delta) {
        if (paused) {
            return;
        }
        inputProcessor.setInDoor(isInDoor > 0);
        gameView.setIsInDoor(isInDoor > 0);
        Vector2 direction = inputProcessor.getDirection();

        playerController.update(delta, direction);
        worldModel.update(delta);
        enemyController.update(delta);

        for(Body i : toDelete)
            worldModel.getWorld().destroyBody(i);
        toDelete.clear();

    }

    public void changeMap(){
        isInDoor = 0;
        TiledMap map = mapSelector.getMap(selectorStrategy);
        EntityData playerData = worldModel.getPlayerData();
        this.worldModel = new WorldModel(map, new CollisionController(this), playerData);

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);
        playerController.setPlayer(worldModel.getPlayer());
        enemyController.setEnemies(worldModel.getEnemies());
    }

    Vector3 getWeaponPosition(Vector2 playerPosition, WeaponType weapon) {
        GameConfig.WeaponConfig weaponConfig = GameConfig.getInstance().getWeaponConfig(weapon);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        return new Vector3(playerPosition.x, playerPosition.y + 0.2f, 0f);
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

    public void deleteBody(Body body){
        toDelete.add(body);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        gameView.setPause(paused);
        if(!paused){
            activateInputProcessor();
        }
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
