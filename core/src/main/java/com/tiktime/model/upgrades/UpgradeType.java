package com.tiktime.model.upgrades;

import com.tiktime.model.configs.GameConfig;

public enum UpgradeType {
    HP("health", GameConfig.getInstance().getPlayerConfig().getBaseHp(), 10),
    SPEED("speed", GameConfig.getInstance().getPlayerConfig().getBaseSpeed(), 3),
    DAMAGE("damage", GameConfig.getInstance().getPlayerConfig().getBaseDamage(), 10),
    REGEN("regeneration", GameConfig.getInstance().getPlayerConfig().getBaseRegen(), 3);

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
