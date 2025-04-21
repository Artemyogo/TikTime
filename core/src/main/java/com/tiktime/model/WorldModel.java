package com.tiktime.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class WorldModel {
    World world;
    int[][] grid;

    WorldModel(int h, int w){
        grid = new int[h][w];
        world = new World(new Vector2(0, 0), true);
    }

}
