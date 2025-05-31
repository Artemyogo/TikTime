package com.tiktime.model.entities.weapons;

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
    public int getDamage() {
        return attackComponent.getDamage();
    }

    @Override
    public void setDamage(int damage) {
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
        return attackComponent.tryAttack(delta);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
