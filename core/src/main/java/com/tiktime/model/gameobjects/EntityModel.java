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
    // Store the raw direction vector; normalization happens below if it's not zero.
    this.direction = direction.cpy();

    if (direction.isZero()) {
        // If the desired direction is zero, we want to stop the body.
        // Calculate impulse to counteract current velocity.
        Vector2 stoppingImpulse = getBody().getLinearVelocity().scl(-getBody().getMass());
        getBody().applyLinearImpulse(stoppingImpulse, getBody().getWorldCenter(), true);
        return;
    }

    // Normalize the direction for calculating target velocity
    Vector2 normalizedDirection = new Vector2(direction).nor();

    // Original speed cap: if current speed is already greater than max speed, do nothing.
    // This was the behavior of the `return` in the original code.
    if (getBody().getLinearVelocity().len() > data.getSpeed()) {
        // Note: This means if the entity is moving faster than data.getSpeed() (e.g., due to an explosion)
        // and then receives a command to move (even to turn or slow down to data.getSpeed()),
        // this implementation, like the original, will not execute the command.
        // To change direction while over speed, this cap would need adjustment.
        return;
    }

    // Calculate the target velocity
    Vector2 targetVelocity = new Vector2(normalizedDirection).scl(data.getSpeed());

    // Calculate the required change in velocity
    Vector2 currentVelocity = getBody().getLinearVelocity();
    Vector2 velocityChange = targetVelocity.sub(currentVelocity);

    // Calculate the impulse: I = m * dV
    Vector2 impulse = velocityChange.scl(getBody().getMass());

    // Apply the impulse at the center of the body
    // The 'delta' parameter from the method signature is not used here,
    // similar to how it wasn't used in the velocity calculation of the original setLinearVelocity approach.
    // If the impulse application was intended to be scaled by delta (e.g. treating it as a force over time),
    // the calculation `impulse.scl(delta)` might be considered, but that changes the physics interpretation.
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
