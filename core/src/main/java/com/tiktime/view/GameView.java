package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.ScreenConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class GameView {
    private boolean paused = false;
    private boolean debug = true;
//    private final boolean debug = false;
    private boolean isInDoor = false;
    private World world;

    private OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera worldCamera;
    private final OrthographicCamera hudCamera;
    private final SpriteBatch worldBatch;
    private final Viewport worldViewport;
    private final Viewport screenViewport;
    private final Box2DDebugRenderer debugRenderer;
    private final ShapeRenderer hudShape;
    private final SpriteBatch hudBatch;
    private final Stage stage;
    private final TextButton continueButton;
    private final TextButton exitButton;
    private final Table pauseMenu;

    private Map<Integer, EnemyView> enemies;
    private PlayerView player;
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

        stage = new Stage(screenViewport);
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        continueButton = new TextButton("Continue game", new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json")));
        exitButton = new TextButton("Exit to menu", new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json")));
        pauseMenu.center();
        pauseMenu.add(continueButton).width(300).height(80).padBottom(20).row();
        pauseMenu.add(exitButton).width(300).height(80);
        stage.addActor(pauseMenu);
    }

    public void setController(WorldController worldController) {

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.setPaused(false);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.goToMenu();
            }
        });

    }

    ///  TODO DELETE THIS
    public void setWorld(World world) {
        this.world = world;
    }

    public void setPause(boolean paused) {
        this.paused = paused;
        this.player.setPause(paused);
        for (EnemyView e: enemies.values()) {
            e.setPause(paused);
        }

        if (paused) {
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
        }
    }

    public void setMapRenderer(TiledMap map) {
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            for (TiledMapTile tile : tileSet) {
                Texture texture = tile.getTextureRegion().getTexture();
                texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            }
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / PPM);
    }

    public void setPlayer(float x, float y, float width, float height,
                          Direction direction, LivingEntityState state, WeaponType weapon) {
        player = new PlayerView(x, y, width, height, direction, state, weapon);
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
        ArrayList<LivingEntityView> allLivingEntities = new ArrayList<>(enemies.values());
        allLivingEntities.add(player);
//        Gdx.app.log("GameView", );
        allLivingEntities.sort((e1, e2) -> e1.getY() > e2.getY() ? -1 : 1);
//        allLivingEntities.forEach(e -> {Gdx.app.log("Render Live Entity", e.getX() + " " + e.getY());});
        allLivingEntities.forEach(e -> {e.render(delta,  worldBatch);});
        worldBatch.end();

        hudBatch.begin();
        hud.render(delta, hudBatch, hudShape);
        hudBatch.end();

        /// TODO DELETE
        if (debug) {
            debugRenderer.render(world, worldCamera.combined);
        }

        if (isInDoor) {
            printMessageNearDoor(hudBatch);
        }

        if (paused) {
            drawPauseDarkening(delta);
            stage.act(delta);
            stage.draw();
        }
    }

    public void printMessageNearDoor(SpriteBatch batch) {
        float x = screenViewport.getScreenWidth() / 2f - 170f, y = 200f;
        BitmapFont font = new BitmapFont();
        font.getData().scale(2f);
        batch.begin();
        font.draw(batch, "Press 'E' to exit door", x, y);
        batch.end();
    }

    public void setIsInDoor(boolean isInDoor) {
        this.isInDoor = isInDoor;
    }

    public void drawPauseDarkening(float delta) {
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

    public void addEnemy(float x, float y, float width, float height, int id, Direction direction,
                         LivingEntityState state, EnemyType enemyType) {
        switch (enemyType) {
            case RUSHER:
                enemies.put(id, new RusherEnemyView(x, y, width, height, id, direction, state));
                break;
            case ANIMAN:
            case MARKSMAN:
            default:
                throw new RuntimeException("SJOHAGFJIGHSDGJIKHLBSD");
        }
    }

    public void clear() {
        enemies.clear();
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemies.get(id);
        enemyView.setPosition(x, y);
    }

    public void setEnemySize(float width, float height, int id) {
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
        player.setWeaponRotationDeg(rotationDeg);
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

    public PlayerView getPlayerView() {
        return player;
    }
}
