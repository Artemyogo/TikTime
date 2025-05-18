package com.tiktime.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tiktime.model.WorldModel;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyType;
import com.tiktime.view.world.GameView;

public class EnemyController {
    private Array<EnemyModel> enemies;
    GameView gameView;
    public EnemyController(WorldModel worldModel, GameView gameView) {
        this.gameView = gameView;
        setEnemies(worldModel.getEnemies());
    }
    public void update(double delta) {
        enemies.forEach(e -> {
            gameView.setEnemyCoordinates(e.getBody().getPosition().x,
                e.getBody().getPosition().y,
                e.getId());
            if (!e.getDirection().equals(Vector2.Zero)) {
                gameView.setEnemyDirection(Direction.getDirection(e.getDirection()), e.getId());
            }
        });
    }
    public void setEnemies(Array<EnemyModel> enemies) {
        gameView.clearEnemies();
        this.enemies = enemies;
        for (EnemyModel e : enemies) {
            gameView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                e.getWidth(), e.getHeight(),
                e.getCurrentHealth(), e.getMaxHealth(),
                e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER);
        }
    }
}
