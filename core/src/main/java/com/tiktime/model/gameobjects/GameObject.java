package com.tiktime.model.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    private final String id;
    private final TextureRegion texture;

    private Vector2 textureOffset;
    private Vector2 position;
    private Rectangle hitbox;

    public GameObject(String id, float x, float y, float width, float height, TextureRegion texture) {
        this.id = id;
        this.hitbox = new Rectangle(x, y, width, height);
        this.texture = texture;

        this.textureOffset.set(
            (hitbox.width - texture.getRegionWidth()) / 2,
            (hitbox.height - texture.getRegionHeight()) / 2
        );
    }

    public Vector2 getPosition() { return position; }

    public void setPosition(float x, float y) {
        position.set(x, y);
        hitbox.setPosition(x, y);
    }
}
