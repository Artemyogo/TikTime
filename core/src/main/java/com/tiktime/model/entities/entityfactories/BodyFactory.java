package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.MagicConstants;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.weapons.MeleeAttackable;

import java.util.ArrayList;
import java.util.List;

public class BodyFactory {
    private static Body createBody(World world, BodyDef bodyDef) {
        Body body = world.createBody(bodyDef);
        return body;
    }

    public static Body createWallBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getWallBodyDef(x, y));
        FixtureFactory.setWallFixture(body);
        return body;
    }

    public static Body createDoorBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getDoorBodyDef(x, y));
        FixtureFactory.setDoorFixture(body);
        return body;
    }

    public static Body createPlayerBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getPlayerBodyDef(x, y));
        FixtureFactory.setPlayerFixtureDef(body);
        return body;
    }

    public static Body createEnemyBody(World world, float x, float y, Category enemy) {
        if (!Category.ENEMY.intercept(enemy.getBits())) throw new IllegalArgumentException("Unknown enemy");

        Body body = createBody(world, BodyDefFactory.getEnemyBodyDef(x, y, enemy));
        FixtureFactory.setEnemyFixture(body, enemy);
        return body;
    }

    /*
    public static Body createRusherEnemyBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getRusherEnemyBodyDef(x, y));
        FixtureFactory.setRusherEnemyFixture(body);
        return body;
    }

    public static Body createAnimanEnemyBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getAnimanEnemyBodyDef(x, y));
        FixtureFactory.setAnimanEnemyFixture(body);
        return body;
    }

    public static Body createBossEnemyBody(World world, float x, float y) {
        Body body = createBody(world, BodyDefFactory.getBossEnemyBodyDef(x, y));
        FixtureFactory.setBossFixture(body);
        return body;
    }
    */

    public static Body createBulletBody(World world, float x, float y, Vector2 direction) {
        Body body = createBody(world, BodyDefFactory.getBulletBodyDef(x, y));
        body.setBullet(true);
        body.setLinearVelocity(direction.scl(GameConfig.getBulletConfig().getBaseSpeed()));
        FixtureFactory.setBulletFixture(body);
        return body;
    }

    public static Body createFistAttackBody(World world, float x, float y, float width, float height, MeleeAttackable meleeAttackable) {
        Body body = createBody(world, BodyDefFactory.getFistAttackBodyDef(x, y, width, height));
        FixtureFactory.setFistAttackFixture(width, height, body);
        body.setUserData(meleeAttackable);
        return body;
    }

    public static ArrayList<Body> createBodiesOnLayer(World world, TiledMapTileLayer layer, Category category, BodyDef.BodyType bodyType) {
        ArrayList<Body> res = new ArrayList<>();
        if(layer == null) return res;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) == null) continue;
                Body body = createBody(world, BodyDefFactory.getBodyDef(x, y, bodyType, MagicConstants.BODY_WIDTH, MagicConstants.BODY_HEIGHT));
                switch (category) {
                    case DOOR: {
                        FixtureFactory.setDoorFixture(body);
                        break;
                    }
                    case DYNAMITE: {
                        FixtureFactory.setDynamiteFixture(body);
                        break;
                    }
                    case WALL: {
                        FixtureFactory.setWallFixture(body);
                        break;
                    }
                    case HEALTH_POTION: {
                        FixtureFactory.setHealthPotionFixture(body);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid category");
                    }
                }
                body.setUserData(layer.getCell(x, y));
                res.add(body);
            }
        }
        return res;
    }
}
