package com.tiktime.view;

public class WeaponFactory {
    public static WeaponView createWeapon(WeaponType weapon, float x, float y) {
        switch (weapon) {
            case AK47:
                return new Ak47WeaponView(x, y);
            case GLOCK:
            case SHOTGUN:
                return null;
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + weapon);
        }
    }
}
