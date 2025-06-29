package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.entityfactories.EntityFactory;
import com.tiktime.model.entities.weapons.BulletModel;
import com.tiktime.model.entities.weapons.GunshotAttackable;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;

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
        BulletModel bulletModel = EntityFactory.createBulletModel(world, bodyManager, this, x, y, rotationDeg, direction);
        EventManager.fireEvent(new GameEvent(GameEventType.BULLET_CREATED, bulletModel));
//        BodyFactory.createBulletBody(world, x, y, direction).setUserData(this);
        return true;
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        this.rotationDeg = rotationDeg;
    }

    @Override
    public float getRotationDeg() {
        return rotationDeg;
    }
}
