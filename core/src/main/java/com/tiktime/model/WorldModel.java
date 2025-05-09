package com.tiktime.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EnemyModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;

import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.GameConfig.FloorConfig;
import com.tiktime.model.consts.GameConfig.WallConfig;
import com.tiktime.model.consts.GameConfig.EntityConfig;
import com.tiktime.model.enums.Category;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.tiktime.model.consts.ScreenConstants.PPM;

public class WorldModel {
    private final World world;
    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

    private final TiledMap map;
    private PlayerModel player;
    private Array<EnemyModel> enemies;

    /// TODO DELETE THIS
    public World getWorld() {
        return world;
    }

    private FixtureDef getFloorFixture(){
        FloorConfig floorConfig = GameConfig.getInstance().getFloorConfig();
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(floorConfig.getHeight() / 2, floorConfig.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;

        // Get floor properties from GameConfig
        fixtureDef.density = floorConfig.getDensity();
        fixtureDef.restitution = floorConfig.getRestitution();
        fixtureDef.friction = floorConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.FLOOR.getBit();
        /// I think there should be maskBits = 0
//        fixtureDef.filter.maskBits = Category.combine(Category.BULLET, Category.ENEMY, Category.PLAYER);
        fixtureDef.filter.maskBits = 0;

        return fixtureDef;
    }

    private FixtureDef getWallFixture(){
        WallConfig wallConfig = GameConfig.getInstance().getWallConfig();
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(PPM * wallConfig.getHeight() / 2, PPM * wallConfig.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = wallShape;

        // Get wall properties from GameConfig
        fixtureDef.density = wallConfig.getDensity();
        fixtureDef.restitution = wallConfig.getRestitution();
        fixtureDef.friction = wallConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.WALL.getBit();
        fixtureDef.filter.maskBits = Category.combine(Category.PLAYER, Category.BULLET, Category.ENEMY);

        return fixtureDef;
    }

    public void update(float delta){
        world.step(delta, velocityIterations, positionIterations);
    }

    public WorldModel(TiledMap map) {
        this.map = map;
        this.world = new World(new Vector2(0, 0), true);
        MapProperties properties = map.getLayers().get("objects").getObjects().get("playerSpawn").getProperties();
        this.player = new PlayerModel(world, properties.get("x", Float.class), properties.get("y", Float.class));
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");

        int width = map.getProperties().get("width", Integer.class);
        int height = map.getProperties().get("height", Integer.class);

        Gdx.app.log("WorldModel", "width: " + width + ", height: " + height);

        for (int x = 0; x < wallLayer.getWidth(); x++) {
            for (int y = 0; y < wallLayer.getHeight(); y++) {
                if (wallLayer.getCell(x, y) == null) continue;
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                WallConfig wallConfig = GameConfig.getInstance().getWallConfig();
                bodyDef.position.set(PPM * (x + wallConfig.getHeight() / 2), PPM * (y + wallConfig.getWidth() / 2));
                Body body = world.createBody(bodyDef);
                body.createFixture(getWallFixture());
                Gdx.app.log("WorldModel", "Creating " + bodyDef.type + " at " + body.getPosition());
            }
        }
        Gdx.app.log("WorldModel", getPlayerPosition().x + " " + getPlayerPosition().y);
    }

    public EntityData getPlayerData(){
        return player.getData();
//        return new EntityData();
    }

    public Vector2 getPlayerPosition(){
        return player.getPosition();
//        return new Vector2(1, 1);
    }

    public void updateMovementDirection(Vector2 movementDirection){
        player.move(movementDirection);
    }
}
