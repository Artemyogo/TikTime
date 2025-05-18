package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.gamefactories.BodyFactory;
import com.tiktime.model.gamefactories.EntityDataFactory;
import com.tiktime.model.configs.GameConfig;

public class RusherEnemyModel extends EnemyModel {
    public RusherEnemyModel(World world, float x, float y) {
        super(EntityDataFactory.createRusherEnemyData(), GameConfig.getInstance().getRusherEnemyConfig(),
            BodyFactory.createRusherEnemyBody(world, x, y));
        setBody();
    }

    @Override
    protected void setBody() {
        body.setUserData(this);
        body.setLinearDamping(3.0f);
    }
}
