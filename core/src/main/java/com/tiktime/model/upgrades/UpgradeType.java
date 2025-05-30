package com.tiktime.model.upgrades;

import com.tiktime.model.configs.GameConfig;

public enum UpgradeType {
    HP("health", GameConfig.getPlayerConfig().getBaseHp(), 10),
    SPEED("speed", GameConfig.getPlayerConfig().getBaseSpeed(), 3),
    DAMAGE("damage", GameConfig.getPlayerConfig().getBaseDamage(), 10),
    REGEN("regeneration", GameConfig.getPlayerConfig().getBaseRegen(), 3);

    private final String displayName;
    private final float baseValue;
    private final float step;

    UpgradeType(String displayName, float baseValue, float step) {
        this.displayName = displayName;
        this.baseValue = baseValue;
        this.step = step;
    }

    public String getName() {
        return displayName;
    }

    public float getBaseValue() {
        return baseValue;
    }

    public float getStep() {
        return step;
    }
}
