package com.tiktime.model.consts;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tiktime.model.enums.Category;

public class FixtureFactory {
    private static FixtureDef getFixture(GameConfig.PhysicsConfig config, short categoryBits, short maskBits, boolean isSensor){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(config.getWidth() / 2, config.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = config.getDensity();
        fixtureDef.restitution = config.getRestitution();
        fixtureDef.friction = config.getFriction();

        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;

        fixtureDef.isSensor = isSensor;
        return fixtureDef;
    }
    public static FixtureDef getDoorFixture() {
        return getFixture(GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.FLOOR), Category.DOOR.getBits(), Category.PLAYER.getBits(), true);
    }
    public static FixtureDef getWallFixture(){
        return getFixture(GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.WALL),
            Category.WALL.getBits(),
            Category.combine(Category.PLAYER, Category.BULLET, Category.ENEMY), false);
    }
    public static FixtureDef getDynamiteFixture(){
        return getFixture(GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.DYNAMITE), Category.DYNAMITE.getBits(),
            Category.combine(Category.PLAYER, Category.ENEMY), false);
    }

    public static FixtureDef getRusherEnemyFixture(){
        return getFixture(GameConfig.getInstance().getRusherEnemyConfig(),
            Category.ENEMY_RUSHER.getBits(),
            Category.combine(Category.BULLET, Category.WALL, Category.DYNAMITE, Category.LIVING_ENTITY),
            false);
    }

    public static FixtureDef getPlayerFixtureDef(){
        return getFixture(GameConfig.getInstance().getPlayerConfig(), Category.PLAYER.getBits(),
            Category.combine(Category.BULLET, Category.DOOR, Category.DYNAMITE, Category.ENEMY, Category.WALL),
            false);
    }

//    public static FixtureDef getBulletFixtureDef(){
//        return getFixture(GameConfig.getInstance().getBulletConfig(), )
//    }

//    public static FixtureDef getEntityFixtureDef(Category category){
//        return getFixture(GameConfig.getInstance().getEntityConfig(), category.getBits(),
//            Category.combine(Category.PLAYER, Category.BULLET, Category.DOOR, Category.DYNAMITE, Category.ENEMY, Category.WALL),
//            false);
//    }
}
