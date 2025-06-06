package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.FistsWeaponModel;

public class RusherEnemyModel extends EnemyModel {
    public RusherEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, FistsWeaponModel fistsWeaponModel, int reward, Body body,
        float width, float height) {
        super(category, movementComponent, healthComponent, reward, body, width, height, fistsWeaponModel);
    }
}
