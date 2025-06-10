package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.Direction;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.MeleeAttackable;
import com.tiktime.model.entities.weapons.WeaponModel;

public abstract class MeleeWeaponableEnemyModel extends EnemyModel implements MeleeAttackable {
    protected float width;
    protected float height;
    MeleeAttackable meleeAttackable;

    public MeleeWeaponableEnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, WeaponModel weaponModel,
                                     int reward, Body body, BodyManager bodyManager, float width, float height) {
        super(category, movementComponent, healthComponent, weaponModel, reward, body, bodyManager);
        if (!(weaponModel instanceof MeleeAttackable)) {
            throw new RuntimeException("weaponModel is not a MeleeAttackable");
        }

        meleeAttackable = (MeleeAttackable) weaponModel;

        this.width = width;
        this.height = height;
    }

    @Override
    public boolean tryAttack(float x, float y, float width, float height, Direction direction) {
        return meleeAttackable.tryAttack(x, y, width, height, direction);
    }

    public void setWidth(float width) {
        this.width = width;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean tryAttack(float playerX, float playerY) {
        if (getDist(playerX, playerY) > getAttackRange()) {
            return false;
        }

        return tryAttack(body.getPosition().x, body.getPosition().y, width, height, movementComponent.getDirectionType());
    }
}
