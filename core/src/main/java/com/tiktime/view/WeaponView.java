package com.tiktime.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public abstract class WeaponView extends AnimatedEntityView {
    protected float rotationDeg;
    protected final float baseAttackTicks = 100;
    protected float curAttackTicks = 0;
    protected boolean isAttack = false;
    protected final float weaponOffsetX; // Смещение вправо
    protected final float weaponOffsetY; // Смещение вверх

    protected WeaponView(float width, float height, float x, float y, float weaponOffsetX, float weaponOffsetY, String atlasPath) {
        super(x, y, width, height, atlasPath);
        this.weaponOffsetX = weaponOffsetX;
        this.weaponOffsetY = weaponOffsetY;
    }

    public void render(float delta, SpriteBatch batch) {
        update(delta);

        TextureRegion frame = animManager.getCurrentFrame();
        boolean flip = needFlipTexture();
        float xn = x + weaponOffsetX;
        float yn = y + weaponOffsetY;
        batch.draw(frame,
            xn - width / 2f,
            yn - height / 2f,
            getOriginX(xn),
            getOriginY(yn),
            width,
            height,
            1,
            flip ? -1 : 1,
            rotationDeg
        );
    }

    public float getOriginX(float x) {
//        return x - width / 2f;
        return width / 2f;
    }

    public float getOriginY(float y) {
//        return y - height / 2f;
        return height / 2f;
    }

    @Override
    public void update(float delta) {
        if (pause) {
            return;
        }

        animManager.update(delta);

        curAttackTicks -= delta;
        if (curAttackTicks <= 0) {
            this.isAttack = false;
            curAttackTicks = 0;
        }
    }

    public void setIsAttack(boolean isAttack) {
        this.isAttack = isAttack;
        if (isAttack) {
            curAttackTicks = baseAttackTicks;
        } else {
            curAttackTicks = 0;
        }
    }

    public void setRotationDeg(float rotationDeg) {
        this.rotationDeg = rotationDeg;
    }

    private boolean needFlipTexture() {
//        return false;
        return rotationDeg > 90 || rotationDeg < -90;
    }
}
