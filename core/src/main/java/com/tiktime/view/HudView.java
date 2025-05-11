package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HudView {
    private final String backgroundPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueBar_128x16.png";
    private final String fillPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueRed_120x8.png";
    private final String fontPath = "flat-earth/skin/font-title-export.fnt";
    private final String coinPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/CoinIcon_16x18.png";
    private final BitmapFont font;
    private final Viewport viewport;
    ProgressBarView health;
    Texture coinTexture;
    private int coins;

    public HudView(Viewport viewport, int curHealth, int maxHealth, int coins) {
        if (curHealth > maxHealth) {
            throw new IllegalArgumentException("curHealth > maxHealth");
        }

        this.viewport = viewport;
        this.coinTexture = new Texture(coinPath);
        this.coins = coins;
        this.health = new ProgressBarView(backgroundPath, fillPath, curHealth, maxHealth);
        this.font = new BitmapFont();
    }

    public void render(float delta, SpriteBatch batch, ShapeRenderer shape) {
        float healthBarWidth = 300;
        float healthBarHeight = 40;
        float healthBarX = viewport.getScreenWidth() - 10 - healthBarWidth;
        float healthBarY = viewport.getScreenHeight() - 10 - healthBarHeight;
        float healthBarDx = 11, healthBarDy = 11;
        float scaleX = 3.5f;
        float scaleY = 2f;
        health.render(delta, healthBarX, healthBarY, healthBarDx, healthBarDy, healthBarWidth, healthBarHeight, scaleX, scaleY, batch, shape);

        float coinWidth = 64;
        float coinHeight = 64;
        float coinX = healthBarX;
        float coinY = healthBarY - 10 - coinHeight;
        batch.draw(coinTexture, coinX, coinY, coinWidth, coinHeight);

        float numCoinsScale = 2.5f;
        float numCoinsX = coinX + 10 + coinWidth;
        float numCoinsY = coinY + 50;
        font.getData().setScale(numCoinsScale);
        font.draw(batch,
            String.valueOf(coins),
            numCoinsX,
            numCoinsY);
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
