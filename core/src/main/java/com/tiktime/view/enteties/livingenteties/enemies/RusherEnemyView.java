package com.tiktime.view.enteties.livingenteties.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;

public class RusherEnemyView extends EnemyView {
    public static final String atlasPath = "animations/rusher.atlas";
    public RusherEnemyView(float x, float y, float width, float height, int curHealth, int maxHealth,
                           int id, Direction direction, LivingEntityState livingEntityState,
                           SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, id, direction, livingEntityState, EnemyType.RUSHER, atlasPath, batch);
        loadAnimations();
        updateAnimation();
    }

    @Override
    protected void loadAnimations() {
        float runFrameDuration  = 0.1f;
        animManager.add("rusher-running", getAnimation("running-e", runFrameDuration, LivingEntityState.RUNNING.playMode));
        animManager.add("rusher-idle", getAnimation("idle-e", runFrameDuration, LivingEntityState.IDLE.playMode));

        /// TODO IDK WHAT TO SET
        float attackFrameDuration  = 0.1f;
        animManager.add("rusher-attacking", getAnimation("attacking-e", attackFrameDuration,
            LivingEntityState.ATTACKING.playMode));

        float deathFrameDuration = 0.1f;
        animManager.add("rusher-dying", getAnimation("death-e", deathFrameDuration, LivingEntityState.DYING.playMode));
    }

    @Override
    protected void updateAnimation() {
        String animName = String.format("%s-%s", "rusher", state.name().toLowerCase());
        animManager.set(animName);
    }
}
