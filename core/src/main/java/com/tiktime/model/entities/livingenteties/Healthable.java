package com.tiktime.model.entities.livingenteties;

import com.tiktime.model.entities.components.HealthComponent;

public interface Healthable extends Damagable {
    int getCurrentHealth();
    int getMaxHealth();
    void setCurrentHealth(int currentHealth);
    void setMaxHealth(int maxHealth);
    HealthComponent getHealthComponent();
}
