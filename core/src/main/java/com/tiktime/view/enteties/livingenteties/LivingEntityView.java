package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyView;

public abstract class LivingEntityView extends HealthEntityView {
    protected Direction direction;
    protected LivingEntityState state;
    boolean isAttacked = false;
    protected float attackFrameDuration, speedFrameDuration;

    protected LivingEntityView(float x, float y, float width, float height, int curHealth, int maxHealth,
                               Direction direction, LivingEntityState state, String atlasPath,
                               SpriteBatch batch, float speedFrameDuration, float attackFrameDuration) {
        super(x, y, width, height, curHealth, maxHealth, atlasPath, batch);
        this.direction = direction;
        this.state = state;
        this.speedFrameDuration = speedFrameDuration;
        this.attackFrameDuration = attackFrameDuration;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setState(LivingEntityState state) {
        boolean stateChanged = (this.state != state);
        this.state = state;
        if (stateChanged) {
            updateAnimation();
        }
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    @Override
    public void update(float delta) {
        if (pause)
            return;

        animManager.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        TextureRegion frame = animManager.getCurrentFrame();
        boolean flip = needFlipTexture();

        if (isAttacked) {
            batch.setColor(Color.RED);
        }

        batch.draw(frame,
            x - width / 2f,
            y - height / 2f,
            width / 2f,
            height / 2f,
            width,
            height,
            flip ? -1 : 1,
            1,
            0
        );

        if (isAttacked) {
            batch.setColor(Color.WHITE);
        }
    }

    protected boolean needFlipTexture() {
        return direction == Direction.WEST;
    }
}
