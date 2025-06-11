package com.tiktime.model.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tiktime.common.MagicConstants;

public class SettingsModel {
    private final PreferencesSettingsStorage preferencesSettingsStorage;

    private boolean shown;
    private float uiScale;
    private float volume;

    private SettingsModel() {
        shown = false;

        preferencesSettingsStorage = new PreferencesSettingsStorage(MagicConstants.SETTINGS_PREFERENCES_NAME);
        load();
    }

    private static SettingsModel settingsModel;

    public static SettingsModel getInstance() {
        if (settingsModel == null) {
            settingsModel = new SettingsModel();
        }

        return settingsModel;
    }

    private void load() {
        uiScale = preferencesSettingsStorage.loadUIScale();
        volume = preferencesSettingsStorage.loadVolume();
    }

    public boolean isShown() {
        return shown;
    }

    public void hide(){
        shown = false;
    }

    public void show(){
        shown = true;
    }

    public float getUiScale() {
        return uiScale;
    }

    public void setUiScale(float uiScale) {
        this.uiScale = uiScale;
        save();
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        save();
    }

    public void save() {
        preferencesSettingsStorage.save(uiScale, volume);
    }

}
