package com.tiktime.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class EntityView {
    protected float x, y, width, height;
    protected boolean pause = false;

    public EntityView(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract void render(float delta, SpriteBatch batch);
}
