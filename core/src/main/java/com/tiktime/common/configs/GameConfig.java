package com.tiktime.common.configs;

import com.tiktime.common.configs.configdata.*;
import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.Category;

public final class GameConfig {
    private static final ConfigData data = new JsonConfigLoader("config.json").load();

    private GameConfig() {
        throw new AssertionError("Do not instantiate GameConfig");
    }

    public static WeaponData getWeaponConfig(WeaponType weapon) {
        switch (weapon) {
            case AK47:
                return data.getAk47();
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

    /*
    public static RusherEnemyData getRusherEnemyConfig() {
        return data.getRusher();
    }

    public static AnimanEnemyData getAnimanEnemyConfig() { return data.getAniman();}

    public static BossEnemyData getBossEnemyConfig() { return data.getBoss();}
    */

    public static EnemyData getEnemyConfig(Category enemy) {
        switch (enemy) {
            case ENEMY_RUSHER: return data.getRusher();
            case ENEMY_ANIMAN: return data.getAniman();
            case ENEMY_BOSS: return data.getBoss();
            default: throw new IllegalArgumentException("Unknown enemy");
        }
    }

    public static WeaponData getAk47WeaponConfig() {
        return data.getAk47();
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

    public static HealthPotionData getHealthPotionConfig() {
        return data.getHealthPotion();
    }

    /**
     * MARKSMAN(0),
     *     ANIMAN(1),
     *     RUSHER(2);
     *     names of enemies
     */
}
