package com.tiktime.controller;

import com.badlogic.gdx.Gdx;
import com.tiktime.model.world.MapModel;
import com.tiktime.screens.Screen;
import com.tiktime.screens.ScreenHandler;

public class DeathController {
    private ScreenHandler screenHandler;
    public DeathController(ScreenHandler screenHandler) {
        this.screenHandler = screenHandler;
    }

    public void onMenuClicked() {
        MapModel.resetCounter();
        screenHandler.setScreen(Screen.MAIN_MENU);
    }

    public void onExitClicked() {
        Gdx.app.exit();
    }
}
