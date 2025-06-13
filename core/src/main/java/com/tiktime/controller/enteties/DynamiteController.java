package com.tiktime.controller.enteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.world.WorldModel;

public class DynamiteController implements IExplosive {
    WorldModel worldModel;

    public DynamiteController(WorldModel worldModel) {
        this.worldModel = worldModel;
    }

    @Override
    public void explosion(Body body, float radius, float force) {
        worldModel.explosion(body.getPosition().x, body.getPosition().y, radius, force);
    }
}
