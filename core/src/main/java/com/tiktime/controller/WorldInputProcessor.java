package com.tiktime.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tiktime.model.enums.MovingDirections;

import java.util.ArrayList;
import java.util.Collection;

public class WorldInputProcessor implements InputProcessor {
    private boolean isInDoor = false;
    private final WorldController worldController;
    private final Collection<MovingDirections> directions = new ArrayList<>();
    private int mouseX, mouseY;

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public WorldInputProcessor(WorldController worldController) {
        this.worldController = worldController;
    }

    public Vector2 getDirection() {
        return MovingDirections.combine(directions);
    }

    public void setInDoor(boolean inDoor) {
        isInDoor = inDoor;
    }
    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("INPUT", "Key pressed: " + keycode);
        switch (keycode) {
            case Input.Keys.E:
                if (isInDoor) {
                    worldController.changeMap();
                    return true;
                }
                return false;
            case Input.Keys.W: {
                directions.add(MovingDirections.UP);
                return true;
            }
            case Input.Keys.A: {
                directions.add(MovingDirections.LEFT);
                return true;
            }
            case Input.Keys.S: {
                directions.add(MovingDirections.DOWN);
                return true;
            }
            case Input.Keys.D: {
                directions.add(MovingDirections.RIGHT);
                return true;
            }
            case Input.Keys.ESCAPE: {
                worldController.setPaused(true);
            }
            default: return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W: return directions.remove(MovingDirections.UP);
            case Input.Keys.A: return directions.remove(MovingDirections.LEFT);
            case Input.Keys.S: return directions.remove(MovingDirections.DOWN);
            case Input.Keys.D: return directions.remove(MovingDirections.RIGHT);
            default: return false;
        }
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
        worldController.updateMousePosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
