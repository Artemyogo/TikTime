package com.tiktime.model.entities.components;

import com.badlogic.gdx.physics.box2d.World;

public class MeleeAttackComponent extends AttackComponent {
    public MeleeAttackComponent(int damage, float attackCooldown, float attackRange, World world) {
        super(damage, attackCooldown, attackRange, world);
    }

    @Override
    public void doAttack() {
        if (!readyToAttack)
            throw new RuntimeException("is not readyToAttack");

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldownTimer == 0);
    }
}
