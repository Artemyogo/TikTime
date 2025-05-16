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

    public static Direction getDirection(Vector2 dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Direction argument was null");
        }

        if (Math.abs(dir.x) > 1 || Math.abs(dir.y) > 1) {
            throw new RuntimeException("Invalid direction");
        }

//        Gdx.app.log("WorldController", "Direction: " + dir);
        if (dir.x == 0)
            throw new IllegalArgumentException("Invalid direction, shouldnt change direction");

        if (dir.x > 0) {
            return Direction.EAST;
        }

        return Direction.WEST;
    }
}

