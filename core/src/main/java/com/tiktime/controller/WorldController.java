package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tiktime.Main;
import com.tiktime.controller.utils.MapSelector;
import com.tiktime.model.WorldModel;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;
import com.tiktime.view.Direction;
import com.tiktime.view.LivingEntityState;
import com.tiktime.view.GameView;
import com.tiktime.view.WeaponType;

public class WorldController {
    Game game;
    GameView gameView;
    WorldModel worldModel;
    MapSelector mapSelector;
    boolean paused = false;
    boolean isInDoor = false;

    public WorldController(Main game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        this.mapSelector = new MapSelector();
        TiledMap map = mapSelector.getMap();
        this.worldModel = new WorldModel(map, new CollisionController(this));
        gameView.setController(this);
        ///TODO DELETE THIS
        gameView.setWorld(worldModel.getWorld());
        ///
        gameView.setMapRenderer(map);
        EntityData entityData = worldModel.getPlayerData();
        gameView.setPlayer(
            worldModel.getPlayerPosition().x,
            worldModel.getPlayerPosition().y,
            entityData.getWidth(),
            entityData.getHeight(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );
        gameView.setHud(entityData.getCurrentHealth(), entityData.getMaxHealth(), PlayerModel.CurrentStats.getCoins());
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            gameView.setPause(paused);
        }

        if (paused) {
            return;
        }

        if (isInDoor && Gdx.input.isKeyPressed(Input.Keys.E)) {
            isInDoor = false;
            TiledMap map = mapSelector.getMap();
            this.worldModel = new WorldModel(map, new CollisionController(this));
            gameView.setMapRenderer(map);
        }

        Vector2 direction = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction.x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction.y -= 1;
        }

        worldModel.updateMovementDirection(direction);
        worldModel.update(delta);

        Vector2 playerPosition = worldModel.getPlayerPosition();
        gameView.setPlayerCoordinates(playerPosition.x, playerPosition.y);
        if (direction.equals(Vector2.Zero)) {
            gameView.setPlayerState(LivingEntityState.IDLE);
        } else {
            if (!direction.equals(new Vector2(0, 1)) && !direction.equals(new Vector2(0, -1))) {
                gameView.setPlayerDirection(getDirection(direction));
            }
            gameView.setPlayerState(LivingEntityState.RUNNING);
        }

        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameView.updatePlayerWeaponRotation(mousePosition, getWeaponPosition(playerPosition.x, playerPosition.y, WeaponType.AK47));
        gameView.setPlayerCurHealth(worldModel.getPlayerData().getCurrentHealth());
    }

    Vector3 getWeaponPosition(float x, float y, WeaponType weapon) {
        GameConfig.WeaponConfig weaponConfig = GameConfig.getInstance().getWeaponConfig(weapon);
        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        float xn = x + weaponConfig.getOffsetX();
        float yn = y + weaponConfig.getOffsetY();
        float width = weaponConfig.getWidth();
        float height = weaponConfig.getHeight();
        return new Vector3(xn - width / 2f, yn - height / 2f, 0);
    }

    Direction getDirection(Vector2 dir) {
        if (dir == null) {
            throw new IllegalArgumentException("Direction argument was null");
        }

        if (dir.equals(new Vector2(1f, 1f))) {
//            this.angleDeg = 45;
            return Direction.NORTH_EAST;
        } else if (dir.equals(new Vector2(-1f, 1f))) {
//            this.angleDeg = 135;
            return Direction.NORTH_WEST;
        } else if (dir.equals(new Vector2(1f, -1f))) {
//            this.angleDeg = 315;
            return Direction.SOUTH_EAST;
        } else if (dir.equals(new Vector2(-1f, -1f))) {
//            this.angleDeg = 225;
            return Direction.SOUTH_WEST;
        } else if (dir.equals(new Vector2(0, 1f))) {
//            this.angleDeg = 90;
            return Direction.NORTH;
        } else if (dir.equals(new Vector2(0, -1f))) {
//            this.angleDeg = 270;
            return Direction.SOUTH;
        } else if (dir.equals(new Vector2(1f, 0))) {
//            this.angleDeg = 0;
            return Direction.EAST;
        } else if (dir.equals(new Vector2(-1f, 0))) {
//            this.angleDeg = 180;
            return Direction.WEST;
        } else {
            throw new IllegalArgumentException("Direction argument was incorrect");
        }
    }

    public void onDoorEntry(){
        isInDoor = true;
    }
    public void onDoorExit(){
        isInDoor = false;
    }

    public void explosion(float x, float y, float radius, float force) {
        worldModel.explosion(x, y, radius, force);
    }


}
