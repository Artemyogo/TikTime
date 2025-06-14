package com.tiktime.model.entities.raycasts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.EntityModel;

public class InPathRaycast implements RayCastCallback {
    private boolean inPath = false;
    private float closestFraction = 1f;
    private final Object userData;

    public InPathRaycast(Object userData) {
        this.userData = userData;
    }

    public boolean isInPath() {
        return inPath;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(fixture.isSensor())
            return -1;

        Filter filter = fixture.getFilterData();
        if(Category.OBSTACLE.intercept(filter.categoryBits)) {
            if (inPath && closestFraction > fraction) {
                inPath = false;
                closestFraction = fraction;
                return 0;
            }

            return fraction;
        }

        Object data = fixture.getBody().getUserData();
//        // TODO: smtimes there is a bug this RE throws
//        if (data == null || userData == null) {
//            throw new RuntimeException("data is null or userData is null");
//        }

        if (data instanceof EntityModel) {
            EntityModel entity = (EntityModel) data;
            if (entity.getBody().getUserData() == userData) {
                if (fraction < closestFraction) {
                    inPath = true;
                    closestFraction = fraction;
                }
                return fraction;
            }
        }

        return -1;
    }
}
