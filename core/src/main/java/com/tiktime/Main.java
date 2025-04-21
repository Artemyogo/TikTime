package com.tiktime;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.model.Player;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Faction;
import com.tiktime.screens.MenuScreen;

public class Main extends Game {
    private final Player player;
    public Main(){
        player = new Player("", 0f, 0f, 0f, 0f, null, null, Faction.PLAYER);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
