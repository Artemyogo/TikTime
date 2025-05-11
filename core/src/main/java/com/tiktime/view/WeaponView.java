package com.tiktime.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public abstract class WeaponView extends AnimatedEntityView {
    protected float originX;
    protected float originY;
    protected float angleDeg;
    protected boolean isFire = false;

    protected WeaponView(float width, float height, float x, float y, float originX, float originY, String atlasPath) {
        super(x, y, width, height, atlasPath);
        this.originX = originX;
        this.originY = originY;
    }

    public void render(float delta, SpriteBatch batch) {
        if (!pause) {
            animManager.update(delta);
        }

        TextureRegion frame = animManager.getCurrentFrame();
        boolean flip = needFlipTexture();
        batch.draw(frame,
            x - width / 2f,
            y - height / 2f,
            originX,
            originY,
            width,
            height,
            flip ? -1 : 1,
            1,
            angleDeg
        );
    }

    boolean needFlipTexture() {
        return angleDeg > 90 && angleDeg < 270;
    }
}
