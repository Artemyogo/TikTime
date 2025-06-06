package com.tiktime.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.tiktime.Main;
import com.tiktime.controller.MenuController;
import com.tiktime.view.MenuView;

public class MenuScreen extends ScreenAdapter {
    private final MenuController menuController;
    private final MenuView menuView;

    public MenuScreen(ScreenHandler screenHandler) {
        menuController = new MenuController(screenHandler);
        menuView = new MenuView(menuController);
    }

    @Override
    public void show() {
        menuView.show();
    }

    @Override
    public void render(float v) {
        menuView.render(v);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        menuView.resize(width, height);
    }

    @Override
    public void dispose() {
        menuView.dispose();
    }
}
