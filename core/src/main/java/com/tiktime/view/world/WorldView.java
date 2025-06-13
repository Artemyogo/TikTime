package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.tiktime.common.*;
import com.tiktime.view.consts.ScreenConstants;
import com.tiktime.view.enteties.livingenteties.LivingEntityView;
import com.tiktime.view.enteties.livingenteties.PlayerView;
import com.tiktime.view.enteties.livingenteties.enemies.AllEnemyView;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyView;
import com.tiktime.view.enteties.livingenteties.enemies.RusherEnemyView;
import com.tiktime.view.enteties.weapons.AllBulletsView;
import com.tiktime.view.enteties.weapons.BulletView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.tiktime.view.consts.ScreenConstants.PPM;

public class WorldView implements Renderable, Disposable {
    private final boolean debug = MagicConstants.DEBUG_WORLD_VIEW;

    private final OrthographicCamera worldCamera = new OrthographicCamera();
    private final SpriteBatch worldBatch = new SpriteBatch();
    private final Viewport worldViewport = new ExtendViewport(
        ScreenConstants.VIEWPORT_WIDTH,
        ScreenConstants.VIEWPORT_HEIGHT,
        worldCamera);
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private OrthogonalTiledMapRenderer mapRenderer;

    private final AllEnemyView allEnemyView = new AllEnemyView(worldBatch);
    private final AllBulletsView allBulletsView = new AllBulletsView(worldBatch);
    private PlayerView playerView;
    private World world;

    public WorldView() {
        worldViewport.update(ScreenConstants.VIEWPORT_WIDTH, ScreenConstants.VIEWPORT_HEIGHT, false);
    }

    public AllEnemyView getAllEnemyView() {
        return allEnemyView;
    }

    public AllBulletsView getAllBulletsView() {
        return allBulletsView;
    }

    public OrthographicCamera getWorldCamera() {
        return worldCamera;
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

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void render(float delta) {
        worldCamera.position.set(playerView.getX(), playerView.getY(), 0);
        worldCamera.update();

        mapRenderer.setView(worldCamera);
        mapRenderer.render();

        worldBatch.setProjectionMatrix(worldCamera.combined);
        worldBatch.begin();
        ArrayList<LivingEntityView> allLivingEntities = getLivingEntities();
        sortByY(allLivingEntities);
        ArrayList<Renderable> renderables = new ArrayList<>(allLivingEntities);
        renderables.addAll(allBulletsView.getBulletViews());
        renderables.forEach(r -> r.render(delta));
        worldBatch.end();

        if (debug) {
            if (weaponCoords != null && screenCoords != null) {
                float x1 = weaponCoords.x, y1 = weaponCoords.y;
                float x2 = screenCoords.x, y2 = screenCoords.y;
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.setProjectionMatrix(worldCamera.combined);
                Gdx.gl.glLineWidth(2);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.line(x1, y1, x2, y2);
                shapeRenderer.end();
            }
            debugRenderer.render(world, worldCamera.combined);
        }
    }

    public PlayerView setAndGetPlayer(float x, float y, float width, float height, int curHealth, int maxHealth, int coins,
                          Direction direction, LivingEntityState state, WeaponType weapon) {
        return playerView = new PlayerView(x, y, width, height, curHealth, maxHealth, coins, direction, state, weapon, worldBatch);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    // TODO: DELETE THIS
    // THIS IS FOR DEBUG LOL DONT DELETE THIS
    Vector3 weaponCoords, screenCoords;
    public void setWeaponCoords(Vector3 weaponCoords) {
        this.weaponCoords = weaponCoords;
    }

    public void setScreenCoords(Vector3 screenCoords) {
        this.screenCoords = screenCoords;
    }

    private ArrayList<LivingEntityView> getLivingEntities() {
        ArrayList<LivingEntityView> allLivingEntities = new ArrayList<>(allEnemyView.getEnemyViews());
        allLivingEntities.add(playerView);
        return allLivingEntities;
    }

    public void sortByY(ArrayList<LivingEntityView> allLivingEntities) {
        allLivingEntities.sort((e1, e2) -> {
            if (e1.getY() - e1.getHeight() / 2f ==  e2.getY() - e2.getHeight() / 2f) {
                return 0;
            }

            return e1.getY() - e1.getHeight() / 2f > e2.getY() - e2.getHeight() / 2f ? -1 : 1;
        });
    }

    public void resize(int width, int height) {
        worldViewport.update(width, height, false);
        worldCamera.update();
    }

    @Override
    public void dispose() {
        worldBatch.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }
}
