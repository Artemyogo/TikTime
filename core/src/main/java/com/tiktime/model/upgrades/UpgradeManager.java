package com.tiktime.model.upgrades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class UpgradeManager {
    private final Set<Upgrade> upgrades;

    public UpgradeManager() {
        upgrades = new TreeSet<>(Comparator.comparing(Upgrade::getType));
    }

    public int size() {
        return upgrades.size();
    }

    public UpgradeManager addUpgrade(Upgrade upgrade) {
        if (upgrade == null) throw new IllegalArgumentException("Upgrade cannot be null");

        upgrades.add(upgrade);

        return this;
    }

    public Upgrade getUpgrade(UpgradeType type) {
        return upgrades.stream().filter(upg -> upg.getType() == type).findAny().orElse(null);
    }

    public ArrayList<Upgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }
}
