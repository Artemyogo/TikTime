package com.tiktime.model.configs;

import com.tiktime.model.configs.configdata.*;
import com.tiktime.common.WeaponType;

public final class GameConfig {
    private static final ConfigData data = new JsonConfigLoader("config.json").load();

    private GameConfig() {
        throw new AssertionError("Do not instantiate GameConfig");
    }

    public static WeaponData getWeaponConfig(WeaponType weapon) {
        switch (weapon) {
            case AK47:
                return data.getAk47(); // currently weapon == ak47
            case FISTS:
                return data.getWeapon();
            case GLOCK:
            case SHOTGUN:
                return null;
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + weapon);
        }
    }

    public static BulletData getBulletConfig() {
        return data.getBullet();
    }

    public static PlayerData getPlayerConfig() {
        return data.getPlayer();
    }

    public static RusherEnemyData getRusherEnemyConfig() {
        return data.getRusher();
    }

    public static WeaponData getAk47WeaponConfig() {
        return data.getWeapon(); // currently weapon == ak47
    }

    public static WallData getWallConfig() {
        return data.getWall();
    }

    public static FloorData getFloorConfig() {
        return data.getFloor();
    }

    public static DynamiteData getDynamiteConfig() {
        return data.getDynamite();
    }

    /**
     * MARKSMAN(0),
     *     ANIMAN(1),
     *     RUSHER(2);
     *     names of enemies
     */
}
