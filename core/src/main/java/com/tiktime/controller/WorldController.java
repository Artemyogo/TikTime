package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.Main;
import com.tiktime.controller.Interactions.DoorInteraction;
import com.tiktime.controller.Interactions.DynamiteInteraction;
import com.tiktime.controller.Interactions.EntityInteraction;
import com.tiktime.controller.inputprocessors.WorldInputProcessor;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.controller.utils.MapSelectorStrategy;
import com.tiktime.controller.utils.RandomSelectorStrategy;
import com.tiktime.model.BodyManager;
import com.tiktime.model.DoorSensorModel;
import com.tiktime.model.WorldModel;
import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.configs.configdata.WeaponData;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.WeaponType;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.world.GameView;
import com.tiktime.view.world.Pausable;
import java.util.Set;

public class WorldController implements Pausable, Disposable, EventListener, IExplosive{
    private final Main game;
    private final GameView gameView;
    private WorldModel worldModel;
    private final MapSelector mapSelector;
    private boolean paused = false;
    private final DoorSensorModel doorSensorModel = new DoorSensorModel();
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
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

        inputMultiplexer.addProcessor(inputProcessor);
        gameView.setController(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

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
        enemyController = new EnemyController(gameView);
        enemyController.setBodyManager(bodyManager);
        enemyController.setEnemies(worldModel.getEnemies());

        EventManager.subscribe(GameEventType.PLAYER_DEATH, this);
        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
    }

    /// TODO: BEWARE idk this is good or not maybe with 'stage' it is acceptable
    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public void activateInputProcessor() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public Set<EnemyModel> getEnemies() {
        return worldModel.getEnemies();
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
        enemyController.update(delta);
        worldModel.update(delta);
        bodyManager.flush();
    }

    public void changeMap(){
        doorSensorModel.reset();
        TiledMap map = mapSelector.getMap(new RandomSelectorStrategy());
        PlayerModel playerModel = worldModel.getPlayerModel();

        worldModel.dispose();
        this.worldModel = new WorldModel(map, playerModel);
        this.bodyManager = new BodyManager(worldModel.getWorld());
        enemyController.setBodyManager(this.bodyManager);
        worldModel.setCollisionController(new CollisionController(this).
            addInteraction(new DynamiteInteraction(this, bodyManager)).
            addInteraction(new DoorInteraction(doorSensorModel)).
            addInteraction(new EntityInteraction()));

        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(map);
        playerController.setPlayer(worldModel.getPlayerModel());
        enemyController.setEnemies(worldModel.getEnemies());
//        enemyController.resubscribe();
    }

    Vector3 getWeaponPosition(Vector2 playerPosition, WeaponType weapon) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(weapon);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        /// TODO: BEWARE TOOOOOO WIERD
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
    }

    public void changePausedStatus() {
        setPaused(!paused);
    }

    public void goToMenu() {
        game.setScreen(new MenuScreen(game));
    }

    public boolean isPaused() {
        return paused;
    }

    public void pushApart(Body A, Body B) {
        float pushMagnitude = 0.3f;
        Vector2 directionAtoB = B.getPosition().cpy().sub(A.getPosition()).nor();
        Vector2 impulseForA = directionAtoB.cpy().scl(-pushMagnitude);
        A.applyLinearImpulse(impulseForA, A.getWorldCenter(), true);
        Vector2 impulseForB = directionAtoB.cpy().scl(pushMagnitude);
        B.applyLinearImpulse(impulseForB, B.getWorldCenter(), true);
    }

    @Override
    public void dispose() {
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
        EventManager.unsubscribe(GameEventType.PLAYER_DEATH, this);
        enemyController.dispose();
        worldModel.dispose();
    }

    @Override
    public void setPause(boolean paused) {
        this.paused = paused;
        gameView.setPause(paused);
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.type == GameEventType.ENEMY_DEATH) {
            if (!(event.data instanceof EnemyModel)) {
                throw new RuntimeException("WHAAAAAAAAAAAAT?!?!");
            }

            gameView.setCoins(UpgradeModel.getInstance().getMoney());
        }
        if (event.type == GameEventType.PLAYER_DEATH) {
            if (!(event.data instanceof PlayerModel)) {
                throw new RuntimeException("WHAAAAAAAAAAAAT?!?!");
            }


        }
    }
}
