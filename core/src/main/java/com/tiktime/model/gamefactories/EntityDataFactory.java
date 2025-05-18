package com.tiktime.model.gamefactories;

import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;

public class EntityDataFactory {
    public static EntityData createPlayerData() {
        GameConfig.PlayerConfig config = GameConfig.getInstance().getPlayerConfig();
        return new EntityData(
            config.getWidth(),
            config.getHeight(),
            config.getBaseSpeed(),
            PlayerModel.CurrentStats.getHealth(),
            PlayerModel.CurrentStats.getHealth(),
            Category.PLAYER
        );
    }

    public static EntityData createRusherEnemyData(){
        GameConfig.EnemyConfig<?> config = GameConfig.getInstance().getRusherEnemyConfig();
        return new EntityData(
            config.getWidth(),
            config.getHeight(),
            config.getBaseSpeed(),
            config.getBaseHp(),
            config.getBaseHp(),
            Category.ENEMY_RUSHER
        );
    }
}
