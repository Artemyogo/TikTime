package com.tiktime.view;

import com.badlogic.gdx.graphics.g2d.Animation;

public enum LivingEntityState {
    IDLE(Animation.PlayMode.LOOP),
    WALKING(Animation.PlayMode.LOOP),
    RUNNING(Animation.PlayMode.LOOP),
    DYING(Animation.PlayMode.NORMAL);

    final Animation.PlayMode playMode;

    LivingEntityState(Animation.PlayMode playMode) {
        this.playMode = playMode;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }
}
