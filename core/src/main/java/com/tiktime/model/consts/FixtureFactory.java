package com.tiktime.model.consts;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tiktime.model.enums.Category;

public class FixtureFactory {
    public static FixtureDef getDoorFixture() {
        GameConfig.FloorConfig floorConfig = GameConfig.getInstance().getFloorConfig();
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(floorConfig.getHeight() / 2, floorConfig.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = floorShape;

        // Get floor properties from GameConfig
        fixtureDef.density = floorConfig.getDensity();
        fixtureDef.restitution = floorConfig.getRestitution();
        fixtureDef.friction = floorConfig.getFriction();

        fixtureDef.filter.categoryBits = Category.DOOR.getBit();
        fixtureDef.filter.maskBits = Category.PLAYER.getBit();

        fixtureDef.isSensor = true;
        return fixtureDef;

    }
    public static FixtureDef getWallFixture(){
        GameConfig.WallConfig wallConfig = GameConfig.getInstance().getWallConfig();
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(wallConfig.getHeight() / 2, wallConfig.getWidth() / 2);

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
    public static FixtureDef getFloorFixture(){
        GameConfig.FloorConfig floorConfig = GameConfig.getInstance().getFloorConfig();
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

}
