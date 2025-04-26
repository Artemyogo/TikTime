package com.tiktime.model.gameobjects;

import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Category;

public class EntityData {

    private HealthComponent healthComponent;
    private float maxSpeed;
    private float width;
    private float height;
    public Category category;

    EntityData(float width, float height, HealthComponent healthComponent, Category category){
        this.width = width;
        this.height = height;
        this.healthComponent = healthComponent;
        this.category = category;
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

    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }

}
