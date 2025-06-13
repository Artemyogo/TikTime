package com.tiktime.common.sounds;

import com.badlogic.gdx.utils.TimeUtils;

public class SoundLimiter {
    private long lastPlayed = -999999999;
    private final long cooldownMillis;

    public SoundLimiter(long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }

    public boolean canPlay() {
        long now = TimeUtils.millis();
        if (now - lastPlayed > cooldownMillis) {
            lastPlayed = now;
            return true;
        }
        return false;
    }
}
