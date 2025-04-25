package com.tiktime.view;

import com.badlogic.gdx.graphics.g2d.Animation;

public enum EntityState {
    IDLE(Animation.PlayMode.LOOP),
    WALKING(Animation.PlayMode.LOOP),
    RUNNING(Animation.PlayMode.LOOP),
    /// TODO DONT touch attacking state, hard to realize this state, just change isAttacking boolean variable
    ATTACKING(Animation.PlayMode.LOOP),
    /// THIS ALSO
    DYING(Animation.PlayMode.NORMAL);

    final Animation.PlayMode playMode;

    EntityState(Animation.PlayMode playMode) {
        this.playMode = playMode;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }
}
