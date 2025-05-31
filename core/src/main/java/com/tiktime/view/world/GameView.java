package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.common.Pausable;
import com.tiktime.controller.world.WorldController;

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

    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
        ArrayList<Pausable> allPausables = new ArrayList<>();
        allPausables.add(worldView);
        allPausables.add(pauseView);
        allPausables.forEach(p -> p.setPaused(paused));
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

    public WorldView getWorldView() {
        return worldView;
    }

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
