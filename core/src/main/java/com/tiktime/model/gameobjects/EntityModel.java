package com.tiktime.model.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.consts.FixtureFactory;
import com.tiktime.model.enums.Category;

import com.tiktime.model.consts.GameConfig.EntityConfig;

import static com.badlogic.gdx.math.MathUtils.ceil;

public abstract class EntityModel {
    protected final Body body;
    protected final EntityData data;
    protected final EntityConfig config;
    protected Vector2 direction = Vector2.Zero;

    public EntityModel(EntityData data, EntityConfig config, Body body) {
        this.data = data;
        this.config = config;
        this.body = body;
    }

    public void takeDamage(int damage){
        if(data.getCurrentHealth() <= 0) return;
        data.setCurrentHealth(data.getCurrentHealth() - damage);
//        if(data.getCurrentHealth() <= 0) {
            //body.setTransform(body.getPosition().x, body.getPosition().y, 0);
            //body.setLinearVelocity(Vector2.Zero);
            //body.setAngularVelocity(0);
//        }
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

public void move(Vector2 direction, float delta) {
    this.direction = direction.cpy();
    if (direction.isZero()) {
        Vector2 stoppingImpulse = getBody().getLinearVelocity().scl(-getBody().getMass());
        getBody().applyLinearImpulse(stoppingImpulse, getBody().getWorldCenter(), true);
        return;
    }

    Vector2 normalizedDirection = new Vector2(direction).nor();
    if (getBody().getLinearVelocity().len() > data.getSpeed()) {
        return;
    }

    Vector2 targetVelocity = new Vector2(normalizedDirection).scl(data.getSpeed());
    Vector2 currentVelocity = getBody().getLinearVelocity();
    Vector2 velocityChange = targetVelocity.sub(currentVelocity);
    Vector2 impulse = velocityChange.scl(getBody().getMass());
    getBody().applyLinearImpulse(impulse, getBody().getWorldCenter(), true);
}
    public Vector2 getDirection() {
        return direction;
    }

    protected abstract void setBody();

    public Body getBody() {
        return body;
    }

    public void applyExplosion(float x, float y, float radius, float force){
        float dist = getPosition().dst(x, y);
//        if(dist >= radius || dist == 0) return; // Avoid division by zero if at exact center

        float effect = (radius - dist) / radius;
        float impulseMagnitude = force * effect;

        Vector2 directionAwayFromExplosion = getPosition().cpy().sub(x, y).nor();
        Vector2 impulseVec = directionAwayFromExplosion.scl(impulseMagnitude);

        getBody().applyLinearImpulse(impulseVec, getBody().getWorldCenter(), true);
        takeDamage(ceil(force/10));
    }
}
