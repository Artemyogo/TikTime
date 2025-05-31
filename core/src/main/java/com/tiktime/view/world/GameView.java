package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.view.enteties.*;
import com.tiktime.view.enteties.livingenteties.*;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyType;
import com.tiktime.model.entities.weapons.WeaponType;

import java.util.ArrayList;

public class GameView implements Pausable, Renderable, Disposable {
    private boolean paused = false;
    private final Viewport screenViewport;
    private final OrthographicCamera screenCamera;
    private WorldView worldView;
    private HudView hudView;
    private PauseView pauseView;

    public GameView() {
        screenCamera = new OrthographicCamera();
        screenViewport = new ScreenViewport(screenCamera);
        screenViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldView = new WorldView();
        pauseView = new PauseView(screenViewport, screenCamera);
    }

    public void setController(WorldController worldController) {
        pauseView.setController(worldController);
    }

    public void setWorld(World world) {
        worldView.setWorld(world);
    }

    @Override
    public void setPause(boolean paused) {
        this.paused = paused;
        ArrayList<Pausable> allPausables = new ArrayList<>();
        allPausables.add(worldView);
        allPausables.add(pauseView);
        allPausables.forEach(p -> p.setPause(paused));
    }

    public void setCoins(int coins) {
        hudView.setCoins(coins);
    }

    public void setMapRenderer(TiledMap map) {
        worldView.setMapRenderer(map);
    }

    public void setPlayer(float x, float y, float width, float height, int curHealth, int maxHealth,
                          Direction direction, LivingEntityState state, WeaponType weapon) {
        worldView.setPlayer(x, y, width, height, curHealth, maxHealth, direction, state, weapon);
    }

    public void setHud(int coins) {
        if (worldView.getPlayerView() == null) {
            throw new NullPointerException();
        }

        hudView = new HudView(
            screenViewport,
            screenCamera,
            worldView.getPlayerView(),
            coins);
    }

    public void setPlayerIsAttacked(boolean isAttacked) {
        worldView.setPlayerIsAttacked(isAttacked);
    }

    public void setEnemyIsAttacked(int id, boolean isAttacked) {
        worldView.setEnemyIsAttacked(id, isAttacked);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ArrayList<Renderable> allRenderables = new ArrayList<>();
        allRenderables.add(worldView);
        allRenderables.add(hudView);
        allRenderables.add(pauseView);
        allRenderables.forEach(r -> r.render(delta));
    }

    public void setIsInDoor(boolean isInDoor) {
        hudView.setIsInDoor(isInDoor);
    }

    public void addEnemy(float x, float y, float width, float height, int curHealth, int maxHealth, int id, Direction direction,
                         LivingEntityState state, EnemyType enemyType) {
        worldView.addEnemy(x, y, width, height, curHealth, maxHealth, id, direction, state, enemyType);
    }

    public void deleteEnemy(int id) {
        worldView.deleteEnemy(id);
    }

    public void clearEnemies() {
        worldView.clearEnemies();
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        worldView.setEnemyCoordinates(x, y, id);
    }

    public void setEnemySize(float width, float height, int id) {
        worldView.setEnemyCoordinates(width, height, id);
    }

    public void setEnemyState(LivingEntityState state, int id) {
        worldView.setEnemyState(state, id);
    }

    public void setEnemyDirection(Direction direction, int id) {
        worldView.setEnemyDirection(direction, id);
    }

    public void setPlayerCoordinates(float x, float y) {
        worldView.setPlayerCoordinates(x, y);
    }

    public void setPlayerSizes(float width, float height) {
        worldView.setPlayerSizes(width, height);
    }

    public void setPlayerState(LivingEntityState state) {
        worldView.setPlayerState(state);
    }

    public void setPlayerDirection(Direction direction) {
        worldView.setPlayerDirection(direction);
    }

    public WorldView getWorldView() {
        return worldView;
    }

    public void updatePlayerWeaponRotation(Vector3 screenCoords, Vector3 weaponCoords) {
        worldView.updatePlayerWeaponRotation(screenCoords, weaponCoords);
    }

    public void show() {}

    public void resize(int width, int height) {
        worldView.resize(width, height);
        screenViewport.update(width, height, false);
        screenCamera.update();
        screenCamera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        worldView.dispose();
        hudView.dispose();
        pauseView.dispose();
    }
}
