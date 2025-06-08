package com.tiktime.model.entities.weapons;

public interface GunshotAttackable extends Attackable {
    boolean tryAttack(float x, float y);
    void updateWeaponRotation(float rotationDeg);
}
