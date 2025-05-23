package com.tiktime.model.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.enums.Category;

public abstract class EnemyModel extends EntityModel {
    public static int idNext = 0;
    private final int id;
    public EnemyModel(EntityData data, GameConfig.EntityConfig config, Body body)  {
        super(data, config, body);
        id = idNext++;
    }

    public int getId() {
        return id;
    }
}
