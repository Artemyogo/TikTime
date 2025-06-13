package com.tiktime.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.livingenteties.PlayerModel;

public class HealthPotion extends EntityModel implements TouchableByPlayer {
    TiledMapTileLayer.Cell cell;
    int healthToAdd;

    public HealthPotion(Body body, BodyManager bodyManager, TiledMapTileLayer.Cell cell) {
        super(body, bodyManager);
        this.cell = cell;
        this.healthToAdd = 10;
    }

    @Override
    public void deleteBody() {
        super.deleteBody();
        cell.setTile(null);
    }

    @Override
    public void onTouch(PlayerModel player) {
        player.getHealthComponent().regenerateHealth(healthToAdd);
    }
}
