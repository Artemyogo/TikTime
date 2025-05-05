package com.tiktime.model.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.UpgradeModel;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.GameConstants;
import com.tiktime.model.enums.Category;
import com.tiktime.model.enums.Fraction;
import com.tiktime.model.consts.GameConfig.PlayerConfig;

public class PlayerModel extends EntityModel{
    public static EntityData PLAYER_DATA = new EntityData(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT,
        );
    public static class BaseStats {
        private static UpgradeModel upgradeModel = UpgradeModel.getInstance();
        private static PlayerConfig playerConfig = GameConfig.getInstance().getPlayerConfig();
        ///  TODO DODELAT BLIN BLINSKIY
        public static int getHealth() {
            return upgradeModel.getHpLevel() * 10 + playerConfig.getBaseHp();
        }

        public static int getSpeed() {
            return upgradeModel.getSpeedLevel() * 10 + playerConfig.getBaseSpeed();
        }

        public static int getDamage() {
            return upgradeModel.getDamageLevel() * 10/* + playerConfig.getBaseDamage()*/;
        }
    }

    public PlayerModel(World world, float x, float y) throws IllegalAccessException {
        super(world, x, y, data);
        if(data.category != Category.PLAYER)
            throw new IllegalAccessException("Player's category is not player");
    }
}
