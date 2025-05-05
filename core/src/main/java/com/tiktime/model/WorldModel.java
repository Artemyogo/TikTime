package com.tiktime.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tiktime.model.consts.GameConstants;
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

public class WorldModel {
    private World world;
    private TiledMap map;
    private PlayerModel player;
    private Array<EnemyModel> enemies;

    private FixtureDef getFloorFixture(){
        float width = GameConstants.FLOOR_WIDTH, height = GameConstants.FLOOR_HEIGHT;
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(height / 2F, width / 2F);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;

        // Get floor properties from GameConfig
        FloorConfig floorConfig = GameConfig.getInstance().getFloorConfig();
        fixtureDef.density = floorConfig.getDensity();
        fixtureDef.restitution = floorConfig.getRestitution();
        fixtureDef.friction = floorConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.FLOOR.getBit();
        /// I think there should be maskBits = 0
//        fixtureDef.filter.maskBits = Category.combine(Category.BULLET, Category.ENEMY, Category.PLAYER);
        fixtureDef.filter.maskBits = 0;

        floorShape.dispose();
        return fixtureDef;
    }

    private FixtureDef getWallFixture(){
        float width = GameConstants.WALL_WIDTH, height = GameConstants.WALL_HEIGHT;
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(height / 2F, width / 2F);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = wallShape;

        // Get wall properties from GameConfig
        WallConfig wallConfig = GameConfig.getInstance().getWallConfig();
        fixtureDef.density = wallConfig.getDensity();
        fixtureDef.restitution = wallConfig.getRestitution();
        fixtureDef.friction = wallConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.WALL.getBit();
        fixtureDef.filter.maskBits = Category.combine(Category.PLAYER, Category.BULLET, Category.ENEMY);

        wallShape.dispose();
        return fixtureDef;
    }

//    private FixtureDef getPlayerFixture(){
//        float width = GameConstants.PLAYER_WIDTH, height = GameConstants.PLAYER_HEIGHT;
//        PolygonShape playerShape = new PolygonShape();
//        playerShape.setAsBox(height / 2F, width / 2F);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = playerShape;
//
//        // Get player(entity) properties from GameConfig
//        EntityConfig playerConfig = GameConfig.getInstance().getEntityConfig();
//        fixtureDef.density = playerConfig.getDensity();
//        fixtureDef.restitution = playerConfig.getRestitution();
//        fixtureDef.friction = playerConfig.getFriction();
//
//        fixtureDef.filter.categoryBits = Category.PLAYER.getBit();
//        fixtureDef.filter.maskBits = Category.combine(Category.WALL, Category.BULLET);
//
//        playerShape.dispose();
//        return fixtureDef;
//    }
//
//    private FixtureDef getEnemyFixture(String enemyName){
//        float width = GameConstants.ENEMY_WIDTH.get(enemyName), height = GameConstants.ENEMY_HEIGHT.get(enemyName);
//        PolygonShape enemyShape = new PolygonShape();
//        enemyShape.setAsBox(height / 2F, width / 2F);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = enemyShape;
//
//        // Get player(entity) properties from GameConfig
//        EntityConfig playerConfig = GameConfig.getInstance().getEntityConfig();
//        fixtureDef.density = playerConfig.getDensity();
//        fixtureDef.restitution = playerConfig.getRestitution();
//        fixtureDef.friction = playerConfig.getFriction();
//
//        fixtureDef.filter.categoryBits = Category.ENEMY.getBit();
//        fixtureDef.filter.maskBits = Category.combine(Category.WALL, Category.BULLET);
//
//        enemyShape.dispose();
//        return fixtureDef;
//    }

    public WorldModel(TiledMap map) throws IllegalAccessException {
        this.map = map;
        this.world = new World(new Vector2(0, -9.8f), true);


//        this.player = new PlayerModel(world, px, py, playerData);
        /// TODO THERE ALSO
//        Body floorBody = world.createBody(floorDef);

//        floorBody.createFixture(getFloorFixture(h, w));
    }

    EntityData getPlayerData(){
        return player.getData();
    }
}
