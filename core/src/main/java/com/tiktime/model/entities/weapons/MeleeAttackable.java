package com.tiktime.model.entities.weapons;

import com.tiktime.common.Direction;

public interface MeleeAttackable extends Attackable {
    boolean tryAttack(float x, float y, float width, float height, Direction direction);
}
