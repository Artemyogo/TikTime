package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tiktime.Main;
import com.tiktime.screens.*;

public class MenuController {
    private final ScreenHandler screenHandler;

    public MenuController(ScreenHandler screenHandler) {
        this.screenHandler = screenHandler;
    }

    public void onPlayClicked() {
        screenHandler.setScreen(Screen.GAME_SCREEN);
    }

    public void onUpgradeClicked() {
        screenHandler.setScreen(Screen.UPGRADE_MENU);
    }

    public void onSettingsClicked() {
        screenHandler.setScreen(Screen.SETTINGS_MENU);
    }

    public void onExitClicked() {
        Gdx.app.exit();
    }


}
