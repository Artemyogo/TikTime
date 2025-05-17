package com.tiktime.model.consts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tiktime.view.enteties.weapons.WeaponType;

import java.util.HashMap;
import java.util.Map;

public final class GameConfig {
    private static final String CONFIG_FILE_PATH = "config.json";
    private static GameConfig instance;

    private final PlayerConfig playerConfig;
    private final MarksmanEnemyConfig marksmanEnemyConfig;
    private final AnimanEnemyConfig animanEnemyConfig;
    private final RusherEnemyConfig rusherEnemyConfig;
    private final Ak47WeaponConfig ak47WeaponConfig;
    private final EntityConfig entityConfig;
    private final Map<StaticObjectType, StaticObjectConfig<?>> staticObjectConfigs;

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public WeaponConfig getWeaponConfig(WeaponType weapon) {
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

    public AnimanEnemyConfig getAnimanEnemyConfig() {
        return animanEnemyConfig;
    }

    public MarksmanEnemyConfig getMarksmanEnemyConfig() {
        return marksmanEnemyConfig;
    }

    public RusherEnemyConfig getRusherEnemyConfig() {
        return rusherEnemyConfig;
    }

    public Ak47WeaponConfig getAk47WeaponConfig() {
        return ak47WeaponConfig;
    }

    public enum StaticObjectType {
        WALL,
        FLOOR,
        DYNAMITE
    }

    public StaticObjectConfig<?> getStaticObjectConfig(StaticObjectType type) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");

        StaticObjectConfig<?> config = staticObjectConfigs.get(type);
        if (config == null) {
            throw new RuntimeException("No config found");
        }
        return config;
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
        this.animanEnemyConfig = new AnimanEnemyConfig(configData.entity);
//        this.animanEnemyConfig = new AnimanEnemyConfig(configData.animan);
        this.marksmanEnemyConfig = new MarksmanEnemyConfig(configData.entity);
//        this.marksmanEnemyConfig = new MarksmanEnemyConfig(configData.marksman);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.entity);
        this.rusherEnemyConfig = new RusherEnemyConfig(configData.rusher);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.rusher);
        this.ak47WeaponConfig = new Ak47WeaponConfig(configData.weapon);

        this.staticObjectConfigs = new HashMap<>();

        // Initialize wall, floor and dynamite configs - use default values if not present
        if (configData.wall != null) {
            this.staticObjectConfigs.put(StaticObjectType.WALL, new StaticObjectConfig<>(configData.wall));
        } else {
            this.staticObjectConfigs.put(StaticObjectType.WALL, new StaticObjectConfig<>(new WallData()));
        }

        if (configData.floor != null) {
            this.staticObjectConfigs.put(StaticObjectType.FLOOR, new StaticObjectConfig<>(configData.floor));
        } else {
            this.staticObjectConfigs.put(StaticObjectType.FLOOR, new StaticObjectConfig<>(new FloorData()));
        }

        if (configData.dynamite != null) {
            this.staticObjectConfigs.put(StaticObjectType.DYNAMITE, new StaticObjectConfig<>(configData.dynamite));
        } else {
            this.staticObjectConfigs.put(StaticObjectType.DYNAMITE, new StaticObjectConfig<>(new DynamiteData()));
        }

    }

    public static abstract class PhysicsConfig {
        protected float density;
        protected final float restitution;
        protected final float friction;
        protected final float width;
        protected final float height;

        private PhysicsConfig(PhysicsData data) {
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

    public static final class StaticObjectConfig<D extends PhysicsData> extends PhysicsConfig {
        private StaticObjectConfig(D data) {
            super(data);
        }
    }

    public static class EntityConfig extends PhysicsConfig {
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

    public static final class MarksmanEnemyConfig extends EntityConfig {
        private MarksmanEnemyConfig(MarksmanEnemyData data) {
            super(data);
        }
        private MarksmanEnemyConfig(EntityData data) {
            super(data);
        }
    }

    public static final class AnimanEnemyConfig extends EntityConfig {
        private AnimanEnemyConfig(AnimanEnemyData data) {
            super(data);
        }
        private AnimanEnemyConfig(EntityData data) {
            super(data);
        }
    }

    public static final class RusherEnemyConfig extends EntityConfig {
        private RusherEnemyConfig(RusherEnemyData data) {
            super(data);
        }
        private RusherEnemyConfig(EntityData data) {
            super(data);
        }
    }

    public static class WeaponConfig {
        protected int damage;
        protected int fireRate;
        protected float reloadTime;
        protected float width;
        protected float height;
        protected float offsetX;
        protected float offsetY;

        private WeaponConfig(WeaponData data) {
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

    public static final class Ak47WeaponConfig extends WeaponConfig {
        private Ak47WeaponConfig(Ak47WeaponData data) {
            super(data);
        }

        private Ak47WeaponConfig(WeaponData data) {
            super(data);
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
