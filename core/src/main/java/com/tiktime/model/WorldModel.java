package com.tiktime.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.enums.Category;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;

import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.GameConfig.FloorConfig;
import com.tiktime.model.consts.GameConfig.WallConfig;
import com.tiktime.model.enums.Category;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class WorldModel {
    private World world;
    private TiledMap map;
    private PlayerModel player;

    private FixtureDef getFloorFixture(int h, int w){
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(h/2F, w/2F);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;

        // Get floor properties from GameConfig
        FloorConfig floorConfig = GameConfig.getInstance().getFloorConfig();
        fixtureDef.density = floorConfig.getDensity();
        fixtureDef.restitution = floorConfig.getRestitution();
        fixtureDef.friction = floorConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.FLOOR.getBit();
        fixtureDef.filter.maskBits = Category.combine(Category.BULLET, Category.ENEMY, Category.PLAYER);

        floorShape.dispose();
        return fixtureDef;
    }

    private FixtureDef getWallFixture(){
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(0.5f, 0.5f);

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

    public WorldModel(int h, int w, EntityData playerData, float px, float py, TiledMap map) throws IllegalAccessException {
        this.map = map;



        world = new World(new Vector2(0, 0), true);

        this.player = new PlayerModel(world, px, py, playerData);
        /// TODO THERE ALSO
//        Body floorBody = world.createBody(floorDef);

//        floorBody.createFixture(getFloorFixture(h, w));
    }

    EntityData getPlayerData(){
        return player.getData();
    }
}
