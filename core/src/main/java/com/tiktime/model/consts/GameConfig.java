package com.tiktime.model.consts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public final class GameConfig {
    private static final String CONFIG_FILE_PATH = "config.json";
    private static GameConfig instance;

    private final PlayerConfig playerConfig;
    private final EntityConfig entityConfig;
    private final WallConfig wallConfig;
    private final FloorConfig floorConfig;

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
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
        this.entityConfig = new EntityConfig(configData.entity);

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

    public static final class WallConfig {
        private final float density;
        private final float restitution;
        private final float friction;

        private WallConfig(WallData data) {
            // Use provided values or defaults if null
            this.density = data.density > 0 ? data.density : 1.0f;
            this.restitution = data.restitution > 0 ? data.restitution : 0.0f;
            this.friction = data.friction > 0 ? data.friction : 0.5f;
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
    }

    public static final class FloorConfig {
        private final float density;
        private final float restitution;
        private final float friction;

        private FloorConfig(FloorData data) {
            // Use provided values or defaults if null
            this.density = data.density > 0 ? data.density : 1.0f;
            this.restitution = data.restitution > 0 ? data.restitution : 0.0f;
            this.friction = data.friction > 0 ? data.friction : 0.3f;
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
    }

    public static final class EntityConfig {
        private final float density;
        private final float restitution;
        private final float friction;
        private final float acceleration;

        private EntityConfig(EntityData data) {
            this.density = data.density;
            this.restitution = data.restitution;
            this.friction = data.friction;
            // Default to 10.0f if acceleration is not provided
            this.acceleration = data.acceleration > 0 ? data.acceleration : 10.0f;
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

        public float getAcceleration() {
            return acceleration;
        }
    }

    public static final class PlayerConfig {
        private final int baseHp;
        private final int baseSpeed;
        private final int baseDamage;
        private final int baseRegen;

        private PlayerConfig(PlayerData data) {
            this.baseHp = data.baseHP;
            this.baseSpeed = data.baseSpeed;
            this.baseDamage = data.baseDamage;
            this.baseRegen = data.baseRegen;
        }

        public int getBaseHp() {
            return baseHp;
        }

        public int getBaseSpeed() {
            return baseSpeed;
        }

        public int getBaseDamage() {
            return baseDamage;
        }

        public int getBaseRegen() {
            return baseRegen;
        }
    }

    private static class PlayerData {
        private int baseHP;
        private int baseSpeed;
        private int baseDamage;
        private int baseRegen;
    }

    private static class EntityData {
        private float density;
        private float restitution;
        private float friction;
    }


    private static class WallData {
        private float density;
        private float restitution;
        private float friction;
    }

    private static class FloorData {
        private float density;
        private float restitution;
        private float friction;
    }

    private static class ConfigData {
        private PlayerData player;
        private EntityData entity;
        private WallData wall;
        private FloorData floor;
    }
}
