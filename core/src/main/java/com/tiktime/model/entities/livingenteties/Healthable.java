package com.tiktime.model.entities.livingenteties;

import com.tiktime.model.entities.components.HealthComponent;

public interface Healthable {
    int getCurrentHealth();
    int getMaxHealth();
    void setCurrentHealth(int currentHealth);
    void setMaxHealth(int maxHealth);
    void applyDamage(int damage);
    HealthComponent getHealthComponent();
}
