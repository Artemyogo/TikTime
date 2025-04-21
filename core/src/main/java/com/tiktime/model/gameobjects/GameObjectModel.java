package com.tiktime.model.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObjectModel {
    private final String id;

    private Vector2 textureOffset;
    private Vector2 position;
    private Rectangle hitbox;

    public GameObjectModel(String id, float x, float y, float width, float height) {
        this.id = id;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public Vector2 getPosition() { return position; }

    public void setPosition(float x, float y) {
        position.set(x, y);
        hitbox.setPosition(x, y);
    }
}
