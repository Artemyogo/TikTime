package com.tiktime.controller.world;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.MagicConstants;
import com.tiktime.controller.Interactions.*;
import com.tiktime.controller.enteties.DynamiteController;
import com.tiktime.controller.inputprocessors.WorldInputProcessor;
import com.tiktime.controller.utils.DebugSelectorStrategy;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.controller.utils.RandomSelectorStrategy;
import com.tiktime.controller.enteties.BulletController;
import com.tiktime.controller.enteties.EnemyController;
import com.tiktime.controller.enteties.PlayerController;
import com.tiktime.model.world.DoorSensorModel;
import com.tiktime.model.world.MapModel;
import com.tiktime.model.world.WorldModel;
import com.tiktime.model.entities.DynamiteModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.screens.Screen;
import com.tiktime.screens.ScreenHandler;
import com.tiktime.view.world.GameView;
import com.tiktime.common.Pausable;
import com.tiktime.view.world.WorldView;

public class WorldController implements Pausable, Disposable {
    private final ScreenHandler screenHandler;
    private final GameView gameView;

    private final DoorSensorModel doorSensorModel = new DoorSensorModel();
    private final MapSelector mapSelector;
    private WorldModel worldModel;

    private final WorldInputProcessor inputProcessor = new WorldInputProcessor(this);
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private PlayerController playerController;
    private EnemyController enemyController;
    private BulletController bulletController;
    private DynamiteController dynamiteController;

    private final boolean debug = MagicConstants.DEBUG_WORLD_CONTROLLER;
    private boolean paused = false;

    public WorldController(ScreenHandler screenHandler, GameView gameView) {
        this.screenHandler = screenHandler;
        this.gameView = gameView;

        mapSelector = new MapSelector();
        TiledMap map;
        if (debug)
            map = mapSelector.getMap(new DebugSelectorStrategy());
        else
            map = mapSelector.getMap(new RandomSelectorStrategy());
        worldModel = new WorldModel(new MapModel(map));

        WorldView worldView = gameView.getWorldView();
        setControllers(worldModel.getPlayerModel(), worldView);
        setCollisionController(worldModel);

        worldView.setWorld(worldModel.getWorld());
        worldView.setMapRenderer(map);
        gameView.setHud(PlayerModel.CurrentStats.getCoins());
        setInputProcessor();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    private void setInputProcessor() {
        inputMultiplexer.addProcessor(inputProcessor);
        gameView.setController(this);
        activateInputProcessor();
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public void activateInputProcessor() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void update(float delta) {
        if (paused) {
            return;
        }

        boolean canEnterDoor = doorSensorModel.isInDoor() && worldModel.killedAll();
        inputProcessor.setInDoor(canEnterDoor);
        gameView.getHudView().setIsInDoor(canEnterDoor);
        Vector2 direction = inputProcessor.getDirection();

        playerController.update(delta, direction);
        if (playerController.isDead())
            return;
        enemyController.update(delta);
        bulletController.update(delta);
        worldModel.update(delta);
    }

    public void changeMap(){
        doorSensorModel.reset();
        TiledMap map;
        if (debug)
            map = mapSelector.getMap(new DebugSelectorStrategy());
        else
            map = mapSelector.getMap(new RandomSelectorStrategy());

        PlayerModel playerModel = worldModel.getPlayerModel();
        dispose();
        worldModel = new WorldModel(new MapModel(map), playerModel);
        WorldView worldView = gameView.getWorldView();
        setControllers(worldModel.getPlayerModel(), worldView);
        setCollisionController(worldModel);

        worldView.setWorld(worldModel.getWorld());
        worldView.setMapRenderer(map);
        gameView.setHud(PlayerModel.CurrentStats.getCoins());
        setInputProcessor();
    }

    private void setControllers(PlayerModel playerModel, WorldView worldView) {
        playerController = new PlayerController(playerModel, worldView, screenHandler);
        enemyController = new EnemyController(worldView.getAllEnemyView(), worldModel.getEnemies());
        bulletController = new BulletController(worldView.getAllBulletsView());
        dynamiteController = new DynamiteController(worldModel);
    }

    private void setCollisionController(WorldModel worldModel) {
        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(dynamiteController)).
                addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()).
            addInteraction(new MeleeAttackInteraction()).
            addInteraction(new BulletInteraction()).
            addInteraction(new EnemyWallInteraction()).
            addInteraction(new HealthPotionInteraction()));
    }

    public void changePausedStatus() {
        setPaused(!paused);
    }

    public void goToMenu() {
        MapModel.resetCounter();
        screenHandler.setScreen(Screen.MAIN_MENU);
    }

    // TODO: same here, mb getter for controller is not good idea, but it is for inputProcessor, so idk
    public boolean getPaused() {
        return paused;
    }

    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
        gameView.setPaused(paused);
        playerController.setPaused(paused);
        bulletController.setPaused(paused);
        enemyController.setPaused(paused);
    }

    @Override
    public void dispose() {
        bulletController.dispose();
        enemyController.dispose();
        playerController.dispose();
        worldModel.dispose();
    }
}
