package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.WeaponType;

public class WeaponFactory {
    public static WeaponView createWeapon(WeaponType weapon, float x, float y, SpriteBatch batch, float attackFrameDuration) {
        switch (weapon) {
            case AK47:
                return new Ak47WeaponView(x, y, batch, attackFrameDuration);
            case GLOCK:
            case SHOTGUN:
                return null;
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + weapon);
        }
    }
}
