package com.tiktime.model.entities.components;

import com.badlogic.gdx.physics.box2d.World;

public class GunshotAttackComponent extends AttackComponent {
    public GunshotAttackComponent(int damage, float attackCooldown, float attackRange, World world) {
        super(damage, attackCooldown, attackRange, world);
    }

    @Override
    public void doAttack() {

    }
}
