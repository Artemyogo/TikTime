package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.view.enteties.livingenteties.AnimatedEntityView;

public abstract class WeaponView extends AnimatedEntityView {
    protected boolean isAttacking = false;
    protected float rotationDeg;
    protected float offsetX;
    protected float offsetY;

    protected WeaponView(float width, float height, float x, float y, float offsetX, float offsetY, String atlasPath,
                         SpriteBatch batch) {
        super(x, y, width, height, atlasPath, batch);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void render(float delta) {
        update(delta);

        TextureRegion frame = animManager.getCurrentFrame();
        boolean flip = needFlipTexture();
        batch.draw(frame,
            x - width / 2f,
            y - height / 2f,
            width / 2f + offsetX,
            height / 2f + offsetY,
            width,
            height,
            1,
            flip ? -1 : 1,
            rotationDeg
        );
    }

    @Override
    public void update(float delta) {
        if (pause) {
            return;
        }

        animManager.update(delta);
        updateAnimation();
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public void setRotationDeg(float rotationDeg) {
        this.rotationDeg = rotationDeg;
    }

    private boolean needFlipTexture() {
        return rotationDeg > 90 || rotationDeg < -90;
    }
}
