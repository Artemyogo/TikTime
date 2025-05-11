package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public abstract class LivingEntityView extends AnimatedEntityView {
    protected Direction direction;
    protected LivingEntityState state;
    protected boolean isAttacked = false;

    protected LivingEntityView(float x, float y, float width, float height, Direction direction, LivingEntityState state, String atlasPath) {
        super(x, y, width, height, atlasPath);
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
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        if (!pause) {
            animManager.update(delta);
        }

        TextureRegion frame = animManager.getCurrentFrame();
//        Gdx.app.log(this.getClass().getSimpleName(), "frame: " + frame + " " + frame.getRegionWidth() + " " + frame.getRegionHeight());
//        if (frame.getTexture() == null) {
//            throw new RuntimeException("Texture could not be loaded");
//        }
        boolean flip = needFlipTexture();

        if (isAttacked) {
            batch.setColor(Color.RED);
        }

        Gdx.app.log(this.getClass().getSimpleName(), width + " " + height);
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

//        batch.draw(frame, x, y, width, height);

//        frame = loadAnimation("player-running", 0.1f).getKeyFrame(0);
//        batch.draw(frame,
//            x - width / 2f,
//            y - height / 2f,
//            width / 2f,
//            height / 2f,
//            width,
//            height,
//            flip ? -1 : 1,
//            1,
//            0
//        );

        if (isAttacked) {
            batch.setColor(Color.WHITE);
        }
    }

    private boolean needFlipTexture() {
        return direction == Direction.NORTH_WEST ||
            direction == Direction.WEST ||
            direction == Direction.SOUTH_WEST;
    }
}
