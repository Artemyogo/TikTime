package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Fraction;

public abstract class EntityModel {
    Body body;
    private HealthComponent healthComponent;
    Fraction fraction;

    public EntityModel(World world, float x, float y, float width, float height,
                       HealthComponent healthComponent, Fraction fraction) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        this.healthComponent = healthComponent;
        this.fraction = fraction;
    }

    public int getMaxHealth() {
        return healthComponent.getMaxHealth();
    }

    public int getCurrentHealth() {
        return healthComponent.getCurrentHealth();
    }

    public void setCurrentHealth(int currentHealth) {
        healthComponent.setCurrentHealth(currentHealth);
    }
    public void setMaxHealth(int maxHealth) {
        healthComponent.setMaxHealth(maxHealth);
    }
}
