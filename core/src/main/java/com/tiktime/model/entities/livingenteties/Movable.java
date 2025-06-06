package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.math.Vector2;
import com.tiktime.model.entities.components.MovementComponent;

public interface Movable {
    float getSpeed();
    void setSpeed(float speed);
    Vector2 getDirection();
    void setDirection(Vector2 dir);
    void setDirectionAndMove(Vector2 dir, float delta);
    /// TODO i think there is not 'applyExplosion' but applyForce for example
    void applyForce(float x, float y, float radius, float force);
    MovementComponent getMovementComponent();
}
