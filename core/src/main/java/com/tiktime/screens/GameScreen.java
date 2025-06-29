package com.tiktime.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.tiktime.Main;
import com.tiktime.controller.world.WorldController;
import com.tiktime.view.world.GameView;

public class GameScreen extends ScreenAdapter {
    private final WorldController worldController;
    private final GameView gameView;

    public GameScreen(ScreenHandler screenHandler) {
        gameView = new GameView();
        worldController = new WorldController(screenHandler, gameView);
    }

    @Override
    public void show() {
        worldController.activateInputProcessor();
    }

    @Override
    public void render(float v) {
        // ORDER IS IMPORTANT
        gameView.render(v);
        worldController.update(v);
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override
    public void dispose() {
        worldController.dispose();
        gameView.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }
}
