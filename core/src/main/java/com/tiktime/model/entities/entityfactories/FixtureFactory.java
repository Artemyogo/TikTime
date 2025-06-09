package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tiktime.common.WeaponType;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.PhysicsData;
import com.tiktime.model.entities.Category;

public class FixtureFactory {
    private static void setFixture(PhysicsData config, short categoryBits, short maskBits, boolean isSensor, Body body) {
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
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private static void setSensorFixture(float width, float height, short categoryBits, short maskBits, Body body) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = categoryBits;

        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public static void setBulletFixture(Body body) {
        setSensorFixture(GameConfig.getBulletConfig().getWidth(), GameConfig.getBulletConfig().getHeight(), Category.BULLET.getBits(),
            Category.combine(Category.ENEMY, Category.OBSTACLE, Category.DYNAMITE), body);
    }
    public static void setDoorFixture(Body body) {
        setFixture(GameConfig.getFloorConfig(), Category.DOOR.getBits(), Category.PLAYER.getBits(), true, body);
    }
    public static void setWallFixture(Body body) {
        setFixture(GameConfig.getWallConfig(),
            Category.WALL.getBits(),
            Category.combine(Category.PLAYER, Category.BULLET, Category.ENEMY), false, body);
    }
    public static void setDynamiteFixture(Body body) {
        setFixture(GameConfig.getDynamiteConfig(), Category.DYNAMITE.getBits(),
            Category.combine(Category.PLAYER, Category.ENEMY, Category.BULLET), false, body);
    }

    public static void setRusherEnemyFixture(Body body) {
        setFixture(GameConfig.getRusherEnemyConfig(),
            Category.ENEMY_RUSHER.getBits(),
            Category.combine(Category.BULLET, Category.OBSTACLE, Category.DYNAMITE, Category.LIVING_ENTITY),
            false, body);
    }

    public static void setAnimanEnemyFixture(Body body) {
        setFixture(GameConfig.getAnimanEnemyConfig(),
            Category.ENEMY_ANIMAN.getBits(),
            Category.combine(Category.BULLET, Category.OBSTACLE, Category.DYNAMITE, Category.LIVING_ENTITY),
            false, body);
    }

    public static void setPlayerFixtureDef(Body body) {
        setFixture(GameConfig.getPlayerConfig(), Category.PLAYER.getBits(),
            Category.combine(Category.BULLET, Category.DOOR, Category.DYNAMITE, Category.ENEMY, Category.WALL, Category.ENEMY_ATTACK),
            false, body);
    }

    public static void setFistAttackFixture(float width, float height, Body body) {
        setSensorFixture(width, height,
            Category.ENEMY_ATTACK.getBits(),
            Category.combine(Category.PLAYER), body);
    }

}
