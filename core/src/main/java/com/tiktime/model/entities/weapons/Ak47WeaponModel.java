package com.tiktime.model.entities.weapons;

import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.AttackComponent;

public class Ak47WeaponModel extends WeaponModel {
    public Ak47WeaponModel(AttackComponent attackComponent) {
        super(attackComponent, WeaponType.AK47);
    }
}
