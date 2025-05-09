package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.Main;
import com.tiktime.model.WorldModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.view.Direction;
import com.tiktime.view.EntityState;
import com.tiktime.view.GameView;

import java.io.Console;
import java.util.Iterator;

public class WorldController {
    Game game;
    GameView gameView;
    WorldModel worldModel;
    TiledMap tiledMap;

    public WorldController(Main game, GameView gameView, String mapName) {
        this.game = game;
        this.gameView = gameView;
        this.tiledMap = new TmxMapLoader().load("maps/" + mapName);
        this.worldModel = new WorldModel(tiledMap);
        gameView.setController(this);
        /// TODO DELETE THIS
        gameView.setWorld(worldModel.getWorld());
        gameView.setMapRenderer(this.tiledMap);

        EntityData entityData = worldModel.getPlayerData();
        gameView.setPlayer(
            worldModel.getPlayerPosition().x,
            worldModel.getPlayerPosition().y,
            entityData.getWidth(),
            entityData.getHeight(),
            Direction.EAST,
            EntityState.RUNNING
        );
    }

    public void update(float delta) {
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
        Vector2 position = worldModel.getPlayerPosition();
        gameView.setPlayerCoordinates(position.x, position.y);
    }
}
