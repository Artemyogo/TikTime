package com.tiktime.controller;

public class SettingsModel {
    boolean shown;
    float uiScale;
    public SettingsModel(){
        shown = false;
        uiScale = 1;
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
}
