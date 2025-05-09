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

import static com.tiktime.model.consts.ScreenConstants.PPM;

public abstract class EntityView {
    protected final TextureAtlas atlas;
    protected AnimationManager animManager;
    protected Direction direction = Direction.SOUTH;
    /// TODO CHANGE TO IDLE
//    protected EntityState state = EntityState.IDLE;
    protected EntityState state = EntityState.RUNNING;
    protected float x, y, width, height;
    protected boolean isAttacked = false;

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

    public void setCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSizes(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected Animation<TextureRegion> loadAnimation(String pathPrefix, float frameDuration) {
        ///  TODO
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            if (region.name.startsWith(pathPrefix)) {
                regions.add(region);
            }
//            Gdx.app.log("REGION", "Name: " + region.name);
        }
//        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(pathPrefix);
//
//        if (regions.size == 0) {
//            regions.sort((a, b) -> a.name.compareTo(b.name));
//            for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
//                Gdx.app.log("REGION", "Name: " + region.name);
//            }
//
//            Gdx.app.log("ERROR", "atlas is empty");
////            Gdx.app.exit();
////            System.exit(-1);
////            while (true) {
////                System.out.println(pathPrefix);
////            }
//            throw new GdxRuntimeException( atlas.findRegions("player-running-0").isEmpty() + "   " + "   " +
//                atlas.findRegions(pathPrefix).isEmpty() + "||" + atlas.getRegions().get(0).name + "||" +
//                "   Animation doesnt foundHIHI: " + pathPrefix);
////            atlas.dispose();
//        }

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
            x - width*PPM/2f,
            y - height*PPM/2f,
            width*PPM/2f,
            height*PPM/2f,
            width * PPM,
            height * PPM,
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
