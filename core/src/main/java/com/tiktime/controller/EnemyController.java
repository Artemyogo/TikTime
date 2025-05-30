package com.tiktime.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.model.BodyManager;
import com.tiktime.model.WorldModel;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.enemies.EnemyType;
import com.tiktime.view.world.GameView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnemyController implements EventListener, Disposable {
    private Set<EnemyModel> enemies;
    private Set<EnemyModel> enemiesToDie = new HashSet<>();
    /// TODO: REDO THIS
    private final int deathTime = 100;
    private Map<EnemyModel, Integer> enemyTimeLeft = new HashMap<>();
    private WorldController worldController;
    private GameView gameView;
    private BodyManager bodyManager;

    public EnemyController(BodyManager bodyManager, GameView gameView) {
        this.gameView = gameView;
        this.bodyManager = bodyManager;
        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
    }

    public void update(double delta) {
        HashSet<EnemyModel> readyToDie = new HashSet<>();

        enemiesToDie.forEach(e -> {
            int timeLeft = (int) (enemyTimeLeft.get(e) - delta);
            if (timeLeft <= 0) {
                readyToDie.add(e);
            }

            enemyTimeLeft.put(e, timeLeft);
        });

        readyToDie.forEach(e -> {
            gameView.deleteEnemy(e.getId());
            enemies.remove(e);
            enemyTimeLeft.remove(e);
        });

        enemies.forEach(e -> {
            if (!enemiesToDie.contains(e)) {
                gameView.setEnemyCoordinates(e.getBody().getPosition().x,
                    e.getBody().getPosition().y,
                    e.getId());
                if (!e.getDirection().equals(Vector2.Zero)) {
                    gameView.setEnemyDirection(Direction.getDirection(e.getDirection()), e.getId());
                }
            }
        });

        readyToDie.forEach(e -> {
            enemiesToDie.remove(e);
        });
    }

    public void setEnemies(Set<EnemyModel> enemies) {
        gameView.clearEnemies();
        this.enemies = enemies;

        for (EnemyModel e : enemies) {
            Gdx.app.log("setEnemiesMODEL", String.valueOf(e.getId()));
            gameView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                e.getWidth(), e.getHeight(),
                e.getCurrentHealth(), e.getMaxHealth(),
                e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER);
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.type == GameEventType.ENEMY_DEATH) {
            if (!(event.data instanceof EnemyModel)) {
                throw new RuntimeException("WHAAAAAAAAAAAAT?!?!");
            }

            EnemyModel enemyModel = (EnemyModel) event.data;

            gameView.setEnemyState(LivingEntityState.DYING, enemyModel.getId());
            enemiesToDie.add(enemyModel);
            enemyTimeLeft.put(enemyModel, deathTime);
            Gdx.app.log("HAHAHAH", String.valueOf(enemyModel.getId()));
            bodyManager.setToDelete(enemyModel.getBody());
        }
    }

    @Override
    public void dispose() {
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
    }
}
