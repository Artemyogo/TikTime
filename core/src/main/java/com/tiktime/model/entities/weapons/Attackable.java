package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.tiktime.common.Direction;

public interface Attackable {
    int getAttackDamage();
    void setAttackDamage(int damage);
    void updateAttackCooldownTimer(float delta);
    float getAttackRange();
    boolean isAttacking();
}
