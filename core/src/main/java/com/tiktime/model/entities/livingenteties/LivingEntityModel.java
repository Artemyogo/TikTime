package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.EntityModel;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;

import static com.badlogic.gdx.math.MathUtils.ceil;

public abstract class LivingEntityModel extends EntityModel implements Movable, Healthable {
    protected MovementComponent movementComponent;
    protected HealthComponent healthComponent;
    protected boolean isExplosionApplied = false;

    public LivingEntityModel(MovementComponent movementComponent, HealthComponent healthComponent,
                             Body body, BodyManager bodyManager, int id) {
        super(body, bodyManager, id);
        this.movementComponent = movementComponent;
        this.healthComponent = healthComponent;
    }

    @Override
    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    @Override
    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    @Override
    public int getCurrentHealth() {
        return healthComponent.getCurrentHealth();
    }

    @Override
    public int getMaxHealth() {
        return healthComponent.getMaxHealth();
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        healthComponent.setCurrentHealth(currentHealth);
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        healthComponent.setMaxHealth(maxHealth);
    }

    @Override
    public float getSpeed() {
        return movementComponent.getSpeed();
    }

    @Override
    public void setSpeed(float speed) {
        movementComponent.setSpeed(speed);
    }

    @Override
    public Vector2 getDirection() {
        return movementComponent.getDirection();
    }

    @Override
    public void setDirection(Vector2 dir) {
        movementComponent.setDirection(dir);
    }

    @Override
    public void setDirectionAndMove(Vector2 direction, float delta) {
        if(isExplosionApplied){
            if(body.getLinearVelocity().isZero(1f))
                isExplosionApplied = false;
            else
                return;
        }
        movementComponent.setDirection(direction);
        movementComponent.move(body, delta);
    }

    @Override
    public void applyDamage(int damage){
        if (healthComponent.isDead())
            return;

        healthComponent.applyDamage(damage);
        if (healthComponent.isDead())
            death();
    }

    public abstract void death();

    @Override
    public void applyForce(float x, float y, float radius, float force){
        isExplosionApplied = true;
        float dist = getPosition().dst(x, y);

        float effect = (radius - dist) / radius;
        float impulseMagnitude = force * effect;

        Vector2 directionAwayFromExplosion = getPosition().cpy().sub(x, y).nor();
        Vector2 impulseVec = directionAwayFromExplosion.scl(impulseMagnitude);

        getBody().applyLinearImpulse(impulseVec, getBody().getWorldCenter(), true);
        applyDamage(ceil(force/10));
    }
}
