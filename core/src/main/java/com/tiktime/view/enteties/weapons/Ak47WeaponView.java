package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.model.configs.GameConfig;

public class Ak47WeaponView extends WeaponView {
    private static String atlasPath = "animations/ak47weapon.atlas";
    protected Ak47WeaponView(float x, float y, SpriteBatch batch) {
        super(GameConfig.getAk47WeaponConfig().getWidth(),
            GameConfig.getAk47WeaponConfig().getHeight(),
            x, y,
            GameConfig.getAk47WeaponConfig().getOffsetX(),
            GameConfig.getAk47WeaponConfig().getOffsetY(),
            atlasPath, batch);
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
        float attackFrameDuration = baseAttackTicks / getRegions("ak47-attacking").size;
        animManager.add("ak47-attacking", getAnimation("ak47-attacking", attackFrameDuration, Animation.PlayMode.NORMAL));
        float idleFrameDuration = 0.1f;
        animManager.add("ak47-idle", getAnimation("ak47-idle", idleFrameDuration, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateAnimation() {
        /// TODO BEWARE animName should be translated to the correct one
        String animName;
        if (isAttack)
            animName = "ak47-attacking";
        else
            animName = "ak47-idle";
        animManager.set(animName);
    }
}
