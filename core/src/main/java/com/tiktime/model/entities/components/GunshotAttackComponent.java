package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.weapons.GunshotAttackable;
import com.tiktime.model.entities.weapons.MeleeAttackable;

public class GunshotAttackComponent extends AttackComponent implements GunshotAttackable {
    public GunshotAttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        super(damage, attackCooldown, attackRange, world, bodyManager);
    }

    public boolean tryAttack() {
        if (!super.tryAttack())
            return false;

//        BodyFactory.createBulletBody(world, position.x, position.y);
        return true;
    }
}
