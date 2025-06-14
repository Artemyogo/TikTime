package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.EntityModel;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;

public class BulletModel extends EntityModel implements GunshotAttackable, Categoriable {
    private final Attackable attackable;
    private final float rotationDeg;
    private boolean isDelete = false;

    public BulletModel(Body body, BodyManager bodyManager, Attackable attackable, float rotationDeg) {
        super(body, bodyManager);
        this.attackable = attackable;
        this.rotationDeg = rotationDeg;
    }

    @Override
    public Category getCategory() {
        return Category.BULLET;
    }

    @Override
    public int getAttackDamage() {
        return attackable.getAttackDamage();
    }

    @Override
    public void setAttackDamage(int damage) {
        attackable.setAttackDamage(damage);
    }

    @Override
    public void updateAttackCooldownTimer(float delta) {
        attackable.updateAttackCooldownTimer(delta);
    }

    @Override
    public float getAttackRange() {
        return attackable.getAttackRange();
    }

    @Override
    public boolean isAttacking() {
        return attackable.isAttacking();
    }

    @Override
    public boolean tryAttack(float x, float y) {
        return false;
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        return;
    }

    @Override
    public float getRotationDeg() {
        return rotationDeg;
    }

    @Override
    public void deleteBody() {
        if (isDelete)
            return;
        super.deleteBody();
        isDelete = true;
        EventManager.fireEvent(new GameEvent(GameEventType.BULLET_DESTROYED, this));
    }

    public boolean isDelete() {
        return isDelete;
    }
}
