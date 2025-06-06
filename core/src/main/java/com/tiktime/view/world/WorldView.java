package com.tiktime.view.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.common.Pausable;
import com.tiktime.view.consts.ScreenConstants;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.LivingEntityView;
import com.tiktime.view.enteties.livingenteties.PlayerView;
import com.tiktime.common.EnemyType;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyView;
import com.tiktime.view.enteties.livingenteties.enemies.RusherEnemyView;
import com.tiktime.common.WeaponType;
import com.tiktime.view.enteties.weapons.BulletView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class WorldView implements Pausable, Renderable, Disposable {
//    private final boolean debug = true;
    private final boolean debug = false;
    private boolean paused = true;
    private final OrthographicCamera worldCamera;
    private final SpriteBatch worldBatch;
    private final Viewport worldViewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final Box2DDebugRenderer debugRenderer;
    private final Map<Integer, EnemyView> enemyViews = new HashMap<>();
    private final Map<Integer, BulletView> bulletViews = new HashMap<>();
    private PlayerView playerView;
    private World world;

    public WorldView() {
        worldBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        worldCamera = new OrthographicCamera();
        worldViewport = new ExtendViewport(
            ScreenConstants.VIEWPORT_WIDTH,
            ScreenConstants.VIEWPORT_HEIGHT,
            worldCamera);
        worldViewport.update((int) ScreenConstants.VIEWPORT_WIDTH, (int) ScreenConstants.VIEWPORT_HEIGHT, false);
    }

    public void setMapRenderer(TiledMap map) {
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            for (TiledMapTile tile : tileSet) {
                Texture texture = tile.getTextureRegion().getTexture();
                texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            }
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f / PPM);
    }

    public void setPlayerIsAttacked(boolean isAttacked) {
        playerView.setIsAttacked(isAttacked);
    }

    public void setEnemyIsAttacked(int id, boolean isAttacked) {
        enemyViews.get(id).setIsAttacked(isAttacked);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void render(float delta) {
        mapRenderer.setView(worldCamera);
        mapRenderer.render();
        worldBatch.setProjectionMatrix(worldCamera.combined);
        worldBatch.begin();
        ArrayList<LivingEntityView> allLivingEntities = getLivingEntities();
        allLivingEntities.sort((e1, e2) -> {
            if (e1.getY() ==  e2.getY()) {
                return 0;
            }

            return e1.getY() > e2.getY() ? -1 : 1;
        });

        ArrayList<Renderable> renderables = new ArrayList<>(allLivingEntities);
        renderables.addAll(bulletViews.values());
        renderables.forEach(r -> r.render(delta));
        worldBatch.end();

        if (debug) {
            debugRenderer.render(world, worldCamera.combined);
        }
    }

    public void setPlayer(float x, float y, float width, float height, int curHealth, int maxHealth, int coins,
                          Direction direction, LivingEntityState state, WeaponType weapon) {
        playerView = new PlayerView(x, y, width, height, curHealth, maxHealth, coins, direction, state, weapon, worldBatch);
    }

    public void addEnemy(float x, float y, float width, float height, int curHealth, int maxHealth, int id, Direction direction,
                         LivingEntityState state, EnemyType enemyType) {
        switch (enemyType) {
            case RUSHER: {
                enemyViews.put(id, new RusherEnemyView(x, y, width, height, curHealth, maxHealth, id, direction, state, worldBatch));
                break;
            }

            case ANIMAN:
            case MARKSMAN:
            default:
                throw new RuntimeException("Invalid enemyType");
        }
    }

    public void deleteEnemy(int id) {
        enemyViews.remove(id);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public EnemyView getEnemyView(int id) {
        return enemyViews.get(id);
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemyViews.get(id);
        enemyView.setPosition(x, y);
    }


    public void setEnemyState(LivingEntityState state, int id) {
        EnemyView enemyView = enemyViews.get(Integer.valueOf(id));
        enemyView.setState(state);
    }

    public void setEnemyDirection(Direction direction, int id) {
        EnemyView enemyView = enemyViews.get(id);
        enemyView.setDirection(direction);
    }

    public void setPlayerCoordinates(float x, float y) {
        playerView.setPosition(x, y);
        worldCamera.position.set(x, y, 0);
        worldCamera.update();
    }

    public void setPlayerState(LivingEntityState state) {
        playerView.setState(state);
    }

    public void setPlayerDirection(Direction direction) {
        playerView.setDirection(direction);
    }

    public void setPlayerCurHealth(int curHealth) {
        playerView.setCurHealth(curHealth);
    }

    public void setPlayerMaxHealth(int maxHealth) {
        playerView.setMaxHealth(maxHealth);
    }

    public void updatePlayerWeaponRotation(Vector3 screenCoords, Vector3 weaponCoords) {
        Vector3 worldCoords = worldCamera.unproject(screenCoords);
        float dx = worldCoords.x - weaponCoords.x;
        float dy = worldCoords.y - weaponCoords.y;
        float rotationDeg = (float) Math.toDegrees(Math.atan2(dy, dx));
        playerView.updateWeaponRotationDeg(rotationDeg);
    }

    public void clearEnemies() {
        enemyViews.clear();
    }

    public ArrayList<LivingEntityView> getLivingEntities() {
        ArrayList<LivingEntityView> allLivingEntities = new ArrayList<>(enemyViews.values());
        allLivingEntities.add(playerView);
        return allLivingEntities;
    }

    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
        ArrayList<? extends Pausable> allPausables = getLivingEntities();
        allPausables.forEach(p -> p.setPaused(paused));
    }

    @Override
    public void dispose() {
        worldBatch.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }

    public void resize(int width, int height) {
        worldViewport.update(width, height, false);
        worldCamera.update();
    }
}
