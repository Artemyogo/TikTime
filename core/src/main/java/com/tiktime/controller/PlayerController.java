package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.tiktime.model.WorldModel;
import com.tiktime.model.gameobjects.EntityData;
import com.tiktime.model.gameobjects.PlayerModel;
import com.tiktime.view.*;

public class PlayerController {
    PlayerModel player;
    GameView gameView;
    public PlayerController(PlayerModel playerModel, GameView gameView) {
        this.player = playerModel;
        this.gameView = gameView;
        EntityData entityData = playerModel.getData();
        gameView.setPlayer(
            player.getPosition().x,
            player.getPosition().y,
            entityData.getWidth(),
            entityData.getHeight(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );
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
        gameView.setPlayerCurHealth(player.getData().getCurrentHealth());
    }

}
