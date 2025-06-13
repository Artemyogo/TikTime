package com.tiktime.model.entities.livingenteties.enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.WeaponModel;

public class BossEnemyModel extends MeleeWeaponableEnemyModel {
    public BossEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, WeaponModel weaponModel, int reward, Body body, BodyManager bodyManager, float width, float height) {
        super(category, movementComponent, healthComponent, weaponModel, reward, body, bodyManager, width, height);
    }
}
