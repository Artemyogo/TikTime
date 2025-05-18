package com.tiktime.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class EntityModel {
    private final int id;
    protected final Body body;

    public EntityModel(Body body, int id) {
        this.body = body;
        this.id = id;
    }

    public Body getBody() {
        return body;
    }

    public int getId() {
        return id;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
