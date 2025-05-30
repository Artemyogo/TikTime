package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.Main;
import com.tiktime.controller.Interactions.DoorInteraction;
import com.tiktime.controller.Interactions.DynamiteInteraction;
import com.tiktime.controller.Interactions.EntityInteraction;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.controller.utils.RandomSelectorStrategy;
import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.BodyManager;
import com.tiktime.model.DoorSensorModel;
import com.tiktime.model.WorldModel;
import com.tiktime.model.configs.configdata.WeaponData;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.model.entities.weapons.WeaponType;
import com.tiktime.view.world.GameView;

import java.util.ArrayList;
import java.util.List;

public class WorldController implements IExplosive{
    private final Main game;
    private final GameView gameView;
    private WorldModel worldModel;
    private final MapSelector mapSelector;
    private boolean paused = false;
    private final DoorSensorModel doorSensorModel = new DoorSensorModel();
    private WorldInputProcessor inputProcessor = new WorldInputProcessor(this);
    private final PlayerController playerController;
    private final EnemyController enemyController;
    private BodyManager bodyManager;

    public WorldController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.mapSelector = new MapSelector();
        TiledMap map = mapSelector.getMap(new RandomSelectorStrategy());
        this.worldModel = new WorldModel(map);
        gameView.setController(this);

        bodyManager = new BodyManager(worldModel.getWorld());

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);

        PlayerModel player = worldModel.getPlayerModel();

        playerController = new PlayerController(player, gameView.getWorldView());

        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(this, bodyManager)).
            addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()));

        gameView.setHud(PlayerModel.CurrentStats.getCoins());
        enemyController = new EnemyController(worldModel, gameView);
    }

    public void activateInputProcessor() {
        inputProcessor = new WorldInputProcessor(this);
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
        inputProcessor.setInDoor(doorSensorModel.isInDoor());
        gameView.setIsInDoor(doorSensorModel.isInDoor());
        Vector2 direction = inputProcessor.getDirection();

        playerController.update(delta, direction);
        worldModel.update(delta);
        enemyController.update(delta);
        bodyManager.flush();
    }

    public void changeMap(){
        doorSensorModel.reset();
        TiledMap map = mapSelector.getMap(new RandomSelectorStrategy());
        PlayerModel playerModel = worldModel.getPlayerModel();

        this.worldModel = new WorldModel(map, playerModel);
        this.bodyManager = new BodyManager(worldModel.getWorld());
        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(this, bodyManager)).
            addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()));

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);
        playerController.setPlayer(worldModel.getPlayerModel());
        enemyController.setEnemies(worldModel.getEnemies());
    }

    Vector3 getWeaponPosition(Vector2 playerPosition, WeaponType weapon) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(weapon);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        return new Vector3(playerPosition.x, playerPosition.y + 0.2f, 0f);
    }


    public void explosion(Body body, float radius, float force) {
        TiledMapTileLayer.Cell cell = (TiledMapTileLayer.Cell) body.getUserData();
        cell.setTile(null);
        worldModel.explosion(body.getPosition().x, body.getPosition().y, radius, force);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        gameView.setPause(paused);
        if(!paused){
            activateInputProcessor();
        }
    }

    public void changePausedStatus() {
        setPaused(!paused);
    }


    public void goToMenu() {
        game.setScreen(new MenuScreen(game));
    }

}
