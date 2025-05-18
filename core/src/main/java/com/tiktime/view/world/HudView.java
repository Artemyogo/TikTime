package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.view.ProgressBarView;
import com.tiktime.view.enteties.livingenteties.PlayerView;

import javax.swing.text.View;

public class HudView implements Renderable, Disposable {
    private final String backgroundPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueBar_128x16.png";
    private final String fillPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/ValueRed_120x8.png";
    private final String fontPath = "flat-earth/skin/font-title-export.fnt";
    private final String coinPath = "Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/CoinIcon_16x18.png";
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final ShapeRenderer hudShape;
    private final SpriteBatch hudBatch;
    private final Viewport viewport;
    private PlayerView playerView;
    ProgressBarView health;
    Texture coinTexture;
    private boolean isInDoor;
    private int coins;

    public HudView(Viewport viewport, OrthographicCamera camera, PlayerView playerView, int coins) {
        this.playerView = playerView;

        this.viewport = viewport;
        this.camera = camera;
        hudShape = new ShapeRenderer();
        hudBatch = new SpriteBatch();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.coinTexture = new Texture(coinPath);
        this.coins = coins;
        this.health = new ProgressBarView(backgroundPath, fillPath, playerView.getCurHealth(), playerView.getMaxHealth());
        this.font = new BitmapFont();
    }

    @Override
    public void dispose() {
        font.dispose();
        coinTexture.dispose();
        health.dispose();
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void render(float delta) {
        update(delta);

        hudBatch.setProjectionMatrix(camera.combined);
        hudShape.setProjectionMatrix(camera.combined);

        hudBatch.begin();
        float healthBarWidth = 300;
        float healthBarHeight = 40;
        float healthBarX = viewport.getScreenWidth() - 10 - healthBarWidth;
        float healthBarY = viewport.getScreenHeight() - 10 - healthBarHeight;
        float healthBarDx = 11, healthBarDy = 11;
        float scaleX = 3.5f;
        float scaleY = 2f;
        health.render(delta, healthBarX, healthBarY, healthBarDx, healthBarDy, healthBarWidth, healthBarHeight, scaleX, scaleY,
            hudBatch, hudShape);

        float coinWidth = 64;
        float coinHeight = 64;
        float coinX = healthBarX;
        float coinY = healthBarY - 10 - coinHeight;
        hudBatch.draw(coinTexture, coinX, coinY, coinWidth, coinHeight);

        float numCoinsScale = 2.5f;
        float numCoinsX = coinX + 10 + coinWidth;
        float numCoinsY = coinY + 50;
        font.getData().setScale(numCoinsScale);
        font.draw(hudBatch,
            String.valueOf(coins),
            numCoinsX,
            numCoinsY);

        if (isInDoor) {
            printMessageNearDoor();
        }
        hudBatch.end();
    }

    public void update(float delta) {
        health.setCurValue(playerView.getCurHealth());
        health.setMaxValue(playerView.getMaxHealth());
    }

    public void setIsInDoor(boolean isInDoor) {
        this.isInDoor = isInDoor;
    }

    public void printMessageNearDoor() {
        float x = viewport.getScreenWidth() / 2f - 170f, y = 200f;
        BitmapFont font = new BitmapFont();
        font.getData().scale(2f);
        font.draw(hudBatch, "Press 'E' to exit door", x, y);
    }
}
