package com.tiktime.model.consts;

import com.badlogic.gdx.Gdx;
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
        Gdx.app.log("WorldModel", "Creating " + bodyDef.type + " at " + body.getPosition());
        return body;
    }

    public static Body createWallBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getWallBodyDef(x, y), FixtureFactory.getWallFixture());
    }

    public static Body createDoorBody(World world, float x, float y) {
        return createBody(world, BodyDefFactory.getDoorBodyDef(x, y), FixtureFactory.getWallFixture());
    }

    public static List<Body> createBodies(World world, TiledMapTileLayer layer, BodyDef bodyDef, FixtureDef fixtureDef) {
        if(layer == null) return null;
        List<Body> res = new ArrayList<>();
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) == null) continue;
                res.add(createBody(world, bodyDef, fixtureDef));
            }
        }
        return res;
    }
}
