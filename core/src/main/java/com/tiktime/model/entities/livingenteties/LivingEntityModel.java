package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.entities.EntityModel;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;

import static com.badlogic.gdx.math.MathUtils.ceil;

public abstract class LivingEntityModel extends EntityModel implements Movable, Healthable {
    private MovementComponent movementComponent;
    private HealthComponent healthComponent;
    private boolean isExposlionApplied = false;

    public LivingEntityModel(MovementComponent movementComponent, HealthComponent healthComponent,
                             Body body, float width, float height, int id) {
        super(body, width, height, id);
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
        if(isExposlionApplied){
            if(body.getLinearVelocity().isZero(1f))
                isExposlionApplied = false;
            else
                return;
        }
        movementComponent.setDirection(direction);
        movementComponent.move(body, delta);
    }

    @Override
    public void applyDamage(int damage){
//        if(healthComponent.getCurrentHealth() <= 0) return;
        Gdx.app.log(this.getClass().getSimpleName(), "apply damage " + damage);
        healthComponent.setCurrentHealth(healthComponent.getCurrentHealth() - damage);
    }

    @Override
    public void applyExplosion(float x, float y, float radius, float force){
        isExposlionApplied = true;
        float dist = getPosition().dst(x, y);

        float effect = (radius - dist) / radius;
        float impulseMagnitude = force * effect;

        Vector2 directionAwayFromExplosion = getPosition().cpy().sub(x, y).nor();
        Vector2 impulseVec = directionAwayFromExplosion.scl(impulseMagnitude);

        getBody().applyLinearImpulse(impulseVec, getBody().getWorldCenter(), true);
        applyDamage(ceil(force/10));
    }
}
