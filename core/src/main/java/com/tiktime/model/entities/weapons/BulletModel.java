package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.BodyManager;
import com.tiktime.model.WorldModel;
import com.tiktime.model.entities.EntityModel;

public class BulletModel extends EntityModel {
    private Body body;
    private float damage;

    public BulletModel(Body body, BodyManager bodyManager) {
        super(body, bodyManager, idNext++);
    }

}
