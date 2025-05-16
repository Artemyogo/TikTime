package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WeaponFactory {
    public static WeaponView createWeapon(WeaponType weapon, float x, float y, SpriteBatch batch) {
        switch (weapon) {
            case AK47:
                return new Ak47WeaponView(x, y, batch);
            case GLOCK:
            case SHOTGUN:
                return null;
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + weapon);
        }
    }
}
