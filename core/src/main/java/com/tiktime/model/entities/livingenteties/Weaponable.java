package com.tiktime.model.entities.livingenteties;

import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.common.WeaponType;

public interface Weaponable {
    WeaponType getWeaponType();
    WeaponModel getWeaponModel();
    void setWeaponModel(WeaponModel weaponModel);
}
