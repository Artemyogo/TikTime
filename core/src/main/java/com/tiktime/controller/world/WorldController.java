package com.tiktime.controller.world;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.Main;
import com.tiktime.controller.Interactions.DoorInteraction;
import com.tiktime.controller.Interactions.DynamiteInteraction;
import com.tiktime.controller.Interactions.EntityInteraction;
import com.tiktime.controller.inputprocessors.WorldInputProcessor;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.controller.utils.RandomSelectorStrategy;
import com.tiktime.controller.world.enteties.EnemyController;
import com.tiktime.controller.world.enteties.PlayerController;
import com.tiktime.model.BodyManager;
import com.tiktime.model.DoorSensorModel;
import com.tiktime.model.WorldModel;
import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.configs.configdata.WeaponData;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.common.WeaponType;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.world.GameView;
import com.tiktime.common.Pausable;
import com.tiktime.view.world.WorldView;

public class WorldController implements Pausable, Disposable, IExplosive {
    private final Main game;
    private final GameView gameView;
    private final WorldView worldView;

    private final DoorSensorModel doorSensorModel = new DoorSensorModel();
    private final MapSelector mapSelector;
    private BodyManager bodyManager;
    private WorldModel worldModel;

    private final WorldInputProcessor inputProcessor = new WorldInputProcessor(this);
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private final PlayerController playerController;
    private final EnemyController enemyController;

    private boolean paused = false;

    public WorldController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        worldView = gameView.getWorldView();

        mapSelector = new MapSelector();
        TiledMap map = mapSelector.getMap(new RandomSelectorStrategy());
        worldModel = new WorldModel(map);

        setInputProcessor();

        PlayerModel player = worldModel.getPlayerModel();
        playerController = new PlayerController(player, worldView);
        enemyController = new EnemyController(worldView);
        enemyController.setEnemies(worldModel.getEnemies());

        setBodyManager(worldModel.getWorld());
        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(this, bodyManager)).
            addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()));

        worldView.setWorld(worldModel.getWorld());
        worldView.setMapRenderer(map);
        gameView.setHud(PlayerModel.CurrentStats.getCoins());
    }

    private void setInputProcessor() {
        inputMultiplexer.addProcessor(inputProcessor);
        gameView.setController(this);
        activateInputProcessor();
    }

    private void setBodyManager(World world) {
        bodyManager = new BodyManager(world);
        enemyController.setBodyManager(bodyManager);
    }

    // TODO: BEWARE idk this is good or not maybe with 'stage' it is acceptable
    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public void activateInputProcessor() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void updateMousePosition(int x, int y) {
        Vector3 mousePosition = new Vector3(x, y, 0);

        worldView.updatePlayerWeaponRotation(mousePosition,
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
        enemyController.update(delta);
        worldModel.update(delta);

        bodyManager.flush();
    }

    public void changeMap(){
        doorSensorModel.reset();
        TiledMap map = mapSelector.getMap(new RandomSelectorStrategy());
        PlayerModel playerModel = worldModel.getPlayerModel();

        worldModel.dispose();
        worldModel = new WorldModel(map, playerModel);
        setBodyManager(worldModel.getWorld());
        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(this, bodyManager)).
            addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()));
        playerController.setPlayer(worldModel.getPlayerModel());
        enemyController.setEnemies(worldModel.getEnemies());

        worldView.setWorld(worldModel.getWorld());
        worldView.setMapRenderer(map);
    }

    Vector3 getWeaponPosition(Vector2 playerPosition, WeaponType weaponType) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(weaponType);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        return new Vector3(playerPosition.x, playerPosition.y, 0f);
    }

    @Override
    public void explosion(Body body, float radius, float force) {
        TiledMapTileLayer.Cell cell = (TiledMapTileLayer.Cell) body.getUserData();
        cell.setTile(null);
        worldModel.explosion(body.getPosition().x, body.getPosition().y, radius, force);
    }

    public void changePausedStatus() {
        setPaused(!paused);
    }

    public void goToMenu() {
        game.setScreen(new MenuScreen(game));
    }

    // TODO: same here, mb getter for controller is not good idea, but it for inputProcessor, so idk
    public boolean getPaused() {
        return paused;
    }

    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
        gameView.setPaused(paused);
    }

    @Override
    public void dispose() {
        enemyController.dispose();
        playerController.dispose();
        worldModel.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
