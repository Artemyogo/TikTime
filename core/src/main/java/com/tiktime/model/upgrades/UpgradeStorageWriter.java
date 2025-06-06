package com.tiktime.model.upgrades;

public interface UpgradeStorageWriter {
    void save(int money, Iterable<Upgrade> upgrades);
}
