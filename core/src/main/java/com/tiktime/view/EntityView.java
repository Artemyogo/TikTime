package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public abstract class EntityView {
    protected final TextureAtlas atlas;
    protected AnimationManager animManager;
    protected Direction direction = Direction.SOUTH;
    /// TODO CHANGE TO IDLE
    protected EntityState state = EntityState.IDLE;
//    protected EntityState state = EntityState.RUNNING;
    protected float x, y, width, height;
    protected boolean isAttacked = false;
    protected boolean pause = false;

    public EntityView(float x, float y, float width, float height, String atlasPath) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        if (atlas.getRegions().size == 0) {
            Gdx.app.log("ERROR", "atlas is empty");
            Gdx.app.exit();
        }
        this.animManager = new AnimationManager();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadAnimations();
    }

    public void setPause(boolean paused) {
        this.pause = paused;
    }

    public EntityView(float x, float y, float width, float height, Direction direction, EntityState state, String atlasPath) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
//        if (atlas.getRegions().size > 0) {
//            Gdx.app.log("ERROR", "atlas is empty");
//            Gdx.app.exit();
//        }
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

    public void setDirection(Direction direction) {
        boolean directionChanged = (this.direction != direction);

        this.direction = direction;

        if (directionChanged) {
            updateAnimation();
        }
    }

    public void setState(EntityState state) {
        boolean stateChanged = (this.state != state);

        this.state = state;

        if (stateChanged) {
            updateAnimation();
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSizes(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected Animation<TextureRegion> loadAnimation(String pathPrefix, float frameDuration) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            if (region.name.startsWith(pathPrefix)) {
                regions.add(region);
            }
        }

        regions.sort(Comparator.comparing(region -> region.index));

        Animation<TextureRegion> animation = new Animation<>(frameDuration, regions);
        animation.setPlayMode(state.getPlayMode());

        return animation;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    protected void updateAnimation() {
        /// TODO BEWARE animName should be translated to the correct one
        String animName = String.format("%s-%s",
            "player",
            state.name().toLowerCase());
            //getDirectionKey(this.direction));

//        Gdx.app.log("PIWOO", "animName: " + animName);
//        Gdx.app.log(animName, animName);
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

    public void render(float delta, SpriteBatch batch) {
        if (!pause) {
            animManager.update(delta);
        }

//        Gdx.app.log(this.getClass().getSimpleName(), "Rendering : x:" + x + ", y:" + y + ", w:" + width + ", h:" + height);
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

    private boolean needFlipTexture() {
        return direction == Direction.NORTH_WEST ||
            direction == Direction.WEST ||
            direction == Direction.SOUTH_WEST;
    }
}
