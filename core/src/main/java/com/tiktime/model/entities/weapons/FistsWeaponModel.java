package com.tiktime.model.entities.weapons;

import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.AttackComponent;

public class FistsWeaponModel extends WeaponModel{
    public FistsWeaponModel(AttackComponent attackComponent, float offsetAttackX, float offsetAttackY) {
        super(attackComponent, WeaponType.FISTS, offsetAttackX, offsetAttackY);
    }
}
