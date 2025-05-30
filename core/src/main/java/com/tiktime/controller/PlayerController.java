package com.tiktime.controller;

import com.badlogic.gdx.math.Vector2;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.model.entities.weapons.WeaponType;
import com.tiktime.view.world.WorldView;

public class PlayerController {
    PlayerModel player;
    WorldView worldView;
    public PlayerController(PlayerModel playerModel, WorldView worldView) {
        this.player = playerModel;
        this.worldView = worldView;
        worldView.setPlayer(
            player.getPosition().x,
            player.getPosition().y,
            playerModel.getWidth(),
            playerModel.getHeight(),
            playerModel.getCurrentHealth(),
            playerModel.getMaxHealth(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    void update(float delta, Vector2 direction) {
        player.setDirectionAndMove(direction, delta);
//        Gdx.app.log("PlayerController", player.getCurrentHealth() + "/" + player.getMaxHealth());
        worldView.setPlayerCurHealth(player.getCurrentHealth());
        worldView.setPlayerMaxHealth(player.getMaxHealth());
        Vector2 playerPosition = player.getPosition();
        worldView.setPlayerCoordinates(playerPosition.x, playerPosition.y);
        if (direction.equals(Vector2.Zero)) {
            worldView.setPlayerState(LivingEntityState.IDLE);
        } else {
            if (direction.x != 0) {
                worldView.setPlayerDirection(Direction.getDirection(direction));
            }
            worldView.setPlayerState(LivingEntityState.RUNNING);
        }
    }
}
