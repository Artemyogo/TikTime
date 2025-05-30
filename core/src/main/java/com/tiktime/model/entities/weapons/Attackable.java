package com.tiktime.model.entities.weapons;

public interface Attackable {
    int getDamage();
    void setDamage(int damage);
    void updateAttackCooldownTimer(float delta);
    void doAttack();
    boolean tryAttack(float delta);
}
