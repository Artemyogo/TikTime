package com.tiktime.model.gameobjects;

import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Category;

public class EntityData {
    private HealthComponent healthComponent;
//    private float maxSpeed;
    public Category category;
    private float height;
    private float width;
    private float speed;

    public EntityData(float width, float height, float speed, int currentHealth, int maxHealth, Category category){
        this.width = width;
        this.height = height;
        this.healthComponent = new HealthComponent(maxHealth, currentHealth);
        this.category = category;
        this.speed = speed;
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
    public float getSpeed(){
        return speed;
    }
}
