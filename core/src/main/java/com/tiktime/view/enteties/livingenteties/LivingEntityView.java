package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.view.enteties.Direction;

public abstract class LivingEntityView extends AnimatedEntityView {
    protected Direction direction;
    protected LivingEntityState state;
    protected final float baseAttackedTicks = 100;
    protected float curAttackedTicks = 0;
    boolean isAttacked = false;

    protected LivingEntityView(float x, float y, float width, float height, Direction direction, LivingEntityState state, String atlasPath,
                               SpriteBatch batch) {
        super(x, y, width, height, atlasPath, batch);
        this.direction = direction;
        this.state = state;
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
        if (isAttacked) {
            curAttackedTicks = baseAttackedTicks;
        } else {
            curAttackedTicks = 0;
        }
    }

    @Override
    public void update(float delta) {
        if (pause)
            return;

        animManager.update(delta);

        curAttackedTicks -= delta;
        if (curAttackedTicks <= 0) {
            this.isAttacked = false;
            curAttackedTicks = 0;
        }
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
        return direction == Direction.NORTH_WEST ||
            direction == Direction.WEST ||
            direction == Direction.SOUTH_WEST;
    }
}
