package com.tiktime.model.raycasts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.enums.Category;

public class ClosestObjectRayCast implements RayCastCallback {
    private boolean canSeeTarget = false;
    private float closestFraction = 1.0f;
    Category source, target;

    public ClosestObjectRayCast(Category source, Category target) {
        this.source = source;
        this.target = target;
    }

    public boolean isCanSeeTarget() {
        return canSeeTarget;
    }
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(fixture.isSensor())
            return -1;

        if(source.is(fixture.getFilterData().categoryBits))
            return -1;
        if(fraction < closestFraction) {
            canSeeTarget = target.is(fixture.getFilterData().categoryBits);
            closestFraction = fraction;
        }
        return fraction;
    }
}
