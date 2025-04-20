package com.tiktime.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.tiktime.controller.MenuController;
import com.tiktime.view.MenuView;

public class MenuScreen implements Screen {
    private final Game game;
    private final MenuController menuController;
    private final MenuView menuView;

    public MenuScreen(Game game) {
        this.game = game;
        menuController = new MenuController(game);
        menuView = new MenuView(menuController);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
