package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HudView {
    private final String backgroundPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueBar_128x16.png";
    private final String fillPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueRed_120x8.png";
    private final String fontPath = "flat-earth/skin/font-title-export.fnt";
    private final String coinPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/CoinIcon_16x18.png";
    private final BitmapFont font;
    Texture coinTexture;
    private float x, y;
    private int coins;
    ProgressBarView health;

    public HudView(float x, float y, float width, float height, int curHealth, int maxHealth, int coins) {
        if (curHealth > maxHealth) {
            throw new IllegalArgumentException("curHealth > maxHealth");
        }

        this.coinTexture = new Texture(coinPath);
        this.coins = coins;
        this.health = new ProgressBarView(backgroundPath, fillPath, x, y, 11, 11, width, height, curHealth, maxHealth);
//        this.font = new BitmapFont(Gdx.files.internal(fontPath));
        this.font = new BitmapFont();
        this.x = x;
        this.y = y;
    }

    public void render(float delta, SpriteBatch batch) {
        health.render(delta, batch);

        batch.draw(coinTexture, x, y - 10 - 64, 64, 64);
        font.getData().setScale(2f);
        font.draw(batch,
            String.valueOf(coins),
            x + 10 + 64,
            y - 10 - 20);

    }

    public void dispose() {
        font.dispose();
        coinTexture.dispose();
        health.dispose();
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCurHealth(int curHealth) {

    }
}
