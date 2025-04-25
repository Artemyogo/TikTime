package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Comparator;

public abstract class EntityView {
    protected final TextureAtlas atlas;
    protected AnimationManager animManager;
    protected Direction direction = Direction.SOUTH;
    protected EntityState state = EntityState.IDLE;
    protected float x, y, width, height;
    protected boolean isAttacked = false;

    public EntityView(float x, float y, float width, float height, String atlasPath) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        this.animManager = new AnimationManager();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadAnimations();
    }

    public EntityView(float x, float y, float width, float height, Direction direction, EntityState state, String atlasPath) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        this.animManager = new AnimationManager();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.state = state;
        loadAnimations();
    }

    protected abstract void loadAnimations();

    public void updateState(Direction direction, EntityState state) {
        boolean stateChanged = (this.state != state);
        boolean directionChanged = (this.direction != direction);

        this.state = state;
        this.direction = direction;

        if (stateChanged || directionChanged) {
            updateAnimation();
        }
    }

    protected Animation<TextureRegion> loadAnimation(String pathPrefix, float frameDuration) {
        ///  TODO
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(pathPrefix);

        if (regions.size == 0) {
            atlas.dispose();
            throw new GdxRuntimeException("Animation doesnt found: " + pathPrefix);
        }

        regions.sort(Comparator.comparing(region -> region.name));

        Animation<TextureRegion> animation = new Animation<>(frameDuration, regions);
        animation.setPlayMode(state.getPlayMode());

        return animation;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    protected void updateAnimation() {
        /// TODO BEWARE animName should be translated to the correct one
        String animName = String.format("%s_%s",
            state.name().toLowerCase(),
            getDirectionKey(this.direction));

        animManager.set(animName);
    }

    protected String getDirectionKey(Direction direction) {
        switch (direction) {
            case NORTH:
                return "n";
            case NORTH_EAST:
                return "ne";
            case EAST:
                return "e";
            case SOUTH_EAST:
                return "se";
            case SOUTH:
                return "s";
            case SOUTH_WEST:
                return "sw";
            case WEST:
                return "w";
            case NORTH_WEST:
                return "nw";
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(float delta, SpriteBatch batch) {
        animManager.update(delta);
        TextureRegion frame = animManager.getCurrentFrame();
        boolean flip = needFlipTexture();

        if (isAttacked) {
            batch.setColor(Color.RED);
        }

        batch.draw(frame,
            x - frame.getRegionWidth()/2f,
            y - frame.getRegionHeight()/2f,
            frame.getRegionWidth()/2f,
            frame.getRegionHeight()/2f,
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

    private boolean needFlipTexture() {
        return direction == Direction.NORTH_WEST ||
            direction == Direction.WEST ||
            direction == Direction.SOUTH_WEST;
    }
}
