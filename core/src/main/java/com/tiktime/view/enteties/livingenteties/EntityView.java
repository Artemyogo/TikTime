package com.tiktime.view.enteties.livingenteties;

import com.tiktime.common.Pausable;
import com.tiktime.view.world.Renderable;

public abstract class EntityView implements Pausable, Renderable {
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setPaused(boolean pause) {
        this.pause = pause;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public abstract void render(float delta);
}
