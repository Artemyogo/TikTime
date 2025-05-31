package com.tiktime.controller.inputprocessors;

import com.badlogic.gdx.InputProcessor;
import com.tiktime.controller.world.WorldController;

public class SeparatorFromWorldInputProcessor implements InputProcessor {
    private final WorldController worldController;

    public SeparatorFromWorldInputProcessor(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public boolean keyDown(int i) {
        return !worldController.getPaused();
    }

    @Override
    public boolean keyUp(int i) {
        return !worldController.getPaused();
    }

    @Override
    public boolean keyTyped(char c) {
        return !worldController.getPaused();
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return !worldController.getPaused();
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return !worldController.getPaused();
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return !worldController.getPaused();
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return !worldController.getPaused();
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return !worldController.getPaused();
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return !worldController.getPaused();
    }
}
