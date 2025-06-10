package com.tiktime.view.enteties.livingenteties.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.Direction;
import com.tiktime.common.EnemyType;
import com.tiktime.common.LivingEntityState;
import com.tiktime.common.MagicConstants;

public class RusherEnemyView extends EnemyView {
    private final float runFrameDuration;
    public static final String atlasPath = MagicConstants.RUSHER_ATLAS_PATH;
    public RusherEnemyView(float x, float y, float width, float height, int curHealth, int maxHealth,
                           int id, Direction direction, LivingEntityState livingEntityState,
                           SpriteBatch batch, float runFrameDuration) {
        super(x, y, width, height, curHealth, maxHealth, id, direction, livingEntityState, EnemyType.RUSHER, atlasPath, batch);
        this.runFrameDuration = runFrameDuration;
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
//        float runFrameDuration  = 0.1f;
        animManager.add(MagicConstants.RUSHER_RUNNING_ANIMATION_NAME, getAnimation("running-e", runFrameDuration, LivingEntityState.RUNNING.playMode));
        animManager.add(MagicConstants.RUSHER_IDLE_ANIMATION_NAME, getAnimation("idle-e", runFrameDuration, LivingEntityState.IDLE.playMode));

        // TODO IDK WHAT TO SET
        float attackFrameDuration  = MagicConstants.RUSHER_ATTACK_FRAME_DURATION;
        animManager.add(MagicConstants.RUSHER_ATTACKING_ANIMATION_NAME, getAnimation("attacking-e", attackFrameDuration,
            LivingEntityState.ATTACKING.playMode));

        float deathFrameDuration = MagicConstants.RUSHER_DEATH_FRAME_DURATION;
        animManager.add(MagicConstants.RUSHER_DYING_ANIMATION_NAME, getAnimation("death-e", deathFrameDuration, LivingEntityState.DYING.playMode));
    }

    @Override
    protected void updateAnimation() {
        String animName = String.format("%s-%s", "rusher", state.name().toLowerCase());
        animManager.set(animName);
//        Gdx.app.log("RusherEnemyView", state.name().toLowerCase());
    }
}
