package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ProgressBarView {
    private Texture background;
    private Texture fill;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float width;
    private float height;
    private float curValue;
    private float maxValue;
    private final ShapeRenderer shapeRenderer;

    public ProgressBarView(String backgroundPath, String fillPath, float x, float y, float dx, float dy, float width, float height, float curValue, float maxValue) {
        this.background = new Texture(Gdx.files.internal(backgroundPath));
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.fill = new Texture(Gdx.files.internal(fillPath));
        fill.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;
        this.curValue = curValue;
        this.maxValue = maxValue;
        this.shapeRenderer = new ShapeRenderer();
        if (curValue > maxValue) {
            throw new IllegalArgumentException("curValue can't be greater than maxValue");
        }
    }

    public void render(float delta, SpriteBatch batch) {
        batch.draw(background, x, y, width, height);
        float mlt = curValue / maxValue;
        Gdx.app.log("ProgressBarView", "curValue: " + curValue + ", maxValue: " + maxValue);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(x + dx, y + dy, (width - 2 * dy), height - 2 * dx);
        shapeRenderer.end();
        batch.begin();
        batch.draw(fill, x + dx, y + dy, (width - 2 * dy) * mlt, height - 2 * dx);
    }

    public void dispose() {
        background.dispose();
        fill.dispose();
    }
}
