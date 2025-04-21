package com.tiktime.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsModel {

    private final Preferences prefs;

    private boolean shown;
    private float uiScale;
    private float volume;

    private SettingsModel() {
        shown = false;

        prefs = Gdx.app.getPreferences("com.tiktime.settings");
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
        uiScale = prefs.getFloat("uiScale", 1f);
        volume = prefs.getFloat("volume", 1f);
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
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void save() {
        prefs.putFloat("uiScale", uiScale);
        prefs.putFloat("volume", volume);
        prefs.flush();
    }

}
