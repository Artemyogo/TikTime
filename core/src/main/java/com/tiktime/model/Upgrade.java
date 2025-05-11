package com.tiktime.model;

public class Upgrade {
    private int level;
    private final UpgradeType type;

    public Upgrade(UpgradeType type) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");

        this.level = 0;
        this.type = type;
    }

    public Upgrade(UpgradeType type, int level) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        if (level < 0) throw new IllegalArgumentException("Level cannot be negative");

        this.level = level;
        this.type = type;
    }

    public static int getCost(int level) {
        return (level + 1) * 100;
    }

    public void setLevel(int level) {
        if (level < 0) throw new IllegalArgumentException("Level cannot be negative");

        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public UpgradeType getType() {
        return type;
    }

    public void doUpgrade() {
        level++;
    }
}
