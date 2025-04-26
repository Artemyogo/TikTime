package com.tiktime.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class UpgradeModel {
    private final Preferences prefs;
    private int money;
    private int hpLevel, speedLevel, damageLevel, regenLevel;

    private UpgradeModel() {
        prefs = Gdx.app.getPreferences("stats");
        clear(); // delete this line later
        load();
    }

    private static UpgradeModel upgradeModel;

    public static UpgradeModel getInstance() {
        if (upgradeModel == null) {
            upgradeModel = new UpgradeModel();
        }

        return upgradeModel;
    }

    private void load() {
        money = prefs.getInteger("money", 0);
        hpLevel = prefs.getInteger("HpLevel", 0);
        speedLevel = prefs.getInteger("SpeedLevel", 0);
        damageLevel = prefs.getInteger("DamageLevel", 0);
        regenLevel = prefs.getInteger("RegenLevel", 0);
    }

    public static int getCost(int level) {
        return (level + 1) * 100;
    }

    public void setMoney(int money) {
        this.money = Math.max(money, 0);
    }

    public void addMoney(int x) {
        this.money = Math.max(this.money + x, 0);
    }

    public int getMoney() {
        return money;
    }

    // HP Logic
    public void setHpLevel(int hpLevel) {
        this.hpLevel = hpLevel;
    }

    public int getHpLevel() {
        return hpLevel;
    }

    public boolean tryAddHpLevel() {
        return tryUpgrade(hpLevel, this::setHpLevel);
    }

    // Speed Logic
    public void setSpeedLevel(int speedLevel) {
        this.speedLevel = speedLevel;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public boolean tryAddSpeedLevel() {
        return tryUpgrade(speedLevel, this::setSpeedLevel);
    }

    // Damage Logic
    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public boolean tryAddDamageLevel() {
        return tryUpgrade(damageLevel, this::setDamageLevel);
    }

    // Regen Logic
    public void setRegenLevel(int regenLevel) {
        this.regenLevel = regenLevel;
    }

    public int getRegenLevel() {
        return regenLevel;
    }

    public boolean tryAddRegenLevel() {
        return tryUpgrade(regenLevel, this::setRegenLevel);
    }

    private boolean tryUpgrade(int currentLevel, java.util.function.IntConsumer levelSetter) {
        int cost = getCost(currentLevel);
        if (money >= cost) {
            levelSetter.accept(currentLevel + 1);
            money -= cost;
            return true;
        }
        return false;
    }

    public void save() {
        prefs.putInteger("money", money);
        prefs.putInteger("HpLevel", hpLevel);
        prefs.putInteger("SpeedLevel", speedLevel);
        prefs.putInteger("DamageLevel", damageLevel);
        prefs.putInteger("RegenLevel", regenLevel);
        prefs.flush();
    }

    private void clear() {
        prefs.clear();
    }
}
