package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.model.consts.ScreenConstants;

import java.util.HashMap;
import java.util.Map;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class GameView {
    private boolean paused = false;
//    private boolean debug = true;
    private final boolean debug = false;
    private WorldController worldController;
    /// TODO DELETE THIS
    private World world;

    private final OrthographicCamera camera;
    private final OrthographicCamera hudCamera;
    private final SpriteBatch batch;
    private final Viewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final Box2DDebugRenderer debugRenderer;
    private final ShapeRenderer shapeRenderer;

    private Map<Integer, EnemyView> enemies;
    private PlayerView player;
    private HudView hud;

    public GameView() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConstants.VIEWPORT_WIDTH / PPM, ScreenConstants.VIEWPORT_HEIGHT / PPM);
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
        viewport = new ExtendViewport(
            ScreenConstants.VIEWPORT_WIDTH,
            ScreenConstants.VIEWPORT_HEIGHT,
            camera);
//        camera.zoom = 1f;
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

    public void setPause(boolean paused) {
        this.paused = paused;
        this.player.setPause(paused);
        for (EnemyView e: enemies.values()) {
            e.setPause(paused);
        }
    }

    public void setMapRenderer(TiledMap map) {
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            /// TODO BEWARE IF TILES NOT GOOD MAY BE ERROR
            for (TiledMapTile tile : tileSet) {
                Texture texture = tile.getTextureRegion().getTexture();
                texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / PPM);
    }

    public void setPlayer(float x, float y, float width, float height, Direction direction, EntityState state) {
        player = new PlayerView(x, y, width, height, direction, state);
        player.updateAnimation();
    }

    public void setHud(float width, float height, int curHealth, int maxHealth, int coins) {
        hud = new HudView(
            Gdx.graphics.getWidth() - width - 25,
            Gdx.graphics.getHeight() - height - 25,
            width,
            height,
            curHealth,
            maxHealth,
            coins);

    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        /// TODO SORT BY 'Y' COORD NEED TO
        for (EnemyView enemy : enemies.values()) {
            enemy.render(delta, batch);
        }

        player.render(delta, batch);
        batch.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        hud.render(delta, batch);
        batch.end();

        /// TODO DELTE
        if (debug) {
            debugRenderer.render(world, camera.combined);
        }

        if (paused) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

//            float width = ScreenConstants.VIEWPORT_WIDTH * PPM;
//            float height = ScreenConstants.VIEWPORT_HEIGHT * PPM;
            float width = Gdx.graphics.getWidth();
            float height = Gdx.graphics.getHeight();
//            Gdx.app.log("GameView", "width: " + width + ", height: " + height);
//            Gdx.app.log("GameView", "width: " + ScreenConstants.VIEWPORT_WIDTH * PPM + ", height: " + ScreenConstants.VIEWPORT_HEIGHT * PPM);
            float centerX = width / 2f;
            float centerY = height / 2f;
            int steps = 40;
            float maxAlpha = 0.75f;

            float sliceH = centerY / steps;
            float sliceW = centerX / steps;

            for (int i = 0; i < steps; i++) {
                float t = i / (float)(steps - 1);
                float alpha = t * maxAlpha;
                shapeRenderer.setColor(0, 0, 0, alpha);

                shapeRenderer.rect(
                    0,
                    centerY + i * sliceH,
                    width,
                    sliceH
                );

                shapeRenderer.rect(
                    0,
                    centerY - (i + 1) * sliceH,
                    width,
                    sliceH
                );

                shapeRenderer.rect(
                    centerX - (i + 1) * sliceW,
                    0,
                    sliceW,
                    height
                );

                shapeRenderer.rect(
                    centerX + i * sliceW,
                    0,
                    sliceW,
                    height
                );
            }

            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void addEnemy(float x, float y, float width, float height, int id, Direction direction,
                         EntityState state, EnemyType enemyType) {
        enemies.put(id, new EnemyView(x, y, width, height, id, direction, state, enemyType));
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setPosition(x, y);
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
        player.setPosition(x, y);
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
        shapeRenderer.dispose();
    }
}
