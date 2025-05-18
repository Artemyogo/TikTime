package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.WorldModel;
import com.tiktime.model.entities.EntityModel;

public class BulletModel extends EntityModel {
    private Body body;
    private float damage;

    public BulletModel(Body body, float width, float height, int id) {
        super(body, width, height, id);
    }

//    public BulletModel(WorldModel worldModel, Vector2 position, Vector2 direction){
//        super();
//    }
//
//    @Override
//    protected void setBody() {
//
//    }
}
