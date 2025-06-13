package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.PlayerData;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.GunshotAttackable;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.model.upgrades.UpgradeType;

public class PlayerModel extends WeaponableLivingEntityModel implements Categoriable, GunshotAttackable {
    public static class CurrentStats {
        private static final PlayerData playerConfig = GameConfig.getPlayerConfig();
        private static final UpgradeModel upgradeModel = UpgradeModel.getInstance();

        public static int getHealth() {
            return upgradeModel.getUpgrade(UpgradeType.HP).getLevel() * (int)UpgradeType.HP.getStep() + playerConfig.getBaseHp();
        }

        public static int getRegenHealth() {
            return upgradeModel.getUpgrade(UpgradeType.REGEN).getLevel() * (int)UpgradeType.REGEN.getStep() + playerConfig.getBaseRegen();
        }

        public static float getSpeed() {
            return upgradeModel.getUpgrade(UpgradeType.SPEED).getLevel() * UpgradeType.SPEED.getStep() + playerConfig.getBaseSpeed();
        }

        public static float getAttackCooldown() {
            return (float) Math.pow(UpgradeType.ATTACK_COOLDOWN.getStep(), upgradeModel.getUpgrade(UpgradeType.ATTACK_COOLDOWN).getLevel()) * playerConfig.getBaseAttackCooldown();
        }

        public static int getDamage() {
            return upgradeModel.getUpgrade(UpgradeType.DAMAGE).getLevel() * (int)UpgradeType.DAMAGE.getStep() + playerConfig.getBaseDamage();
        }

        public static int getCoins() {
            return upgradeModel.getMoney();
        }
    }

    Category category;
    GunshotAttackable gunshotAttackable;

    public PlayerModel(Category category, WeaponModel weaponModel, MovementComponent movementComponent,
                       HealthComponent healthComponent, Body body, BodyManager bodyManager) {
        super(movementComponent, healthComponent, weaponModel, body, bodyManager);
        if (!Category.PLAYER.is(category.getBits()))
            throw new IllegalArgumentException("Category is not Player");

        this.category = category;
        gunshotAttackable = (GunshotAttackable) weaponModel;
    }

    @Override
    public boolean tryAttack(float x, float y) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) weaponModel;
        WeaponData weaponData = GameConfig.getWeaponConfig(WeaponType.AK47);

        float centerX = x + weaponData.getOffsetX();
        float centerY = y + weaponData.getOffsetY();

        float width = GameConfig.getBulletConfig().getWidth();
        float height = GameConfig.getBulletConfig().getHeight();

        float localOffsetX = weaponData.getOffsetAttackX();
        float localOffsetY = weaponData.getOffsetAttackY();

        if (gunshotAttackable.getRotationDeg() > 90 || getRotationDeg() < -90) {
            localOffsetY = -localOffsetY;
        }

        float rotationRad = (float) Math.toRadians(getRotationDeg());

        float rotatedOffsetX = (float) (Math.cos(rotationRad) * localOffsetX - Math.sin(rotationRad) * localOffsetY);
        float rotatedOffsetY = (float) (Math.sin(rotationRad) * localOffsetX + Math.cos(rotationRad) * localOffsetY);

        float barrelX = centerX + rotatedOffsetX;
        float barrelY = centerY + rotatedOffsetY;

        return gunshotAttackable.tryAttack(barrelX - width / 2f, barrelY);
    }

    public boolean tryAttack() {
        return tryAttack(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        gunshotAttackable.updateWeaponRotation(rotationDeg);
    }

    @Override
    public float getRotationDeg() {
        return gunshotAttackable.getRotationDeg();
    }

    @Override
    public void applyDamage(int damage){
        super.applyDamage(damage);
        EventManager.fireEvent(new GameEvent(GameEventType.PLAYER_ATTACKED, this));
        if (getCurrentHealth() == 0) {
            death();
        }
    }

    @Override
    public void death() {
        EventManager.fireEvent(new GameEvent(GameEventType.PLAYER_DEATH, this));
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
