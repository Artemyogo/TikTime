package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.configs.configdata.PlayerData;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.model.upgrades.UpgradeType;
import com.tiktime.model.entities.weapons.WeaponType;

public class PlayerModel extends LivingEntityModel implements Weaponable, Categoriable {
    public static class CurrentStats {
        private static final PlayerData playerConfig = GameConfig.getPlayerConfig();
        private static final UpgradeModel upgradeModel = UpgradeModel.getInstance();
        public static int getHealth() {
            return upgradeModel.getUpgrade(UpgradeType.HP).getLevel() * 10 + playerConfig.getBaseHp();
        }

        public static float getSpeed() {
            return upgradeModel.getUpgrade(UpgradeType.SPEED).getLevel() * 3 + playerConfig.getBaseSpeed();
        }

        public static float getDamage() {
            return upgradeModel.getUpgrade(UpgradeType.DAMAGE).getLevel() * 10 + playerConfig.getBaseDamage();
        }

        public static int getCoins() {
            return upgradeModel.getMoney();
        }
    }

    WeaponModel weaponModel;
    Category category;

    @Override
    public void applyDamage(int damage){
        super.applyDamage(damage);
        EventManager.fireEvent(new GameEvent(GameEventType.PLAYER_ATTACKED, this));
    }

    @Override
    public void death() {

    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponModel.getWeaponType();
    }

    @Override
    public WeaponModel getWeaponModel() {
        return weaponModel;
    }

    @Override
    public void setWeaponModel(WeaponModel weaponModel) {
        this.weaponModel = weaponModel;
    }

    public PlayerModel(Category category, WeaponModel weaponModel, MovementComponent movementComponent,
                       HealthComponent healthComponent, Body body, float width, float height, int id) {
        super(movementComponent, healthComponent, body, width, height, id);
        this.weaponModel = weaponModel;
        if (!Category.PLAYER.is(category.getBits()))
            throw new IllegalArgumentException("Category is not Player");

        this.category = category;
    }
}
