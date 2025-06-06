package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.AttackComponent;

public abstract class WeaponModel implements Attackable {
    AttackComponent attackComponent;
    WeaponType weaponType;

    public WeaponModel(AttackComponent attackComponent, WeaponType weaponType) {
        this.attackComponent = attackComponent;
        this.weaponType = weaponType;
    }

    @Override
    public int getAttackDamage() {
        return attackComponent.getDamage();
    }

    @Override
    public void setAttackDamage(int damage) {
        attackComponent.setDamage(damage);
    }

    @Override
    public void updateAttackCooldownTimer(float delta) {
        attackComponent.updateAttackCycle(delta);
    }

    @Override
    public void doAttack() {
        attackComponent.doAttack();
    }

    @Override
    public boolean tryAttack(float delta) {
        return attackComponent.tryAttack();
    }

    public void update(float delta) {
        attackComponent.updateAttackCycle(delta);
    }

    public void setWorld(World world) {
        attackComponent.setWorld(world);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getRange() {
        return attackComponent.getAttackRange();
    }
}
