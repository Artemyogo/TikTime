package com.tiktime;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.tiktime.screens.*;

public class Main extends Game implements ScreenHandler {
    @Override
    public void create() {
        setScreen(Screen.MAIN_MENU);
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null) throw new NullPointerException("Screen cannot be null");

        if (screen == Screen.GAME_SCREEN) {
            setScreen(new GameScreen(this));
        } else if (screen == Screen.MAIN_MENU) {
            setScreen(new MenuScreen(this));
        } else if (screen == Screen.UPGRADE_MENU) {
            setScreen(new UpgradeScreen(this));
        } else if (screen == Screen.SETTINGS_MENU) {
            setScreen(new SettingsScreen(this));
        } else if (screen == Screen.DEATH_SCREEN) {
            Gdx.app.log("Game", "Death Screen");
            setScreen(new DeathScreen(this));
        } else {
            throw new IllegalArgumentException("Unknown screen: " + screen);
        }
    }
}
