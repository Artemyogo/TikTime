package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.common.Pausable;
import com.tiktime.view.enteties.livingenteties.EntityView;
import com.tiktime.view.world.Renderable;

public class BulletView extends EntityView {
    // TODO: magic constant
    private final String texturePath = "bullet.png";
    private final TextureRegion texture = new TextureRegion(new Texture(texturePath));
    private final SpriteBatch batch;
    private final float rotationDeg;
    public BulletView(float x, float y, float width, float height, float rotationDeg, SpriteBatch batch) {
        super(x, y, width, height);
        this.batch = batch;
        this.rotationDeg = rotationDeg;
    }

    @Override
    public void render(float delta) {
        batch.draw(texture,
            x - width / 2f,
            y - height / 2f,
            width / 2f,
            height / 2f,
            width,
            height,
            1,
            1,
            rotationDeg);
    }
}
