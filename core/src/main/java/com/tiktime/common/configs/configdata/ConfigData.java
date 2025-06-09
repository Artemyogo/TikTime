package com.tiktime.common.configs.configdata;

public class ConfigData {
    private PlayerData player;
    //        private MarksmanEnemyData marksman;
    //        private AnimanEnemyData animan;
    private RusherEnemyData rusher;
    private WallData wall;
    private FloorData floor;
    private Ak47WeaponData ak47;
    private EntityData entity;
    private WeaponData weapon;
    private DynamiteData dynamite;
    private BulletData bullet;
    private FistsWeaponData fists;
    private AnimanEnemyData animan;
    private BossEnemyData boss;
    private float timeStep;
    private int velocityIterations;
    private int positionIterations;
    private float explosionRadius;
    private float explosionForce;
    private float ak47AttackFrameDuration;
    private float ak47IdleFrameDuration;
    private float enemyDeathTime;
    private float enemyBaseDamageTime;
    private float playerBaseDamageTime;
    private float playerDamageTimeLeft;


    public AnimanEnemyData getAniman() {
        return animan;
    }
    public BossEnemyData getBoss() {
        return boss;
    }
    public PlayerData getPlayer() {
        return player;
    }

    public RusherEnemyData getRusher() {
        return rusher;
    }

    public WallData getWall() {
        return wall;
    }

    public FloorData getFloor() {
        return new FloorData(); // currently
        //return floor;
    }

    public EntityData getEntity() {
        return entity;
    }

    public Ak47WeaponData getAk47() {
        return ak47;
    }

    public FistsWeaponData getFists() {
        return fists;
    }

    public WeaponData getWeapon() {
        return weapon;
    }

    public DynamiteData getDynamite() {
        return dynamite;
    }

    public BulletData getBullet() {
        return bullet;
    }

    public float getTimeStep() {
        return timeStep;
    }

    public int getVelocityIterations() {
        return velocityIterations;
    }

    public int getPositionIterations() {
        return positionIterations;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getExplosionForce() {
        return explosionForce;
    }

    public float getAk47AttackFrameDuration() {
        return ak47AttackFrameDuration;
    }

    public float getAk47IdleFrameDuration() {
        return ak47IdleFrameDuration;
    }

    public float getEnemyDeathTime() {
        return enemyDeathTime;
    }

    public float getEnemyBaseDamageTime() {
        return enemyBaseDamageTime;
    }

    public float getPlayerBaseDamageTime() {
        return playerBaseDamageTime;
    }

    public float getPlayerDamageTimeLeft() {
        return playerDamageTimeLeft;
    }
}
