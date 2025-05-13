package com.tiktime.model.raycasts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EntityModel;

public class PlayerChaseRaycast implements RayCastCallback {
    private boolean canSeePlayer = false;
    private float closestFraction = 1.0f;

    public boolean canSeePlayer() {
        return canSeePlayer;
    }
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(fixture.isSensor())
            return -1;

        Object data = fixture.getBody().getUserData();
        if(!(data instanceof EntityModel))
            return fraction;
        EntityModel entity = (EntityModel) data;
        if(entity.getData().category == Category.ENEMY)
            return -1;
        if(fraction < closestFraction) {
            canSeePlayer = entity.getData().category != Category.PLAYER;
            closestFraction = fraction;
        }
        return fraction;
    }
}
