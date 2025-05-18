package com.tiktime.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class EntityModel implements Entitiable {
    private final int id;
    protected final Body body;
    protected float width;
    protected float height;

    public EntityModel(Body body, float width, float height, int id) {
        this.body = body;
        this.width = width;
        this.height = height;
        this.id = id;
        setBody();
    }

    public Body getBody() {
        return body;
    }

    protected void setBody() {
        body.setUserData(this);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
