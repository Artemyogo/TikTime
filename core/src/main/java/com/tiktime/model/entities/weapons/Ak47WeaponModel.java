package com.tiktime.model.entities.weapons;

import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.components.AttackComponent;

public class Ak47WeaponModel extends WeaponModel implements GunshotAttackable {
    public Ak47WeaponModel(AttackComponent attackComponent) {
        super(attackComponent, WeaponType.AK47);
    }

    @Override
    public boolean tryAttack(float x, float y) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) attackComponent;
        return gunshotAttackable.tryAttack(x + GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetX(),
            y + GameConfig.getWeaponConfig(WeaponType.AK47).getOffsetY());
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) attackComponent;
        gunshotAttackable.updateWeaponRotation(rotationDeg);
    }
}
