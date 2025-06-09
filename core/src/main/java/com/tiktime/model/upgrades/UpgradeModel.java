package com.tiktime.model.upgrades;

public final class UpgradeModel {
    private final PreferencesUpgradeStorage preferencesUpgradeStorage;
    private final UpgradeManager manager;
    private int money;

    private UpgradeModel() {
        manager = new UpgradeManager();
        manager.addUpgrade(new Upgrade(UpgradeType.HP));
        manager.addUpgrade(new Upgrade(UpgradeType.SPEED));
        manager.addUpgrade(new Upgrade(UpgradeType.DAMAGE));
        manager.addUpgrade(new Upgrade(UpgradeType.REGEN));

        preferencesUpgradeStorage = new PreferencesUpgradeStorage("com.tiktime.upgrades");
        money = preferencesUpgradeStorage.loadMoney();
        preferencesUpgradeStorage.loadUpgrades(manager.getUpgrades());
//        preferencesUpgradeStorage.clearUpgrades(manager.getUpgrades());
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
        preferencesUpgradeStorage.save(money, manager.getUpgrades());
    }
}
