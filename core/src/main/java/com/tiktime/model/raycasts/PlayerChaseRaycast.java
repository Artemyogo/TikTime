package com.tiktime.model.raycasts;

import com.badlogic.gdx.Gdx;
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

        Filter filter = fixture.getFilterData();
        Gdx.app.log("PlayerChaseRaycast", "filter: " + filter.categoryBits);
        if(filter.categoryBits == Category.WALL.getBit() || filter.categoryBits == Category.DOOR.getBit()) {
            if (canSeePlayer && closestFraction > fraction) {
                canSeePlayer = false;
                closestFraction = fraction;
                return 0;
            }

            return fraction;
        }

        Object data = fixture.getBody().getUserData();
        if (data instanceof EntityModel) {
            EntityModel entity = (EntityModel) data;
            if (entity.getData().category == Category.PLAYER) {
                if (fraction < closestFraction) {
                    canSeePlayer = true;
                    closestFraction = fraction;
                }
                return fraction;
            }
        }

        return -1;
    }
}
