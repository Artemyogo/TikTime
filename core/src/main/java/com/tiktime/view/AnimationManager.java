package com.tiktime.view;

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
        /// TODO i think that here should just start from the begining frames (stateTime to 0)
        /// TODO now i dont think so 09.05
        if (!name.equals(currentAnim)) {
            currentAnim = name;
            stateTime = 0;
        }
//        currentAnim = name;
//        stateTime = 0;
    }

    public TextureRegion getCurrentFrame() {
//        Gdx.app.log("AnimationManager", "Current frame: " + currentAnim);
        return animations.get(currentAnim).getKeyFrame(stateTime);
    }

    public void update(float delta) {
        stateTime += delta;
    }
}
