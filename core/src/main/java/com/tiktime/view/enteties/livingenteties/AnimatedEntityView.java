package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tiktime.view.enteties.AnimationManager;

import java.util.Comparator;

public abstract class AnimatedEntityView extends EntityView {
    protected final TextureAtlas atlas;
    protected final AnimationManager animManager;
    protected final SpriteBatch batch;

    public AnimatedEntityView(float x, float y, float width, float height, String atlasPath, SpriteBatch batch) {
        super(x, y, width, height);
        this.batch = batch;
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        if (atlas.getRegions().size == 0) {
            throw new RuntimeException("Unable to load atlas");
        }
        this.animManager = new AnimationManager();
    }

    public Array<TextureAtlas.AtlasRegion> getRegions(String pathPrefix) {
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            if (region.name.startsWith(pathPrefix)) {
                regions.add(region);
            }
        }

        return regions;
    }

    protected Animation<TextureRegion> getAnimation(String pathPrefix, float frameDuration, Animation.PlayMode animationPlayMode) {
        Array<TextureAtlas.AtlasRegion> regions = getRegions(pathPrefix);
        regions.sort(Comparator.comparing(region -> region.index));

        Animation<TextureRegion> animation = new Animation<>(frameDuration, regions);
        animation.setPlayMode(animationPlayMode);

        return animation;
    }

    protected abstract void loadAnimations();

    protected abstract void updateAnimation();

    public abstract void update(float delta);
}
