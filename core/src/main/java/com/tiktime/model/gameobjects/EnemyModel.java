package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.enums.Category;

public class EnemyModel extends EntityModel {

    public EnemyModel(World world, float x, float y, EntityData data) throws IllegalAccessException {
        /// TODO REBUILD
        super(world, x, y, data, GameConfig.getInstance().getPlayerConfig());
        if(data.category != Category.ENEMY)
            throw new IllegalAccessException("Enemy's category is not enemny");
    }
}
