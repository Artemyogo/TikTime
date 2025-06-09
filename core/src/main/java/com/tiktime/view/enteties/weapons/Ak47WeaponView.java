package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;

public class Ak47WeaponView extends WeaponView {
    private static String atlasPath = "animations/ak47weapon.atlas";
    protected Ak47WeaponView(float x, float y, SpriteBatch batch) {
        super(GameConfig.getWeaponConfig(WeaponType.AK47).getWidth(),
            GameConfig.getWeaponConfig(WeaponType.AK47).getHeight(),
            x, y,
            GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetX(),
            GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetY(),
            atlasPath, batch);
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
//        float attackFrameDuration = baseAttackTicks / getRegions("ak47-attacking").size;
        // TODO: magic constants
        float attackFrameDuration = 0.04f;
        animManager.add("ak47-attacking", getAnimation("ak47-attacking", attackFrameDuration, Animation.PlayMode.LOOP));
        float idleFrameDuration = 0.1f;
        animManager.add("ak47-idle", getAnimation("ak47-idle", idleFrameDuration, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateAnimation() {
//        Gdx.app.log("Ak47WepanoVIw", isAttacking ? "attacking" : "idle");
        /// TODO BEWARE animName should be translated to the correct one
        String animName;
        if (isAttacking)
            animName = "ak47-attacking";
        else
            animName = "ak47-idle";
        animManager.set(animName);
    }
}
