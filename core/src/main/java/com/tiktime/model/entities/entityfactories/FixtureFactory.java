package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.PhysicsData;
import com.tiktime.model.entities.Category;

public class FixtureFactory {
    private static FixtureDef getFixture(PhysicsData config, short categoryBits, short maskBits, boolean isSensor){
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

    private static FixtureDef getSensorFixture(float width, float height, short categoryBits, short maskBits){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = categoryBits;

//        fixtureDef.density = 1;
//        fixtureDef.restitution = 1;
//        fixtureDef.friction = 1;

        fixtureDef.filter.maskBits = maskBits;
//        fixtureDef.isSensor = false;
        fixtureDef.isSensor = true;

        return fixtureDef;
    }
    public static FixtureDef getBulletFixture() {
        return getFixture(GameConfig.getBulletConfig(), Category.BULLET.getBits(),
            Category.combine(Category.ENEMY, Category.PLAYER, Category.OBSTACLE), false);
    }
    public static FixtureDef getDoorFixture() {
        return getFixture(GameConfig.getFloorConfig(), Category.DOOR.getBits(), Category.PLAYER.getBits(), true);
    }
    public static FixtureDef getWallFixture(){
        return getFixture(GameConfig.getWallConfig(),
            Category.WALL.getBits(),
            Category.combine(Category.PLAYER, Category.BULLET, Category.ENEMY), false);
    }
    public static FixtureDef getDynamiteFixture(){
        return getFixture(GameConfig.getDynamiteConfig(), Category.DYNAMITE.getBits(),
            Category.combine(Category.PLAYER, Category.ENEMY), false);
    }

    public static FixtureDef getRusherEnemyFixture(){
        return getFixture(GameConfig.getRusherEnemyConfig(),
            Category.ENEMY_RUSHER.getBits(),
            Category.combine(Category.BULLET, Category.WALL, Category.DYNAMITE, Category.LIVING_ENTITY),
            false);
    }

    public static FixtureDef getPlayerFixtureDef(){
        return getFixture(GameConfig.getPlayerConfig(), Category.PLAYER.getBits(),
            Category.combine(Category.BULLET, Category.DOOR, Category.DYNAMITE, Category.ENEMY, Category.WALL, Category.ENEMY_ATTACK),
            false);
    }

    public static FixtureDef getFistAttackFixture(float width, float height) {
        return getSensorFixture(width, height,
            Category.ENEMY_ATTACK.getBits(),
            Category.combine(Category.PLAYER));
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
