package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;

public interface Attackable {
    int getAttackDamage();
    void setAttackDamage(int damage);
    void updateAttackCooldownTimer(float delta);
    void doAttack();
    boolean tryAttack();
}
