package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.model.consts.ScreenConstants;

import java.util.HashMap;
import java.util.Map;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class GameView {
    private WorldController worldController;
    /// TODO DELETE THIS
    private World world;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    private Map<Integer, EnemyView> enemies;
    private PlayerView player;

    public GameView() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        viewport = new ExtendViewport(ScreenConstants.VIEWPORT_WIDTH * PPM, ScreenConstants.VIEWPORT_HEIGHT * PPM, camera);
//        viewport = new ExtendViewport(100 * PPM, 100 * PPM, camera);
        enemies = new HashMap<>();
        viewport.apply();
        camera.update();
    }

    public void setController(WorldController worldController) {
        this.worldController = worldController;
    }

    ///  TODO DELTE THIS
    public void setWorld(World world) {
        this.world = world;
    }

    public void setMapRenderer(TiledMap map) {
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            /// TODO BEWARE IF TILES NOT GOOD MAY BE ERROR
            for (TiledMapTile tile : tileSet) {
                Texture texture = tile.getTextureRegion().getTexture();
                texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void setPlayer(float x, float y, float width, float height, Direction direction, EntityState state) {
        player = new PlayerView(x, y, width, height, direction, state);
        player.updateAnimation();
//        player.
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mapRenderer.setView(camera);
        mapRenderer.render();
        /// TODO DELTE
        debugRenderer.render(world, camera.combined);

        /// TODO SORT BY 'Y' COORD NEED TO
        for (EnemyView enemy : enemies.values()) {
            enemy.render(delta, batch);
        }

        player.render(delta, batch);

        batch.end();
    }

    public void addEnemy(float x, float y, float width, float height, int id, Direction direction,
                         EntityState state, EnemyType enemyType) {
        enemies.put(id, new EnemyView(x, y, width, height, id, direction, state, enemyType));
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setCoordinates(x, y);
    }

    public void setEnemySizes(float width, float height, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setSizes(width, height);
    }

    public void setEnemyState(EntityState state, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setState(state);
    }

    public void setEnemyDirection(Direction direction, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setDirection(direction);
    }

    public void setPlayerCoordinates(float x, float y) {
        player.setCoordinates(x, y);
        camera.position.set(x, y, 0);
        camera.update();
    }

    public void setPlayerSizes(float width, float height) {
        player.setSizes(width, height);
    }

    public void setPlayerState(EntityState state) {
        player.setState(state);
    }

    public void setPlayerDirection(Direction direction) {
        player.setDirection(direction);
    }

    public void show() {
        Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
    }

    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    public void dispose() {
        batch.dispose();
    }
}
