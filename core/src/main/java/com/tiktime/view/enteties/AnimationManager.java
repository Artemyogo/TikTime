package com.tiktime.view.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private final Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private String currentAnim;
    private float stateTime;

    public void add(String name, Animation<TextureRegion> anim) {
        animations.put(name, anim);
    }

    public void set(String name) {
        if (!name.equals(currentAnim)) {
            currentAnim = name;
            stateTime = 0;
        }
    }

    public TextureRegion getCurrentFrame() {
        return animations.get(currentAnim).getKeyFrame(stateTime);
    }

    public String getCurrentAnimation() {
        return currentAnim;
    }

    public boolean isAnimationFinished() {
        if (animations.get(currentAnim) == null) {
            return true;
        }

        return animations.get(currentAnim).isAnimationFinished(stateTime);
    }

    public void resetAnimation() {
        stateTime = 0;
    }

    public void update(float delta) {
        stateTime += delta;
    }
}
