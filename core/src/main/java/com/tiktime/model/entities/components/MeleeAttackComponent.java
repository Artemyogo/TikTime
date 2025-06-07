package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.Direction;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.weapons.MeleeAttackable;

public class MeleeAttackComponent extends AttackComponent implements MeleeAttackable {
    public MeleeAttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        super(damage, attackCooldown, attackRange, world, bodyManager);
    }

    public boolean tryAttack(float x, float y, float width, float height, Direction direction) {
        if (!super.tryAttack())
            return false;

        y -= height / 2f;
        if (direction == Direction.WEST) {
            x -= width;
        }
//        BodyFactory.createFistAttackBody(world, x, y, width, height, this);
        bodyManager.setToDelete(BodyFactory.createFistAttackBody(world, x, y, width, height, this));
        return true;
    }
}
