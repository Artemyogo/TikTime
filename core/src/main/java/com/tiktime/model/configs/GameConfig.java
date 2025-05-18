package com.tiktime.model.configs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tiktime.view.enteties.weapons.WeaponType;

public final class GameConfig {
    private static final String CONFIG_FILE_PATH = "config.json";
    private static GameConfig instance;

    // TODO: single responsibility (data loading to another class)

    private final PlayerConfig playerConfig;
    private final EnemyConfig<EntityData> marksmanEnemyConfig;
    private final EnemyConfig<EntityData> animanEnemyConfig;
    private final EnemyConfig<EntityData> rusherEnemyConfig;
    private final WeaponConfig<WeaponData> ak47WeaponConfig;
    private final EntityConfig entityConfig;
    private final PhysicsConfig<WallData> wallConfig;
    private final PhysicsConfig<FloorData> floorConfig;
    private final PhysicsConfig<DynamiteData> dynamiteConfig;

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public WeaponConfig<?> getWeaponConfig(WeaponType weapon) {
        switch (weapon) {
            case AK47:
                return getAk47WeaponConfig();
            case GLOCK:
            case SHOTGUN:
                return null;
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + weapon);
        }
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public EnemyConfig<?> getAnimanEnemyConfig() {
        return animanEnemyConfig;
    }

    public EnemyConfig<?> getMarksmanEnemyConfig() {
        return marksmanEnemyConfig;
    }

    public EnemyConfig<?> getRusherEnemyConfig() {
        return rusherEnemyConfig;
    }

    public WeaponConfig<?> getAk47WeaponConfig() {
        return ak47WeaponConfig;
    }

    private GameConfig() {
        FileHandle configFile = Gdx.files.internal(CONFIG_FILE_PATH);
        if (configFile == null || !configFile.exists()) {
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE_PATH);
        }

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        ConfigData configData = json.fromJson(ConfigData.class, configFile);

        if (configData == null) {
            throw new RuntimeException("Invalid configuration format");
        }

        this.entityConfig = new EntityConfig(configData.entity);
        this.playerConfig = new PlayerConfig(configData.entity);
//        this.playerConfig = new PlayerConfig(configData.player);
        this.animanEnemyConfig = new EnemyConfig<>(configData.entity);
//        this.animanEnemyConfig = new AnimanEnemyConfig(configData.animan);
        this.marksmanEnemyConfig = new EnemyConfig<>(configData.entity);
//        this.marksmanEnemyConfig = new MarksmanEnemyConfig(configData.marksman);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.entity);
        this.rusherEnemyConfig = new EnemyConfig<>(configData.rusher);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.rusher);
        this.ak47WeaponConfig = new WeaponConfig<>(configData.weapon);

        // Initialize wall, floor and dynamite configs - use default values if not present
        if (configData.wall != null) {
            this.wallConfig = new PhysicsConfig<>(configData.wall);
        } else {
            this.wallConfig = new PhysicsConfig<>(new WallData());
        }

        if (configData.floor != null) {
            this.floorConfig = new PhysicsConfig<>(configData.floor);
        } else {
            this.floorConfig = new PhysicsConfig<>(new FloorData());
        }

        if (configData.dynamite != null) {
            this.dynamiteConfig = new PhysicsConfig<>(configData.dynamite);
        } else {
            this.dynamiteConfig = new PhysicsConfig<>(new DynamiteData());
        }

    }

    public PhysicsConfig<?> getWallConfig() {
        return wallConfig;
    }

    public PhysicsConfig<?> getFloorConfig() {
        return floorConfig;
    }

    public PhysicsConfig<?> getDynamiteConfig() {
        return dynamiteConfig;
    }

    public static class PhysicsConfig<D extends PhysicsData> {
        private final float density;
        private final float restitution;
        private final float friction;
        private final float width;
        private final float height;

        public PhysicsConfig(D data) {
            this.density = data.density;
            this.restitution = data.restitution;
            this.friction = data.friction;
            this.width = data.width;
            this.height = data.height;
        }

        public float getDensity() {
            return density;
        }

        public float getRestitution() {
            return restitution;
        }

        public float getFriction() {
            return friction;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }

    public static class EntityConfig extends PhysicsConfig<EntityData> {
        protected int baseHp;
        protected float baseSpeed;
        protected float baseDamage;

        private EntityConfig(EntityData data) {
            super(data);
            this.baseHp = data.baseHp;
            this.baseSpeed = data.baseSpeed;
            this.baseDamage = data.baseDamage;
        }

        public int getBaseHp() {
            return baseHp;
        }

        public float getBaseSpeed() {
            return baseSpeed;
        }

        public float getBaseDamage() {
            return baseDamage;
        }
    }

    public static final class PlayerConfig extends EntityConfig {
        private int baseRegen;
        private PlayerConfig(PlayerData data) {
            super(data);
        }
        private PlayerConfig(EntityData data) {
            super(data);
            this.baseRegen = 5;
        }

        public int getBaseRegen() {
            return baseRegen;
        }
    }

    public static final class EnemyConfig<D extends EntityData> extends EntityConfig {
        private EnemyConfig(D data) {
            super(data);
        }
    }

    public static final class WeaponConfig<D extends WeaponData> {
        private final int damage;
        private final int fireRate;
        private final float reloadTime;
        private final float width;
        private final float height;
        private final float offsetX;
        private final float offsetY;

        private WeaponConfig(D data) {
            this.damage = data.damage;
            this.fireRate = data.fireRate;
            this.reloadTime = data.reloadTime;
            this.width = data.width;
            this.height = data.height;
            this.offsetX = data.offsetX;
            this.offsetY = data.offsetY;
        }

        public int getDamage() {
            return damage;
        }

        public int getFireRate() {
            return fireRate;
        }

        public float getReloadTime() {
            return reloadTime;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public float getOffsetX() {
            return offsetX;
        }

        public float getOffsetY() {
            return offsetY;
        }
    }

    private static class PhysicsData {
        protected float density;
        protected float restitution;
        protected float friction;
        protected float width;
        protected float height;
    }

    private static class EntityData extends PhysicsData {
        protected int baseHp;
        protected float baseSpeed;
        protected float baseDamage;
    }

    private static final class PlayerData extends EntityData {
        private int baseRegen;
    }

    /**
     * MARKSMAN(0),
     *     ANIMAN(1),
     *     RUSHER(2);
     *     names of enemies
     */

    private static final class MarksmanEnemyData extends EntityData { }
    private static final class AnimanEnemyData extends EntityData { }
    private static final class RusherEnemyData extends EntityData { }

    private static class WeaponData {
        protected int damage;
        protected int fireRate;
        protected float reloadTime;
        protected float width;
        protected float height;
        protected float offsetX;
        protected float offsetY;
    }

    private static final class Ak47WeaponData extends WeaponData {}

    private static class WallData extends PhysicsData {}

    private static class FloorData extends PhysicsData {}

    private static class DynamiteData extends PhysicsData {}

    private static class ConfigData {
        private PlayerData player;
        //        private MarksmanEnemyData marksman;
//        private AnimanEnemyData animan;
        private RusherEnemyData rusher;
        private WallData wall;
        private FloorData floor;
        //        private Ak47WeaponData ak47;
        private EntityData entity;
        private WeaponData weapon;
        private DynamiteData dynamite;
    }
}
