package com.tiktime.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ak47WeaponView extends WeaponView {
    private static String atlasPath = "animations/ak47Weapon.atlas";
    protected Ak47WeaponView(float width, float height, float x, float y, float originX, float originY) {
        super(width, height, x, y, originX, originY, atlasPath);
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
        float runFrameDuration  = 0.1f;

        animManager.add("AK47-SpriteSheet", getAnimation("AK47-SpriteSheet", runFrameDuration, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateAnimation() {
        /// TODO BEWARE animName should be translated to the correct one
        String animName;
        if (isFire)
            animName = "AK47-SpriteSheet";
//        else
//        animManager.set(animName);
    }
}
