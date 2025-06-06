package com.tiktime.model.entities.components;

public class HealthComponent {
    private int currentHealth;
    private int maxHealth;

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

    public void applyDamage(int amount) {
        setCurrentHealth(getCurrentHealth() - amount);
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

    public void regenerateHealth(int amount) {
        setCurrentHealth(currentHealth + amount);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
