package com.tiktime.model.entities.weapons;

import com.tiktime.common.WeaponType;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.components.AttackComponent;

public class Ak47WeaponModel extends WeaponModel {
    public Ak47WeaponModel(AttackComponent attackComponent, float offsetAttackX, float offsetAttackY) {
        super(attackComponent, WeaponType.AK47, offsetAttackX, offsetAttackY);
    }
}
