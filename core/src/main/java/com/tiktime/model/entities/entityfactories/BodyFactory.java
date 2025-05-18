package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class BodyFactory {
    private static Body createBody(World world, BodyDef bodyDef, FixtureDef fixtureDef) {
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        return body;
    }

    public static Body createWallBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getWallBodyDef(x, y), FixtureFactory.getWallFixture());
    }

    public static Body createDoorBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getDoorBodyDef(x, y), FixtureFactory.getDoorFixture());
    }

    public static Body createPlayerBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getPlayerBodyDef(x, y), FixtureFactory.getPlayerFixtureDef());
    }

    public static Body createRusherEnemyBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getRusherEnemyBodyDef(x, y), FixtureFactory.getRusherEnemyFixture());
    }

    public static Body createBulletBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getBulletBodyDef(x, y), FixtureFactory.getBulletFixture());
    }

    public static List<Body> createBodies(World world, TiledMapTileLayer layer, FixtureDef fixtureDef, BodyDef.BodyType bodyType) {
        /// TODO PEREDELAT ETY HU..
        if(layer == null) return null;
        List<Body> res = new ArrayList<>();
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) == null) continue;
                /// TODO 1f IS BAD
                Body body = createBody(world, BodyDefFactory.getBodyDef(x, y, bodyType, 1f, 1f), fixtureDef);
                body.setUserData(layer.getCell(x, y));
                res.add(body);
            }
        }
        return res;
    }
}
