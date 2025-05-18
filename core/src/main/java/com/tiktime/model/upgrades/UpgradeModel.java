package com.tiktime.model.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class UpgradeModel {
    private final Preferences prefs;
    private final UpgradeManager manager;
    private int money;

    private UpgradeModel() {
        prefs = Gdx.app.getPreferences("com.tiktime.upgrades");
        manager = new UpgradeManager();
        manager.addUpgrade(new Upgrade(UpgradeType.HP));
        manager.addUpgrade(new Upgrade(UpgradeType.SPEED));
        manager.addUpgrade(new Upgrade(UpgradeType.DAMAGE));
        manager.addUpgrade(new Upgrade(UpgradeType.REGEN));
        load();
    }

    private static UpgradeModel upgradeModel;

    public static UpgradeModel getInstance() {
        if (upgradeModel == null) {
            upgradeModel = new UpgradeModel();
        }

        return upgradeModel;
    }

    public UpgradeManager getManager() {
        return manager;
    }

    public Upgrade getUpgrade(UpgradeType type) {
        return manager.getUpgrade(type);
    }

    private void load() {
        prefs.clear();
        prefs.putInteger("money", 1000);
        prefs.flush();
        money = prefs.getInteger("money", 0);
        for (Upgrade upgrade : manager.getUpgrades()) {
            upgrade.setLevel(prefs.getInteger(upgrade.getType().getName(), 0));
        }
    }

    public void setMoney(int money) {
        if (money < 0) throw new IllegalArgumentException("Money cannot be negative");

        this.money = money;
    }

    public void addMoney(int x) {
        if (money + x < 0) throw new IllegalArgumentException("Money cannot be negative");

        money += x;
    }

    public int getMoney() {
        return money;
    }

    public void save() {
        prefs.putInteger("money", money);
        for (Upgrade upgrade : manager.getUpgrades()) {
            prefs.putInteger(upgrade.getType().getName(), upgrade.getLevel());
        }
        prefs.flush();
    }
}
