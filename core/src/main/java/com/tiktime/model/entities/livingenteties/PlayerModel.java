package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.common.Direction;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.PlayerData;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.GunshotAttackable;
import com.tiktime.model.entities.weapons.MeleeAttackable;
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
            return upgradeModel.getUpgrade(UpgradeType.HP).getLevel() * 10 + playerConfig.getBaseHp();
        }

        public static int getRegenHealth() {
            return upgradeModel.getUpgrade(UpgradeType.REGEN).getLevel() * 2 + playerConfig.getBaseRegen();
        }

        public static float getSpeed() {
            return upgradeModel.getUpgrade(UpgradeType.SPEED).getLevel() * 0.3f + playerConfig.getBaseSpeed();
        }

        public static float getDamage() {
            return upgradeModel.getUpgrade(UpgradeType.DAMAGE).getLevel() * 10 + playerConfig.getBaseDamage();
        }

        public static int getCoins() {
            return upgradeModel.getMoney();
        }
    }

    Category category;

    @Override
    public boolean tryAttack(float x, float y) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) weaponModel;
        // TODO: magic constants
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
        GunshotAttackable gunshotAttackable = (GunshotAttackable) weaponModel;
        gunshotAttackable.updateWeaponRotation(rotationDeg);
    }

    @Override
    public float getRotationDeg() {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) weaponModel;
        return gunshotAttackable.getRotationDeg();
    }

    @Override
    public void applyDamage(int damage){
        super.applyDamage(damage);
        EventManager.fireEvent(new GameEvent( GameEventType.PLAYER_ATTACKED, this));
    }



//    @Override
//    public boolean tryAttack(float x, float y, float width, float height, Direction direction) {
//        MeleeAttackable meleeAttackable = (MeleeAttackable) weaponModel;
//        return meleeAttackable.tryAttack(x, y, width, height, direction);
//    }

    @Override
    public void death() {

    }

    @Override
    public Category getCategory() {
        return category;
    }

    public PlayerModel(Category category, WeaponModel weaponModel, MovementComponent movementComponent,
                       HealthComponent healthComponent, Body body, BodyManager bodyManager) {
        super(movementComponent, healthComponent, weaponModel, body, bodyManager);
        if (!Category.PLAYER.is(category.getBits()))
            throw new IllegalArgumentException("Category is not Player");

        this.category = category;
    }
}
