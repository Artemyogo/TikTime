package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.FistsWeaponModel;
import com.tiktime.model.entities.weapons.WeaponModel;

public class RusherEnemyModel extends MeleeWeaponableEnemyModel {
    public RusherEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent,
                            FistsWeaponModel fistsWeaponModel, int reward, Body body, BodyManager bodyManager, float width, float height) {
        super(category, movementComponent, healthComponent, fistsWeaponModel, reward, body, bodyManager, width, height);
        assert reward == GameConfig.getRusherEnemyConfig().getReward(); // TODO: mb implace reward with this
    }


}
