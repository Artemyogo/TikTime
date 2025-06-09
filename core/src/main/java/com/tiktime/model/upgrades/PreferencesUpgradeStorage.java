package com.tiktime.model.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesUpgradeStorage implements UpgradeStorageReader, UpgradeStorageWriter {
    private final Preferences preferences;

    public PreferencesUpgradeStorage(String preferencesName) {
        preferences = Gdx.app.getPreferences(preferencesName);
    }

    @Override
    public int loadMoney() {
        return preferences.getInteger("money", 0);
    }

    @Override
    public void loadUpgrades(Iterable<Upgrade> upgrades) {
        for (Upgrade upgrade : upgrades) {
            int level = preferences.getInteger(upgrade.getType().getName(), 0);
            upgrade.setLevel(level);
        }
    }

    public void clearUpgrades(Iterable<Upgrade> upgrades) {
        preferences.putInteger("money", 0);
        for (Upgrade upgrade : upgrades) {
            preferences.putInteger(upgrade.getType().getName(), 0);
        }

        preferences.flush();
    }

    @Override
    public void save(int money, Iterable<Upgrade> upgrades) {
        preferences.putInteger("money", money);

        for (Upgrade upgrade : upgrades) {
            preferences.putInteger(upgrade.getType().getName(), upgrade.getLevel());
        }

        preferences.flush();
    }
}
