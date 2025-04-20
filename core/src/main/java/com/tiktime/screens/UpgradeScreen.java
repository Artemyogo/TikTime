package com.tiktime.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.tiktime.controller.UpgradeController;
import com.tiktime.model.UpgradeModel;
import com.tiktime.view.UpgradeView;

public class UpgradeScreen extends ScreenAdapter {
    UpgradeController controller;
    UpgradeModel model;
    UpgradeView view;

    public UpgradeScreen(Game game){
        model = new UpgradeModel();
        view = new UpgradeView();
        controller = new UpgradeController(game, model, view);
        view.setController(controller);
    }

    @Override
    public void show() {
        view.show();
    }

    @Override
    public void render(float delta) {
        view.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void dispose() {
        view.dispose();
    }
}
