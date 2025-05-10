package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tiktime.Main;
import com.tiktime.screens.GameScreen;
import com.tiktime.screens.SettingsScreen;
import com.tiktime.screens.UpgradeScreen;

public class MenuController {
    private final Main game;

    public MenuController(Main game) {
        this.game = game;
    }

    public void onPlayClicked() {
        /// TODO SET FIRST MAP NAME
        Gdx.app.log("SCREEN", "Show: " + this.getClass().getSimpleName());
        game.setScreen(new GameScreen(game));
    }

    public void onUpgradeClicked() {
        game.setScreen(new UpgradeScreen(game));
    }

    public void onSettingsClicked() {
        game.setScreen(new SettingsScreen());
    }

    public void onExitClicked() {
        Gdx.app.exit();
    }
}
