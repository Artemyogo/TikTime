package com.tiktime.model.components;

public class HealthComponent {
    private int currentHealth;
    private int maxHealth;

    public HealthComponent(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("maxHealth out of range");
        }

        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public HealthComponent(int maxHealth, int currentHealth) {
        if (currentHealth <= 0 || maxHealth < currentHealth) {
            throw new IllegalArgumentException("currentHealth or maxHealth out of range");
        }

        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public HealthComponent(HealthComponent healthComponent) {
        this(healthComponent.maxHealth, healthComponent.currentHealth);
    }

    public void takeDamage(int amount) {
        setCurrentHealth(getCurrentHealth() - amount);
    }

    public void heal(int amount) {
        setCurrentHealth(getCurrentHealth() + amount);
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(maxHealth, currentHealth));
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        /// TODO I want to believe that all is OK
        setCurrentHealth(Math.min(currentHealth, maxHealth));
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
