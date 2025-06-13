package com.tiktime.model.entities.livingenteties.enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.FistsWeaponModel;

public class RusherEnemyModel extends MeleeWeaponableEnemyModel {
    public RusherEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent,
                            FistsWeaponModel fistsWeaponModel, int reward, Body body, BodyManager bodyManager, float width, float height) {
        super(category, movementComponent, healthComponent, fistsWeaponModel, reward, body, bodyManager, width, height);
        assert reward == GameConfig.getEnemyConfig(Category.ENEMY_RUSHER).getReward(); // TODO: mb implace reward with this
    }


}
