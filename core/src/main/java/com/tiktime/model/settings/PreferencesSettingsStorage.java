package com.tiktime.model.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesSettingsStorage implements SettingsStorageReader, SettingsStorageWriter {
    private final Preferences preferences;

    PreferencesSettingsStorage(String preferencesName) {
        preferences = Gdx.app.getPreferences(preferencesName);
    }

    @Override
    public float loadUIScale() {
        return preferences.getFloat("uiScale", 1.0f);
    }

    @Override
    public float loadVolume() {
        return preferences.getFloat("volume", 1.0f);
    }

    @Override
    public void save(float uiScale, float volume) {
        preferences.putFloat("uiScale", uiScale);
        preferences.putFloat("volume", volume);
        preferences.flush();
    }
}
