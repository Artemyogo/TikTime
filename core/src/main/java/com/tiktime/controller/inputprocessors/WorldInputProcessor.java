package com.tiktime.controller.inputprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tiktime.controller.enteties.BulletController;
import com.tiktime.controller.enteties.EnemyController;
import com.tiktime.controller.enteties.PlayerController;
import com.tiktime.controller.world.WorldController;
import com.tiktime.common.Direction;
import com.tiktime.common.Pausable;

import java.util.EnumSet;

public class WorldInputProcessor implements InputProcessor, Pausable {
    private boolean isInDoor = false;
    private boolean paused = false;
    private final WorldController worldController;
    private final EnumSet<Direction> directions = EnumSet.noneOf(Direction.class);
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
        return Direction.combine(directions);
    }

    public void setInDoor(boolean inDoor) {
        isInDoor = inDoor;
    }

    @Override
    public void setPaused(boolean paused) {
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
                directions.add(Direction.UP);
                return true;
            }
            case Input.Keys.A: {
                directions.add(Direction.LEFT);
                return true;
            }
            case Input.Keys.S: {
                directions.add(Direction.DOWN);
                return true;
            }
            case Input.Keys.D: {
                directions.add(Direction.RIGHT);
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
            case Input.Keys.W: return directions.remove(Direction.UP);
            case Input.Keys.A: return directions.remove(Direction.LEFT);
            case Input.Keys.S: return directions.remove(Direction.DOWN);
            case Input.Keys.D: return directions.remove(Direction.RIGHT);
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
        worldController.getPlayerController().setAttacking(true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseMoved(screenX, screenY);
        worldController.getPlayerController().setAttacking(false);
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
        worldController.getPlayerController().updateMousePosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
