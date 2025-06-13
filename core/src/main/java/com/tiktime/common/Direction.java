package com.tiktime.common;

import com.badlogic.gdx.math.Vector2;

import java.util.Collection;

public enum Direction implements Comparable<Direction>{
    UP(new Vector2(0, 1)),
    NORTH(new Vector2(0, 1)),     // ↑

    DOWN(new Vector2(0, -1)),
    SOUTH(new Vector2(0, -1)),    // ↓

    LEFT(new Vector2(-1, 0)),
    WEST(new Vector2(-1, 0)),     // ←

    RIGHT(new Vector2(1, 0)),
    EAST(new Vector2(1, 0));      // →

    private final Vector2 direction;
    Direction(Vector2 direction){
        this.direction = direction;
    }
    public static Vector2 combine(Collection<Direction> directions){
        Vector2 res = new Vector2(0, 0);
        for(Direction x : directions)
            res.add(x.direction);
        return res;
    }

    public static Direction getDirection(Vector2 dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Direction argument was null");
        }

        if (Math.abs(dir.x) > 1 || Math.abs(dir.y) > 1) {
            throw new RuntimeException("Invalid direction");
        }

        if (dir.x >= 0) {
            return Direction.EAST;
        }

        return Direction.WEST;
    }
}
