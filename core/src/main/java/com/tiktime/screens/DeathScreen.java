package com.tiktime.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tiktime.controller.DeathController;
import com.tiktime.view.DeathView;

public class DeathScreen extends ScreenAdapter {
    private final DeathController deathController;
    private final DeathView deathView;

    public DeathScreen(ScreenHandler screenHandler) {
        deathController = new DeathController(screenHandler);
        deathView = new DeathView(deathController);
    }

    @Override
    public void show() {
        deathView.show();
    }

    @Override
    public void render(float v) {
        deathView.render(v);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        deathView.resize(width, height);
    }

    @Override
    public void dispose() {
        deathView.dispose();
    }
}
