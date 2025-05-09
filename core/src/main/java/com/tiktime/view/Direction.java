package com.tiktime.view;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    EAST(0),      // →
    NORTH_EAST(45),  // ↗
    NORTH(90),     // ↑
    NORTH_WEST(135), // ↖
    WEST(180),     // ←
    SOUTH_WEST(225), // ↙
    SOUTH(270),    // ↓
    SOUTH_EAST(315); // ↘

    private final float angleDeg;

    Direction(float angleDeg) {
        this.angleDeg = angleDeg;
    }

    public boolean isRight() {
        return angleDeg < 90 || angleDeg > 270; // Все что не влево
    }
}

