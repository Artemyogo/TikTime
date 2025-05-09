package com.tiktime.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudView {
    private int coins;
    private int curHealth, maxHealth;
    Texture healthBar;
    Texture coin;

    public HudView(int coins, int curHealth, int maxHealth) {
        this.coins = coins;
        this.curHealth = curHealth;
        this.maxHealth = maxHealth;
        if (curHealth > maxHealth) {
            throw new IllegalArgumentException("curHealth > maxHealth");
        }
    }

    public void render(float delta, SpriteBatch batch) {

    }
}
