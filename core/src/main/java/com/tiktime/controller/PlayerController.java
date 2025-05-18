package com.tiktime.controller;

import com.badlogic.gdx.math.Vector2;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.view.enteties.weapons.WeaponType;
import com.tiktime.view.world.GameView;

public class PlayerController {
    PlayerModel player;
    GameView gameView;
    public PlayerController(PlayerModel playerModel, GameView gameView) {
        this.player = playerModel;
        this.gameView = gameView;
        gameView.setPlayer(
            player.getPosition().x,
            player.getPosition().y,
            playerModel.getWidth(),
            entityData.getHeight(),
            entityData.getCurrentHealth(),
            entityData.getMaxHealth(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    void update(float delta, Vector2 direction) {
        player.move(direction, delta);
        Vector2 playerPosition = player.getPosition();
        gameView.setPlayerCoordinates(playerPosition.x, playerPosition.y);
        if (direction.equals(Vector2.Zero)) {
            gameView.setPlayerState(LivingEntityState.IDLE);
        } else {
            if (direction.x != 0) {
                gameView.setPlayerDirection(Direction.getDirection(direction));
            }
            gameView.setPlayerState(LivingEntityState.RUNNING);
        }
    }

}
