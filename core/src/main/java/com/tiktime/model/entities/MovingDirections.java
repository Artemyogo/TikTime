package com.tiktime.model.entities;

import com.badlogic.gdx.math.Vector2;

import java.util.Collection;

public enum MovingDirections implements Comparable<MovingDirections>{
    UP(new Vector2(0, 1)),
    DOWN(new Vector2(0, -1)),
    LEFT(new Vector2(-1, 0)),
    RIGHT(new Vector2(1, 0));

    private final Vector2 direction;
    MovingDirections(Vector2 direction){
        this.direction = direction;
    }
    public static Vector2 combine(Collection<MovingDirections> directions){
        Vector2 res = new Vector2(0, 0);
        for(MovingDirections x : directions)
            res.add(x.direction);
        return res;
    }

}
