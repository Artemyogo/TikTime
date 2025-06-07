package com.tiktime.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.BodyManager;

public abstract class EntityModel implements Entitiable {
    public static int idNext = 0;
    private final int id;
    protected final Body body;
    protected final BodyManager bodyManager;

    public EntityModel(Body body, BodyManager bodyManager, int id) {
        this.bodyManager = bodyManager;
        this.body = body;
        this.id = id;
        setBody();
    }

    public Body getBody() {
        return body;
    }

    public void deleteBody() {
        bodyManager.setToDelete(body);
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
}
