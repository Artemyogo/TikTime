package com.tiktime.model.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashSet;

public class BodyManager {
    private final World world;
    private final HashSet<Body> toDelete = new HashSet<>();
    public BodyManager(World world){
        this.world = world;
    }

    public void setToDelete(Body body){
        if(body == null) return;
        if(body.getWorld() != world)
            throw new IllegalArgumentException("Body must be in the same world");
        toDelete.add(body);
    }

    public void flush(){
        for(Body body : toDelete)
            world.destroyBody(body);
        toDelete.clear();
    }

}
