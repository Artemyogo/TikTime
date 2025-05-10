package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.UpgradeModel;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.enums.Category;
import com.tiktime.model.consts.GameConfig.PlayerConfig;

public class PlayerModel extends EntityModel{
    public static class CurrentStats {
        private static final PlayerConfig playerConfig = GameConfig.getInstance().getPlayerConfig();
        private static final UpgradeModel upgradeModel = UpgradeModel.getInstance();
        ///  TODO DODELAT BLIN BLINSKIY
        public static int getHealth() {
            return upgradeModel.getHpLevel() * 10 + playerConfig.getBaseHp();
        }

        public static float getSpeed() {
            return upgradeModel.getSpeedLevel() * 3 + playerConfig.getBaseSpeed();
        }

        public static float getDamage() {
            return (upgradeModel.getDamageLevel() * 10) + playerConfig.getBaseDamage();
        }

        public static int getCoins() {
            return upgradeModel.getMoney();
        }
    }

    public PlayerModel(World world, float x, float y) {
        /// IDK seems toooo wierd, maybe we need to realize abstract method in EntityModel like getConfig()
        super(world, x, y,
            new EntityData(GameConfig.getInstance().getPlayerConfig().getWidth(),
                GameConfig.getInstance().getPlayerConfig().getHeight(),
                CurrentStats.getSpeed(),
                CurrentStats.getHealth(),
                CurrentStats.getHealth(),
                Category.PLAYER),
            GameConfig.getInstance().getPlayerConfig());
    }
}
