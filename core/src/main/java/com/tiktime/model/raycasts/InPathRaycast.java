package com.tiktime.model.raycasts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EntityModel;

public class InPathRaycast implements RayCastCallback {
    private boolean inPath = false;
    private float closestFraction = 1.0f;
    private Category category;
    private EntityModel entity;

    public InPathRaycast(Category category) {
        this.category = category;
    }

    public void setEntity(EntityModel entity) {
        this.entity = entity;
    }

    public EntityModel getEntity() {
        return entity;
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
        if (data instanceof EntityModel) {
            EntityModel entity = (EntityModel) data;
            if (entity.getData().category == category) {
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
