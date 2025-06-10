package com.tiktime.model.entities.weapons;

import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.GunshotAttackComponent;

public final class Ak47WeaponModel extends WeaponModel implements GunshotAttackable {
    private final GunshotAttackComponent attackComponent;

    public Ak47WeaponModel(GunshotAttackComponent gunshotAttackComponent) {
        super(gunshotAttackComponent, WeaponType.AK47);
        attackComponent = gunshotAttackComponent;
    }

    @Override
    public boolean tryAttack(float x, float y) {
        return attackComponent.tryAttack(x, y);
    }


    @Override
    public void updateWeaponRotation(float rotationDeg) {
        attackComponent.updateWeaponRotation(rotationDeg);
    }

    @Override
    public float getRotationDeg() {
        return attackComponent.getRotationDeg();
    }
}
