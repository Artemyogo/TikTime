package com.tiktime.controller;

import com.badlogic.gdx.InputProcessor;

public class WorldInputProcessor implements InputProcessor {
    private boolean isInDoor = false;
    private final WorldController worldController;

    public WorldInputProcessor(WorldController worldController) {
        this.worldController = worldController;
    }

    public void setInDoor(boolean inDoor) {
        isInDoor = inDoor;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(isInDoor && keycode == 'e') {
            worldController.changeMap();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
