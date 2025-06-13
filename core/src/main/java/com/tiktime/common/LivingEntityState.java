package com.tiktime.common;

import com.badlogic.gdx.graphics.g2d.Animation;

public enum LivingEntityState {
    IDLE(Animation.PlayMode.LOOP),
    RUNNING(Animation.PlayMode.LOOP),
    ATTACKING(Animation.PlayMode.LOOP),
    DYING(Animation.PlayMode.NORMAL);

    public final Animation.PlayMode playMode;

    LivingEntityState(Animation.PlayMode playMode) {
        this.playMode = playMode;
    }
}
