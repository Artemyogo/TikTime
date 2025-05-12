package com.tiktime.model;

import com.tiktime.model.enums.UpgradeType;

import java.util.ArrayList;

public class UpgradeManager {
    private ArrayList<Upgrade> upgrades;

    public UpgradeManager() {
        upgrades = new ArrayList<>();
    }

    public int size() {
        return upgrades.size();
    }

    public void addUpgrade(Upgrade upgrade) {
        for (Upgrade upg : upgrades) {
            if (upg.getType() == upgrade.getType()) {
                throw new RuntimeException("Trying to add existing upgrade");
            }
        }
        upgrades.add(upgrade);
    }

    public Upgrade getUpgrade(UpgradeType type) {
        for (Upgrade upg : upgrades) {
            if (upg.getType() == type) {
                return upg;
            }
        }
        return null;
    }

    public ArrayList<Upgrade> getUpgrades() {
        return new ArrayList<>(upgrades);
    }
}
