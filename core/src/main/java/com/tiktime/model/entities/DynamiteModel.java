package com.tiktime.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.world.BodyManager;

public class DynamiteModel extends EntityModel {
    TiledMapTileLayer.Cell cell;
    public DynamiteModel(Body body, BodyManager bodyManager, TiledMapTileLayer.Cell cell) {
        super(body, bodyManager);
        this.cell = cell;
    }

    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }
}
