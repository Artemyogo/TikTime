package com.tiktime.common.configs;

import com.tiktime.common.configs.configdata.*;
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
                return data.getFists();
            case GLOCK:
            case SHOTGUN:
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

    public static AnimanEnemyData getAnimanEnemyConfig() { return data.getAniman();}
    public static BossEnemyData getBossEnemyConfig() { return data.getBoss();}

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

    public static float getTimeStep() {
        return data.getTimeStep();
    }

    public static int getVelocityIterations() {
        return data.getVelocityIterations();
    }

    public static int getPositionIterations() {
        return data.getPositionIterations();
    }

    public static float getExplosionRadius() {
        return data.getExplosionRadius();
    }

    public static float getExplosionForce() {
        return data.getExplosionForce();
    }

    public static float getAk47AttackFrameDuration() {
        return data.getAk47AttackFrameDuration();
    }

    public static float getAk47IdleFrameDuration() {
        return data.getAk47IdleFrameDuration();
    }

    public static float getEnemyDeathTime() {
        return data.getEnemyDeathTime();
    }

    public static float getEnemyBaseDamageTime() {
        return data.getEnemyBaseDamageTime();
    }

    public static float getPlayerBaseDamageTime() {
        return data.getPlayerBaseDamageTime();
    }

    public static float getPlayerDamageTimeLeft() {
        return data.getPlayerDamageTimeLeft();
    }

    /**
     * MARKSMAN(0),
     *     ANIMAN(1),
     *     RUSHER(2);
     *     names of enemies
     */
}
