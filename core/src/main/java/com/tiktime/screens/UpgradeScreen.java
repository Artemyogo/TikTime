package com.tiktime.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.tiktime.Main;
import com.tiktime.controller.UpgradeController;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.view.UpgradeView;

public class UpgradeScreen extends ScreenAdapter {
    UpgradeController controller;
    UpgradeView view;

    public UpgradeScreen(ScreenHandler screenHandler){
        view = new UpgradeView(UpgradeModel.getInstance().getManager());
        controller = new UpgradeController(screenHandler, view);
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
