package com.tiktime.model.entities.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.Direction;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.entityfactories.BodyFactory;
import com.tiktime.model.entities.weapons.GunshotAttackable;
import com.tiktime.model.entities.weapons.MeleeAttackable;

public class GunshotAttackComponent extends AttackComponent implements GunshotAttackable {
    private float rotationDeg;
    public GunshotAttackComponent(int damage, float attackCooldown, float attackRange, float rotationDeg,
                                  World world, BodyManager bodyManager) {
        super(damage, attackCooldown, attackRange, world, bodyManager);
        this.rotationDeg = rotationDeg;
    }

    @Override
    public boolean tryAttack(float x, float y) {
        if (!super.tryAttack())
            return false;

        Vector2 direction = new Vector2(MathUtils.cosDeg(rotationDeg), MathUtils.sinDeg(rotationDeg));
        BodyFactory.createBulletBody(world, x, y, direction).setUserData(this);
        return true;
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        this.rotationDeg = rotationDeg;
    }
}
