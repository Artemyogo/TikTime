package com.tiktime.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.Main;
import com.tiktime.model.WorldModel;
import com.tiktime.view.Direction;
import com.tiktime.view.EntityState;
import com.tiktime.view.GameView;

import java.io.Console;
import java.util.Iterator;

public class WorldController {
    Game game;
    GameView gameView;
    WorldModel worldModel;
    private boolean[] keys;
    TiledMap tiledMap;
    private float deltaX;
    private float deltaY;
    private final float deltaXAdd = 1.0f;
    private final float deltaYAdd = 1.0f;
    float x;
    float y;
    InputProcessor inputProcessor;

    public WorldController(Main game, GameView gameView, String mapName) {
        this.game = game;
        this.gameView = gameView;
        this.keys = new boolean[256];
        this.deltaX = 0;
        this.deltaY = 0;
//        System.out.println(Gdx.files.internal("maps/" + mapName).file().getAbsolutePath());
        this.tiledMap = new TmxMapLoader().load("maps/" + mapName);
        Gdx.app.log("WorldController111111", tiledMap.getProperties().get("width")  + " "
            + tiledMap.getProperties().get("height"));
//        this.worldModel = new WorldModel(tiledMap);
        gameView.setController(this);
        gameView.setMapRenderer(this.tiledMap);
//        for (MapLayer m : tiledMap.getLayers()) {
//            Gdx.app.log("WorldController", m.getName());
//        }
//        MapLayer m = tiledMap.getLayers().get("Objects");
//        for (MapObject obj : m.getObjects()) {
//            Gdx.app.log("WorldController", obj.getName());
//
//        }

        MapObject mapObject = tiledMap.getLayers().get("Objects").getObjects().get("PlayerSpawn");
        MapProperties playerProperties = mapObject.getProperties();
//        Iterator<String> g = mapObject.getProperties().getKeys();
//        while (g.hasNext()) {
//            Gdx.app.log("worldController", "g: " + g.next());
//        }

        this.x = playerProperties.get("x", Float.class);
        this.y = playerProperties.get("y", Float.class);
//        x = 4;
//        y = 5;
        gameView.setPlayer(x,
            y,
            30,
            30,
//            playerProperties.get("Width", Float.class),
//            playerProperties.get("Height", Float.class),
            Direction.EAST,
            EntityState.RUNNING
            );

        /// TODO WHEN WORLD MODEL WILL BE COMPLETE
//        Gdx.input.setInputProcessor(inputProcessor);

//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean keyDown(int keycode) {
//                if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
//                    boolean moveChanged = (!keys[Input.Keys.D] && !keys[Input.Keys.RIGHT]);
//                    keys[keycode] = true;
//                    if (moveChanged) {
//                        worldModel.setMovingRight(true);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.A ||  keycode == Input.Keys.LEFT) {
//                    boolean moveChanged = (!keys[Input.Keys.A] && !keys[Input.Keys.LEFT]);
//                    keys[keycode] = true;
//                    if (moveChanged) {
//                        worldModel.setMovingLeft(true);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
//                    boolean moveChanged = (!keys[Input.Keys.W] && !keys[Input.Keys.UP]);
//                    keys[keycode] = true;
//                    if (moveChanged) {
//                        worldModel.setMovingUp(true);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
//                    boolean moveChanged = (!keys[Input.Keys.S] && !keys[Input.Keys.DOWN]);
//                    keys[keycode] = true;
//                    if (moveChanged) {
//                        worldModel.setMovingDown(true);
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//
//            @Override
//            public boolean keyUp(int keycode) {
//                if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
//                    boolean moveChanged = (!keys[Input.Keys.D] || !keys[Input.Keys.RIGHT]);
//                    keys[keycode] = false;
//                    if (moveChanged) {
//                        worldModel.setMovingRight(false);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.A ||  keycode == Input.Keys.LEFT) {
//                    boolean moveChanged = (!keys[Input.Keys.A] || !keys[Input.Keys.LEFT]);
//                    keys[keycode] = false;
//                    if (moveChanged) {
//                        worldModel.setMovingLeft(false);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
//                    boolean moveChanged = (!keys[Input.Keys.W] || !keys[Input.Keys.UP]);
//                    keys[keycode] = false;
//                    if (moveChanged) {
//                        worldModel.setMovingUp(false);
//                    }
//                    return true;
//                }
//
//                if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
//                    boolean moveChanged = (!keys[Input.Keys.S] || !keys[Input.Keys.DOWN]);
//                    keys[keycode] = false;
//                    if (moveChanged) {
//                        worldModel.setMovingDown(false);
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//        });
        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
                    boolean moveChanged = (!keys[Input.Keys.D] && !keys[Input.Keys.RIGHT]);
                    keys[keycode] = true;
                    if (moveChanged) {
                        deltaX += deltaXAdd;
//                        worldModel.setMovingRight(true);
                    }
                    return true;
                }

                if (keycode == Input.Keys.A ||  keycode == Input.Keys.LEFT) {
                    boolean moveChanged = (!keys[Input.Keys.A] && !keys[Input.Keys.LEFT]);
                    keys[keycode] = true;
                    if (moveChanged) {
                        deltaX -= deltaXAdd;
//                        worldModel.setMovingLeft(true);
                    }
                    return true;
                }

                if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
                    boolean moveChanged = (!keys[Input.Keys.W] && !keys[Input.Keys.UP]);
                    keys[keycode] = true;
                    if (moveChanged) {
                        deltaY += deltaYAdd;
//                        worldModel.setMovingUp(true);
                    }
                    return true;
                }

                if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
                    boolean moveChanged = (!keys[Input.Keys.S] && !keys[Input.Keys.DOWN]);
                    keys[keycode] = true;
                    if (moveChanged) {
                        deltaY -= deltaYAdd;
//                        worldModel.setMovingDown(true);
                    }
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
                    boolean moveChanged = (!keys[Input.Keys.D] || !keys[Input.Keys.RIGHT]);
                    keys[keycode] = false;
                    if (moveChanged) {
                        deltaX -= deltaXAdd;
//                        worldModel.setMovingRight(false);
                    }
                    return true;
                }

                if (keycode == Input.Keys.A ||  keycode == Input.Keys.LEFT) {
                    boolean moveChanged = (!keys[Input.Keys.A] || !keys[Input.Keys.LEFT]);
                    keys[keycode] = false;
                    if (moveChanged) {
                        deltaX += deltaXAdd;
//                        worldModel.setMovingLeft(false);
                    }
                    return true;
                }

                if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
                    boolean moveChanged = (!keys[Input.Keys.W] || !keys[Input.Keys.UP]);
                    keys[keycode] = false;
                    if (moveChanged) {
                        deltaY -= deltaYAdd;
//                        worldModel.setMovingUp(false);
                    }
                    return true;
                }

                if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
                    boolean moveChanged = (!keys[Input.Keys.S] || !keys[Input.Keys.DOWN]);
                    keys[keycode] = false;
                    if (moveChanged) {
                        deltaY += deltaYAdd;
//                        worldModel.setMovingDown(false);
                    }
                    return true;
                }

                return false;
            }
        };
        gameView.setInputProcessor(inputProcessor);
    }

    /// TODO
    public void update(float delta) {
        x += deltaX;
        y += deltaY;
        gameView.setPlayerCoordinates(x, y);
//        worldModel.update(delta);
    }
}
