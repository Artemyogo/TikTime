package com.tiktime.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudView {
    private final String backgroundPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueBar_128x16.png";
    private final String fillPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueRed_120x8.png";
    private int coins;
    ProgressBarView health;

    public HudView(float x, float y, float width, float height, int curHealth, int maxHealth, int coins) {
        this.coins = coins;
        this.health = new ProgressBarView(backgroundPath, fillPath, x, y, 11, 11, width, height, curHealth, maxHealth);
        if (curHealth > maxHealth) {
            throw new IllegalArgumentException("curHealth > maxHealth");
        }
    }

    public void render(float delta, SpriteBatch batch) {
        health.render(delta, batch);

    }
}
