package com.tiktime.model.entities.livingenteties;

public interface Healthable {
    int getCurrentHealth();
    int getMaxHealth();
    void setCurrentHealth(int currentHealth);
    void setMaxHealth(int maxHealth);
    void applyDamage(int damage);
}
