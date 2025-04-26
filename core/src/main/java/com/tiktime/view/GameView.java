package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tiktime.controller.WorldController;

public class GameView {
    private WorldController worldController;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Array<WallView> walls;
    private Array<FloorView> floors;
    private Array<EnemyView> enemies;
    private PlayerView player;

    private int WorldWidth, WorldHeight;

    public GameView(WorldController worldController, int WorldWidth, int WorldHeight) {
        this.WorldWidth = WorldWidth;
        this.WorldHeight = WorldHeight;
        this.worldController = worldController;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WorldWidth, WorldHeight, camera);
        batch = new SpriteBatch();
    }

    public void setController(WorldController worldController) {
        this.worldController = worldController;
    }



    public void setViewport(ExtendViewport viewport) {

    }

    public void render(float delta) {
        batch.begin();

        for (FloorView floor : floors) {
            floor.render(delta, batch);
        }

        for (WallView wall : walls) {
            wall.render(delta, batch);
        }

        for (EnemyView enemy : enemies) {
            enemy.render(delta, batch);
        }

        player.render(delta, batch);

        batch.end();
    }

    public void show() {
        Gdx.input.setInputProcessor((InputProcessor) batch);
    }

    public void resize(int width, int height) {
        viewport().update(width, height, true);
    }


    public void dispose() {
        batch.dispose();
    }
}
