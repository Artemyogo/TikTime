package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.AttackComponent;

public abstract class WeaponModel implements Attackable {
    protected final AttackComponent attackComponent;
    protected final WeaponType weaponType;

    public WeaponModel(AttackComponent attackComponent, WeaponType weaponType) {
        this.attackComponent = attackComponent;
        this.weaponType = weaponType;
    }

    @Override
    public int getAttackDamage() {
        return attackComponent.getAttackDamage();
    }

    @Override
    public void setAttackDamage(int damage) {
        attackComponent.setAttackDamage(damage);
    }

    @Override
    public void updateAttackCooldownTimer(float delta) {
        attackComponent.updateAttackCooldownTimer(delta);
    }

    @Override
    public boolean isAttacking() {
        return attackComponent.isAttacking();
    }

    public void update(float delta) {
        attackComponent.updateAttackCooldownTimer(delta);
    }

    public void setWorld(World world) {
        attackComponent.setWorld(world);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getAttackRange() {
        return attackComponent.getAttackRange();
    }
}
