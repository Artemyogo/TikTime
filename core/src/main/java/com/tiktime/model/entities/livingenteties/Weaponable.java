package com.tiktime.model.entities.livingenteties;

import com.tiktime.model.entities.weapons.Attackable;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.common.WeaponType;

public interface Weaponable extends Attackable {
    WeaponModel getWeaponModel();
    void setWeaponModel(WeaponModel weaponModel);

}
