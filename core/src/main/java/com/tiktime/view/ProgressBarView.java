package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProgressBarView {
    Texture background;
    Texture fill;
    float x;
    float y;
    float width;
    float height;
    float curValue;
    float maxValue;

    public ProgressBarView(String backgroundPath, String fillPath, float x, float y, float width, float height, float curValue, float maxValue) {
        this.background = new Texture(Gdx.files.internal(backgroundPath));
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.fill = new Texture(Gdx.files.internal(fillPath));
        fill.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.curValue = curValue;
        this.maxValue = maxValue;
        if (curValue > maxValue) {
            throw new IllegalArgumentException("curValue can't be greater than maxValue");
        }
    }

    public void render(float delta, SpriteBatch batch) {
        batch.draw(background, x, y, width, height);
        batch.draw(fill, x + 0, y, width, height);
    }

    public void dispose() {
        background.dispose();
        fill.dispose();
    }
}
