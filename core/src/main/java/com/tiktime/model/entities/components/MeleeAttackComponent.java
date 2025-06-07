package com.tiktime.model.entities.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.entityfactories.BodyFactory;

public class MeleeAttackComponent extends AttackComponent {
    public MeleeAttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        super(damage, attackCooldown, attackRange, world, bodyManager);
    }

    @Override
    public void doAttack(float x, float y) {
        super.doAttack(x, y);
        if (!readyToAttack)
            throw new RuntimeException("is not readyToAttack");

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldownTimer == 0);
        Body body = BodyFactory.createFistAttackBody(world, x, y);

    }
}
