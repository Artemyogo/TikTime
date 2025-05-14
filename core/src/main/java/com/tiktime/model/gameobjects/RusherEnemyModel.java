package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.consts.BodyFactory;
import com.tiktime.model.consts.EntityDataFactory;
import com.tiktime.model.consts.GameConfig;

public class RusherEnemyModel extends EnemyModel {
    public RusherEnemyModel(World world, float x, float y) {
        super(EntityDataFactory.createRusherEnemyData(), GameConfig.getInstance().getRusherEnemyConfig(),
            BodyFactory.createPlayerBody(world, x, y));
        setBody();
    }

    @Override
    protected void setBody() {
        body.setUserData(this);
        body.setLinearDamping(3.0f);
    }
}
