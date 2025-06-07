package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.Direction;
import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.entityfactories.BodyFactory;

public class FistsWeaponModel extends WeaponModel implements MeleeAttackable {
    public FistsWeaponModel(AttackComponent attackComponent) {
        super(attackComponent, WeaponType.FISTS);
    }

    @Override
    public boolean tryAttack(float x, float y, float width, float height, Direction direction) {
        MeleeAttackable meleeAttackable = (MeleeAttackable) attackComponent;
        return meleeAttackable.tryAttack(x, y, width, height, direction);
    }
}
