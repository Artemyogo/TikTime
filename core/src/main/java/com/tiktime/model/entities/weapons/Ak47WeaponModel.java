package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.components.GunshotAttackComponent;

public class Ak47WeaponModel extends WeaponModel implements GunshotAttackable {
    public Ak47WeaponModel(GunshotAttackComponent gunshotAttackComponent) {
        super(gunshotAttackComponent, WeaponType.AK47);
    }

    @Override
    public boolean tryAttack(float x, float y) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) attackComponent;

        // TODO: make bullet from end of weapon
        return gunshotAttackable.tryAttack(x, y);
    }

    @Override
    public void updateWeaponRotation(float rotationDeg) {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) attackComponent;
        gunshotAttackable.updateWeaponRotation(rotationDeg);
    }

    @Override
    public float getRotationDeg() {
        GunshotAttackable gunshotAttackable = (GunshotAttackable) attackComponent;
        return gunshotAttackable.getRotationDeg();
    }
}
