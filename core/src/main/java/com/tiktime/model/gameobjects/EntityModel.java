package com.tiktime.model.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.consts.FixtureFactory;
import com.tiktime.model.enums.Category;

import com.tiktime.model.consts.GameConfig.EntityConfig;

public abstract class EntityModel {
    private final Body body;
    private final EntityData data;
    private final EntityConfig config;


    public EntityModel(World world, float x, float y,
                       EntityData data, EntityConfig config) {
        this.data = data;
        this.config = config;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x - data.getWidth() / 2, y - data.getHeight() / 2);
        this.body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setLinearDamping(3.0f);

        this.body.createFixture(FixtureFactory.getEntityFixtureDef(data.category));
    }

    public void takeDamage(int damage){
        if(data.getCurrentHealth() <= 0) return;
        data.setCurrentHealth(data.getCurrentHealth() - damage);
        if(data.getCurrentHealth() <= 0) {
            body.setTransform(body.getPosition().x, body.getPosition().y, 0);
            body.setLinearVelocity(Vector2.Zero);
            body.setAngularVelocity(0);
        }
    }

    public EntityData getData(){
        return data;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

//    public void move(Vector2 direction){
//        EntityConfig entityConfig = GameConfig.getInstance().getEntityConfig();
//        ///  TODO THERE ALSO
////        Vector2 force = new Vector2(direction).scl(entityConfig.getAcceleration());
////        body.applyForceToCenter(force, true);
//    }

    public void move(Vector2 direction) {
        if(getBody().getLinearVelocity().len() > data.getSpeed()) return;


        Vector2 velocity = new Vector2(direction).nor();
//        velocity.x *= 10000;
        velocity.x *= data.getSpeed();
//        velocity.y *= 10000;
        velocity.y *= data.getSpeed();
//        if (!direction.equals(Vector2.Zero)) {
//            Gdx.app.log(this.getClass().getSimpleName(), "Moving " + velocity);
//        }
//        velocity.scl(1f / PPM);

        body.setLinearVelocity(velocity);
//        body.setTransform(32, 32, 0);
        ///  TODO MAY BE NOT GOOD
//        body.setBullet(true);
    }

    public Body getBody() {
        return body;
    }

    public void applyExplosion(float x, float y, float radius, float force){
        float dist = getPosition().dst(x, y);
        if(dist >= radius || dist == 0) return; // Avoid division by zero if at exact center

        float effect = (radius - dist) / radius;
        float impulseMagnitude = force * effect;

        Vector2 directionAwayFromExplosion = getPosition().cpy().sub(x, y).nor();
        Vector2 impulseVec = directionAwayFromExplosion.scl(impulseMagnitude);

        getBody().applyLinearImpulse(impulseVec, getBody().getWorldCenter(), true);
        takeDamage(1);

    }
}
