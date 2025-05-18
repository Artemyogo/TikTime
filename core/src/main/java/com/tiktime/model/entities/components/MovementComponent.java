package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MovementComponent {
    private float speed;
    private Vector2 direction;

    public MovementComponent(float speed, Vector2 direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void move(Body body, float delta) {
        if (direction.isZero()) {
            Vector2 stoppingImpulse = body.getLinearVelocity().scl(-body.getMass());
            body.applyLinearImpulse(stoppingImpulse, body.getWorldCenter(), true);
            return;
        }

        Vector2 normalizedDirection = new Vector2(direction).nor();
        if (body.getLinearVelocity().len() > speed) {
            return;
        }

        Vector2 targetVelocity = new Vector2(normalizedDirection).scl(speed);
        Vector2 currentVelocity = body.getLinearVelocity();
        Vector2 velocityChange = targetVelocity.sub(currentVelocity);
        Vector2 impulse = velocityChange.scl(body.getMass());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }
}
