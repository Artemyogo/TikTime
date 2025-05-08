package com.tiktime.model.consts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import sun.font.PhysicalFont;

public final class GameConfig {
    private static final String CONFIG_FILE_PATH = "config.json";
    private static GameConfig instance;

    private final PlayerConfig playerConfig;
    private final WallConfig wallConfig;
    private final FloorConfig floorConfig;
    private final MarksmanEnemyConfig marksmanEnemyConfig;
    private final AnimanEnemyConfig animanEnemyConfig;
    private final RusherEnemyConfig rusherEnemyConfig;
    private final Ak47WeaponConfig ak47WeaponConfig;

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
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

    public WallConfig getWallConfig() {
        return wallConfig;
    }

    public FloorConfig getFloorConfig() {
        return floorConfig;
    }

    private GameConfig() {
        FileHandle configFile = Gdx.files.internal(CONFIG_FILE_PATH);
        if (configFile == null || !configFile.exists()) {
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE_PATH);
        }

        Json json = new Json();
        ConfigData configData = json.fromJson(ConfigData.class, configFile);

        if (configData == null) {
            throw new RuntimeException("Invalid configuration format");
        }

        this.playerConfig = new PlayerConfig(configData.player);
        this.animanEnemyConfig = new AnimanEnemyConfig(configData.animanEnemy);
        this.marksmanEnemyConfig = new MarksmanEnemyConfig(configData.marksmanEnemy);
        this.rusherEnemyConfig = new RusherEnemyConfig(configData.rusherEnemy);
        this.ak47WeaponConfig = new Ak47WeaponConfig(configData.ak47Weapon);

        // Initialize wall and floor configs - use default values if not present
        if (configData.wall != null) {
            this.wallConfig = new WallConfig(configData.wall);
        } else {
            this.wallConfig = new WallConfig(new WallData());
        }

        if (configData.floor != null) {
            this.floorConfig = new FloorConfig(configData.floor);
        } else {
            this.floorConfig = new FloorConfig(new FloorData());
        }
    }

    public static abstract class PhysicsConfig {
        protected float density;
        protected final float restitution;
        protected final float friction;
        protected final float width;
        protected final float height;

        public PhysicsConfig(PhysicsData data) {
            this.density = data.density;
            this.restitution = data.restitution;
            this.friction = data.friction;
            this.width = data.width > 0 ? data.width : 1.0f;
            this.height = data.height > 0 ? data.height : 1.0f;
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

    public static final class WallConfig extends PhysicsConfig {
        private WallConfig(WallData data) {
            super(data);
        }
    }


    public static final class FloorConfig extends PhysicsConfig {
        private FloorConfig(FloorData data) {
            super(data);
        }
    }

    public static abstract class EntityConfig extends PhysicsConfig {
        private int baseHp;
        private float baseSpeed;
        private float baseDamage;
        ///  TODO can move it into PlayerConfig
        private int baseRegen;
        private EntityConfig(EntityData data) {
            super(data);
            this.baseHp = data.baseHp;
            this.baseSpeed = data.baseSpeed;
            this.baseDamage = data.baseDamage;
            this.baseRegen = data.baseRegen;
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

        public int getBaseRegen() {
            return baseRegen;
        }
    }

    public static final class PlayerConfig extends EntityConfig {
        private PlayerConfig(PlayerData data) {
            super(data);
        }
    }

    public static final class MarksmanEnemyConfig extends EntityConfig {
        private MarksmanEnemyConfig(MarksmanEnemyData data) {
            super(data);
        }
    }

    public static final class AnimanEnemyConfig extends EntityConfig {
        private AnimanEnemyConfig(AnimanEnemyData data) {
            super(data);
        }
    }

    public static final class RusherEnemyConfig extends EntityConfig {
        private RusherEnemyConfig(RusherEnemyData data) {
            super(data);
        }
    }

    public static abstract class WeaponsConfig {
        protected int damage;
        protected int fireRate;
        protected int speed;
        protected float width;
        protected float height;

        public WeaponsConfig(WeaponsData data) {
           this.damage = data.damage;
           this.fireRate = data.fireRate;
           this.speed = data.speed;
           this.width = data.width;
           this.height = data.height;
        }

        public int getDamage() {
            return damage;
        }

        public int getFireRate() {
            return fireRate;
        }

        public int getSpeed() {
            return speed;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }

    public static final class Ak47WeaponConfig extends WeaponsConfig {
        private Ak47WeaponConfig(Ak47WeaponData data) {
            super(data);
        }
    }

    private static abstract class PhysicsData {
        protected float density;
        protected float restitution;
        protected float friction;
        protected float width;
        protected float height;
    }

    private static abstract class EntityData extends PhysicsData {
        protected int baseHp;
        protected int baseRegen;
        protected float baseSpeed;
        protected float baseDamage;
    }

    private static final class PlayerData extends EntityData { }

    /**
     * MARKSMAN(0),
     *     ANIMAN(1),
     *     RUSHER(2);
     *     names of enemies
     */

    private static final class MarksmanEnemyData extends EntityData { }
    private static final class AnimanEnemyData extends EntityData { }
    private static final class RusherEnemyData extends EntityData { }

    private static abstract class WeaponsData {
        protected int damage;
        protected int fireRate;
        protected int speed;
        protected float width;
        protected float height;
    }

    private static final class Ak47WeaponData extends WeaponsData { }

    private static class WallData extends PhysicsData { }

    private static class FloorData extends PhysicsData { }

    private static class ConfigData {
        private PlayerData player;
        private MarksmanEnemyData marksman;
        private AnimanEnemyData animan;
        private RusherEnemyData rusher;
        private WallData wall;
        private FloorData floor;
        private Ak47WeaponData ak47;
    }
}
