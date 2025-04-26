package com.tiktime.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class StaticObjectView {
    protected TextureRegion texture;
    float x, y, width, height;

    public StaticObjectView(TextureRegion texture) {

    }

    public void render(float delta, SpriteBatch batch) {
        batch.draw(texture,
            x,
            y,
            width,
            height);
    }

    protected void loadTexture(String texturePath) {
        texture = Assets.getTexture(texturePath);
    }
}
