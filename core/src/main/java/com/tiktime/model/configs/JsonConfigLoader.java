package com.tiktime.model.configs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.tiktime.model.configs.configdata.ConfigData;

public class JsonConfigLoader implements ConfigLoader {
    private final String configFilePath;

    public JsonConfigLoader(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    @Override
    public ConfigData load() {
        FileHandle configFile;
        try {
            configFile = Gdx.files.internal(configFilePath);
        } catch (GdxRuntimeException e) {
            throw new RuntimeException("Configuration file not found: " + configFilePath, e);
        }

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        ConfigData configData = json.fromJson(ConfigData.class, configFile);

        if (configData == null) {
            throw new RuntimeException("Invalid configuration format");
        }

        return configData;
    }
}
