package com.tiktime.controller.inputprocessors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tiktime.controller.WorldController;
import com.tiktime.model.entities.MovingDirections;
import com.tiktime.view.world.Pausable;

import java.util.ArrayList;
import java.util.Collection;

public class WorldInputProcessor implements InputProcessor, Pausable {
    private boolean isInDoor = false;
    private boolean paused = false;
    private final WorldController worldController;
    /// WHEN CHANGED INPUT CONTROLLER THEN WE NEED TO CLEAR DIRECTIONS
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
    public void setPause(boolean paused) {
        this.paused = paused;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.E: {
                if (isInDoor && !paused) {
                    worldController.changeMap();
                    return true;
                }
                return false;
            }
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
                worldController.changePausedStatus();
                return true;
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
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseMoved(screenX, screenY);
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
