package com.tiktime.model.entities.weapons;

public interface Attackable {
    int getAttackDamage();
    void setAttackDamage(int damage);
    void updateAttackCooldownTimer(float delta);
    void doAttack();
    boolean tryAttack(float delta);
}
