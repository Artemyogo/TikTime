package com.tiktime.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.tiktime.common.MagicConstants;
import com.tiktime.common.sounds.SoundLimiter;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.livingenteties.PlayerModel;

public class HealthPotion extends EntityModel implements TouchableByPlayer, SoundOnTouch {
    private static final SoundLimiter soundLimiter = new SoundLimiter(MagicConstants.HEALTH_POTION_TOUCH_SOUND_COOLDOWN);

    private final TiledMapTileLayer.Cell cell;
    private final Sound pickupSound;
    private int healthToAdd;

    public HealthPotion(int healthToAdd, Body body, BodyManager bodyManager, TiledMapTileLayer.Cell cell) {
        super(body, bodyManager);
        this.cell = cell;
        this.pickupSound = Gdx.audio.newSound(Gdx.files.internal(MagicConstants.HEALTH_POTION_SOUND_PATH));
        this.healthToAdd = healthToAdd;
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

    @Override
    public void playSound() {
        if (soundLimiter.canPlay()) {
            pickupSound.play();
        }
    }
}
