package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.MagicConstants;

public class Ak47WeaponView extends WeaponView {
    private static String atlasPath = MagicConstants.AK47_ATLAS_PATH;
    private final float attackFrameDuration;
    protected Ak47WeaponView(float x, float y, SpriteBatch batch, float attackFrameDuration) {
        super(GameConfig.getWeaponConfig(WeaponType.AK47).getWidth(),
            GameConfig.getWeaponConfig(WeaponType.AK47).getHeight(),
            x, y,
            GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetX(),
            GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetY(),
            atlasPath, batch);
        this.attackFrameDuration = attackFrameDuration;
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
        animManager.add(MagicConstants.AK47_ATTACK_ANIMATION_NAME, getAnimation(MagicConstants.AK47_ATTACK_ANIMATION_NAME, attackFrameDuration, Animation.PlayMode.NORMAL));
        float idleFrameDuration = MagicConstants.AK47_IDLE_FRAME_DURATION;
        animManager.add(MagicConstants.AK47_IDLE_ANIMATION_NAME, getAnimation(MagicConstants.AK47_IDLE_ANIMATION_NAME, idleFrameDuration, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateAnimation() {
        String animName;
        if (isAttacking) {
            if (animManager.isAnimationFinished())
                animManager.resetAnimation();
            animName = MagicConstants.AK47_ATTACK_ANIMATION_NAME;
        } else if (animManager.getCurrentAnimation() == MagicConstants.AK47_ATTACK_ANIMATION_NAME && !animManager.isAnimationFinished()) {
            animName = MagicConstants.AK47_ATTACK_ANIMATION_NAME;
        } else {
            animName = MagicConstants.AK47_IDLE_ANIMATION_NAME;
        }
        animManager.set(animName);
    }
}
