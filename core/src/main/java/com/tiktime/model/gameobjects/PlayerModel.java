package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.model.configs.*;
import com.tiktime.model.upgrades.UpgradeType;
import com.tiktime.model.configs.GameConfig.PlayerConfig;
import com.tiktime.model.gamefactories.BodyFactory;
import com.tiktime.model.gamefactories.EntityDataFactory;

public class PlayerModel extends EntityModel{
    public static class CurrentStats {
        private static final PlayerConfig playerConfig = GameConfig.getInstance().getPlayerConfig();
        private static final UpgradeModel upgradeModel = UpgradeModel.getInstance();
        ///  TODO DODELAT BLIN BLINSKIY
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

    public PlayerModel(World world, float x, float y) {
        super(EntityDataFactory.createPlayerData(),
            GameConfig.getInstance().getPlayerConfig(),
            BodyFactory.createPlayerBody(world, x, y));
        setBody();
    }

    public PlayerModel(World world, float x, float y, EntityData entityData) {
        super(entityData,
            GameConfig.getInstance().getPlayerConfig(),
            BodyFactory.createPlayerBody(world, x, y));
        setBody();
    }

    @Override
    protected void setBody() {
        body.setUserData(this);
        body.setLinearDamping(3.0f);
    }

    @Override
    public void takeDamage(int damage){
        super.takeDamage(damage);
//        getData().setCurrentHealth(getData().getCurrentHealth() - damage);
    }
}
