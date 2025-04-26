package com.tiktime.model.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Category;
import com.tiktime.model.enums.Fraction;

public class PlayerModel extends EntityModel{

    public PlayerModel(World world, float x, float y, EntityData data) throws IllegalAccessException {
        super(world, x, y, data);
        if(data.category != Category.PLAYER)
            throw new IllegalAccessException("Player's category is not player");
    }
}
