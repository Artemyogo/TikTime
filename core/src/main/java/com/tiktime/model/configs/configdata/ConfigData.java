package com.tiktime.model.configs.configdata;

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

    public WeaponData getWeapon() {
        return weapon;
    }

    public DynamiteData getDynamite() {
        return dynamite;
    }

    public BulletData getBullet() {
        return bullet;
    }
}
