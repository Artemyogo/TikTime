package com.tiktime.model;

public enum UpgradeType {
    HP("health"),
    SPEED("speed"),
    DAMAGE("damage"),
    REGEN("regeneration");

    private final String displayName;

    UpgradeType(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }
}
