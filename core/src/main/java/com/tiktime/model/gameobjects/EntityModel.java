package com.tiktime.model.gameobjects;

import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Faction;

public abstract class EntityModel extends GameObjectModel {
    private HealthComponent healthComponent;
    Faction faction;

    public EntityModel(String id, float x, float y, float width, float height,
                       HealthComponent healthComponent, Faction faction) {
        super(id, x, y, width, height);
        this.healthComponent = healthComponent;
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
