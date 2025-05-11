package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ProgressBarView {
    private Texture background;
    private Texture fill;
    private float curValue;
    private float maxValue;
    private BitmapFont font;

    public ProgressBarView(String backgroundPath, String fillPath, float curValue, float maxValue) {
        if (curValue > maxValue) {
            throw new IllegalArgumentException("curValue can't be greater than maxValue");
        }
        this.background = new Texture(Gdx.files.internal(backgroundPath));
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.fill = new Texture(Gdx.files.internal(fillPath));
        fill.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.curValue = curValue;
        this.maxValue = maxValue;
        this.font = new BitmapFont();
    }

    public void render(float delta, float x, float y, float dx, float dy, float width, float height, float scaleX, float scaleY,
                       SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.draw(background, x, y, width, height);
        float mlt = curValue / maxValue;
//        Gdx.app.log("ProgressBarView", "curValue: " + curValue + ", maxValue: " + maxValue);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(x + dx, y + dy, (width - 2 * dy), (height - 2 * dx));
        shapeRenderer.end();
        batch.begin();
        batch.draw(fill, x + dx, y + dy, (width - 2 * dy) * mlt, height - 2 * dx);
        font.getData().setScale(scaleX,  scaleY);
        font.draw(batch, (int)curValue + "/" + (int)maxValue, x + dx + 10, y + dy + (height - 2 * dx) / 2 + 12);
    }

    public void setCurValue(float curValue) {
        this.curValue = curValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void dispose() {
        background.dispose();
        fill.dispose();
    }
}
