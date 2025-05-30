package com.tiktime.model.configs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.tiktime.model.configs.configdata.*;

public class ConfigLoader {
    private static final String CONFIG_FILE_PATH = "config.json";

    private ConfigLoader() {
        throw new AssertionError("Do not instantiate ConfigLoader");
    }

    public static ConfigData load() {
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

        return configData;

        /*
        this.entityConfig = new GameConfig.EntityConfig(configData.entity);
        this.playerConfig = new GameConfig.PlayerConfig(configData.entity);
//        this.playerConfig = new PlayerConfig(configData.player);
        this.animanEnemyConfig = new GameConfig.EnemyConfig<>(configData.entity);
//        this.animanEnemyConfig = new AnimanEnemyConfig(configData.animan);
        this.marksmanEnemyConfig = new GameConfig.EnemyConfig<>(configData.entity);
//        this.marksmanEnemyConfig = new MarksmanEnemyConfig(configData.marksman);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.entity);
        this.rusherEnemyConfig = new GameConfig.EnemyConfig<>(configData.rusher);
//        this.rusherEnemyConfig = new RusherEnemyConfig(configData.rusher);
        this.ak47WeaponConfig = new GameConfig.WeaponConfig<>(configData.weapon);
        this.bulletConfig = new GameConfig.BulletConfig(configData.bullet);

        // Initialize wall, floor and dynamite configs - use default values if not present
        if (configData.wall != null) {
            this.wallConfig = new GameConfig.PhysicsConfig<>(configData.wall);
        } else {
            this.wallConfig = new GameConfig.PhysicsConfig<>(new GameConfig.WallData());
        }

        if (configData.floor != null) {
            this.floorConfig = new GameConfig.PhysicsConfig<>(configData.floor);
        } else {
            this.floorConfig = new GameConfig.PhysicsConfig<>(new GameConfig.FloorData());
        }

        if (configData.dynamite != null) {
            this.dynamiteConfig = new GameConfig.PhysicsConfig<>(configData.dynamite);
        } else {
            this.dynamiteConfig = new GameConfig.PhysicsConfig<>(new GameConfig.DynamiteData());
        }
        */
    }
}
