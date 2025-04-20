package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.tiktime.ui.screens.GameScreen;
import com.tiktime.ui.screens.UpgradeScreen;

public class MenuController {
    private final Game game;

    public MenuController(Game game) {
        this.game = game;
    }
    public void onPlayClicked() {
        game.setScreen(new GameScreen());
    }

    public void onUpgradeClicked() {
        game.setScreen(new UpgradeScreen());
    }

    public void onSettingsClicked() {
        game.setScreen(new UpgradeScreen());
    }
}
