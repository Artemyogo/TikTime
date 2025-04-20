package com.tiktime.model.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Faction;

public abstract class Entity extends GameObject {
    private HealthComponent healthComponent;
    Faction faction;

    public Entity(String id, float x, float y, float width, float height, TextureRegion texture,
                  HealthComponent healthComponent, Faction faction) {
        super(id, x, y, width, height, texture);
        this.healthComponent = new HealthComponent(healthComponent);
        this.faction = faction;
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
