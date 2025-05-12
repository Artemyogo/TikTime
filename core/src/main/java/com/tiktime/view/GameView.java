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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.ScreenConstants;

import java.util.HashMap;
import java.util.Map;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class GameView {
    private boolean paused = false;
//    private boolean debug = true;
    private final boolean debug = false;
    /// TODO DELETE THIS
    private World world;

    private final OrthographicCamera worldCamera;
    private final OrthographicCamera hudCamera;
    private final SpriteBatch worldBatch;
    private final Viewport worldViewport;
    private final Viewport screenViewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final Box2DDebugRenderer debugRenderer;
    private final ShapeRenderer hudShape;
    private final SpriteBatch hudBatch;

    private Map<Integer, EnemyView> enemies;
    private PlayerView player;
    private WeaponView weapon;
    private HudView hud;

    public GameView() {
        worldCamera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();

        worldBatch = new SpriteBatch();
        hudShape = new ShapeRenderer();
        hudBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        worldViewport = new ExtendViewport(
            ScreenConstants.VIEWPORT_WIDTH,
            ScreenConstants.VIEWPORT_HEIGHT,
            worldCamera);
        worldViewport.update((int) ScreenConstants.VIEWPORT_WIDTH, (int) ScreenConstants.VIEWPORT_HEIGHT, false);
        screenViewport = new ScreenViewport(hudCamera);
        screenViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        enemies = new HashMap<>();
    }

    public void setController(WorldController worldController) {}

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

    public void setPlayer(float x, float y, float width, float height, Direction direction, LivingEntityState state) {
        player = new PlayerView(x, y, width, height, direction, state);
        weapon = new Ak47WeaponView(x, y);
    }

    public void setHud(int curHealth, int maxHealth, int coins) {
        hud = new HudView(
            screenViewport,
            curHealth,
            maxHealth,
            coins);
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(worldCamera);
        mapRenderer.render();

        worldBatch.setProjectionMatrix(worldCamera.combined);
        hudBatch.setProjectionMatrix(hudCamera.combined);
        hudShape.setProjectionMatrix(hudCamera.combined);

        worldBatch.begin();
        /// TODO SORT BY 'Y' COORD NEED TO
//        for (EnemyView enemy : enemies.values()) {
//            enemy.render(delta, worldBatch);
//        }
        player.render(delta, worldBatch);
        weapon.render(delta, worldBatch);
        worldBatch.end();

        hudBatch.begin();
        hud.render(delta, hudBatch, hudShape);
        hudBatch.end();

        /// TODO DELTE
        if (debug) {
            debugRenderer.render(world, worldCamera.combined);
        }

        if (paused) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            hudShape.setProjectionMatrix(hudCamera.combined);
            hudShape.begin(ShapeRenderer.ShapeType.Filled);

            float width = screenViewport.getScreenWidth();
            float height = screenViewport.getScreenHeight();
            int stepW = 100;
            int stepH = 100;
            float rectWidth = width / stepW;
            float rectHeight = height / stepH;
            float centerX = width / 2f;
            float centerY = height / 2f;
            float maxDist = (float) Math.sqrt(centerX * centerX + centerY * centerY);
            float maxAlpha = 1.1f;

//            Gdx.app.log("GameView", width + "x" + height);
//            Gdx.app.log("GameView", centerX + "x" + centerY);
//            Gdx.app.log("GameView", centerX + "x" + centerY);
            for (int i = 0; i < stepW; i++) {
                for (int j = 0; j < stepH; j++) {
                    float x = i * rectWidth, y = j * rectHeight;
                    float curDist = (float) Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                    float t = (maxAlpha * (curDist / maxDist));
                    float alpha = t * maxAlpha;
                    hudShape.setColor(0, 0, 0, alpha);
                    hudShape.rect(
                        x,
                        y,
                        rectWidth,
                        rectHeight
                    );
                }
            }

            hudShape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void addEnemy(float x, float y, float width, float height, int id, Direction direction,
                         LivingEntityState state, EnemyType enemyType) {
        enemies.put(id, new EnemyView(x, y, width, height, id, direction, state, enemyType));
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setPosition(x, y);
    }

    public void setEnemySizes(float width, float height, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setSize(width, height);
    }

    public void setEnemyState(LivingEntityState state, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setState(state);
    }

    public void setEnemyDirection(Direction direction, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setDirection(direction);
    }

    public void setPlayerCoordinates(float x, float y) {
        player.setPosition(x, y);
        weapon.setPosition(x, y);
        worldCamera.position.set(x, y, 0);
        worldCamera.update();
    }

    public void setPlayerSizes(float width, float height) {
        player.setSize(width, height);
    }

    public void setPlayerState(LivingEntityState state) {
        player.setState(state);
    }

    public void setPlayerDirection(Direction direction) {
        player.setDirection(direction);
    }

    public void updatePlayerWeaponRotation(Vector3 screenCoords, Vector3 weaponCoords) {
        Vector3 worldCoords = worldCamera.unproject(screenCoords);
        float dx = worldCoords.x - weaponCoords.x;
        float dy = worldCoords.y - weaponCoords.y;
        float rotationDeg = (float) Math.toDegrees(Math.atan2(dy, dx));
        weapon.setRotationDeg(rotationDeg);
    }

    public void setPlayerCurHealth(int curHealth) {
        hud.setCurHealth(curHealth);
    }

    public void show() {
        Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
    }

    public void resize(int width, int height) {
        worldViewport.update(width, height, false);
        screenViewport.update(width, height, false);
        hudCamera.update();
        worldCamera.update();
        hudCamera.setToOrtho(false, width, height);
    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    public void dispose() {
        worldBatch.dispose();
        hudBatch.dispose();
        hudShape.dispose();
        hud.dispose();
    }
}
