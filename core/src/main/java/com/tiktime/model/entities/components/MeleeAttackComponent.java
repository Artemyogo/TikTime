package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.entityfactories.BodyFactory;

public class MeleeAttackComponent extends AttackComponent {
    public MeleeAttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        super(damage, attackCooldown, attackRange, world, bodyManager);
    }

    @Override
    public void doAttack(Vector2 position) {
        super.doAttack();
        bodyManager.setToDelete(BodyFactory.createFistAttackBody(world, position.x, position.y));
    }
}
