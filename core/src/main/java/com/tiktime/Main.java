package com.tiktime;

import com.badlogic.gdx.Game;
import com.tiktime.screens.MenuScreen;

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
