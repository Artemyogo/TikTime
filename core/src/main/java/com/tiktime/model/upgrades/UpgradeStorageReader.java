package com.tiktime.model.upgrades;

public interface UpgradeStorageReader {
    int loadMoney();
    void loadUpgrades(Iterable<Upgrade> upgrades);
}
