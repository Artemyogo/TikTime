package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.GameConfig;

public class RusherEnemyModel extends EnemyModel {
    public RusherEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, Body body,
        float width, float height) {
        super(category, movementComponent, healthComponent, body, width, height);
    }
}
