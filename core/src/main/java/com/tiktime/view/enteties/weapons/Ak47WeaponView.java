package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.MagicConstants;

public class Ak47WeaponView extends WeaponView {
    private static String atlasPath = MagicConstants.AK47_ATLAS_PATH;
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
        float attackFrameDuration = MagicConstants.AK47_ATTACK_FRAME_DURATION;
        animManager.add(MagicConstants.AK47_ATTACK_ANIMATION_NAME, getAnimation(MagicConstants.AK47_ATTACK_ANIMATION_NAME, attackFrameDuration, Animation.PlayMode.LOOP));
        float idleFrameDuration = MagicConstants.AK47_IDLE_FRAME_DURATION;
        animManager.add(MagicConstants.AK47_IDLE_ANIMATION_NAME, getAnimation(MagicConstants.AK47_IDLE_ANIMATION_NAME, idleFrameDuration, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateAnimation() {
//        Gdx.app.log("Ak47WepanoVIw", isAttacking ? "attacking" : "idle");
        /// TODO BEWARE animName should be translated to the correct one
        String animName;
        if (isAttacking)
            animName = MagicConstants.AK47_ATTACK_ANIMATION_NAME;
        else
            animName = MagicConstants.AK47_IDLE_ANIMATION_NAME;
        animManager.set(animName);
    }
}
