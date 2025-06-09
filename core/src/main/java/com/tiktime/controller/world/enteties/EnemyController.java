package com.tiktime.controller.world.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.entities.livingenteties.AnimanEnemyModel;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.common.EnemyType;
import com.tiktime.view.world.WorldView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnemyController implements EventListener, Disposable {
    private final Set<EnemyModel> enemies;
    private final Map<EnemyModel, Float> enemyTimeLeft = new HashMap<>();
    private final Map<EnemyModel, Float> curDamageTime = new HashMap<>();
    private final WorldView worldView;
    // TODO: magic constants
    private final float deathTime = 0.5f;
    private final float baseDamageTime = 0.1f;

    public EnemyController(WorldView worldView, Set<EnemyModel> enemies) {
        this.worldView = worldView;
        this.enemies = enemies;
//        Gdx.app.log("EnemyController", enemies.toString());

        for (EnemyModel e : enemies) {
            curDamageTime.put(e, 0f);
            worldView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                GameConfig.getRusherEnemyConfig().getWidth(),
                GameConfig.getRusherEnemyConfig().getHeight(),
                e.getCurrentHealth(), e.getMaxHealth(),
                e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER,
                (e instanceof AnimanEnemyModel ? 0.04f : 0.1f));
        }
        subscribeOnEvents();
    }

    private void subscribeOnEvents() {
        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
        EventManager.subscribe(GameEventType.ENEMY_ATTACKED, this);
    }

    private void unsubscribeOnEvents() {
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
        EventManager.unsubscribe(GameEventType.ENEMY_ATTACKED, this);
    }

    public void update(float delta) {
        HashSet<EnemyModel> readyToDie = new HashSet<>();

        enemyTimeLeft.keySet().forEach(e -> {
            float timeLeft = enemyTimeLeft.get(e) - delta;
            if (timeLeft <= 0) {
                readyToDie.add(e);
            }

            enemyTimeLeft.put(e, timeLeft);
        });

        readyToDie.forEach(e -> {
            worldView.deleteEnemy(e.getId());
            enemies.remove(e);
            curDamageTime.remove(e);
        });

        enemies.forEach(e -> {
            if (!enemyTimeLeft.containsKey(e)) {
                worldView.setEnemyCoordinates(e.getBody().getPosition().x,
                    e.getBody().getPosition().y,
                    e.getId());

                if (e.getDirection().x != 0) {
                    worldView.setEnemyDirection(Direction.getDirection(e.getDirection()), e.getId());
                }

                if (e.isAttacking())
                    worldView.setEnemyState(LivingEntityState.ATTACKING, e.getId());
                else if (e.isRunning())
                    worldView.setEnemyState(LivingEntityState.RUNNING, e.getId());
                else
                    worldView.setEnemyState(LivingEntityState.IDLE, e.getId());
            }

            curDamageTime.put(e, Math.max(0f, curDamageTime.get(e) - delta));
            worldView.setEnemyIsAttacked(e.getId(), curDamageTime.get(e) != 0f);
        });

        readyToDie.forEach(enemyTimeLeft::remove);
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            case ENEMY_DEATH: {
                if (!(event.data instanceof EnemyModel)) {
                    throw new RuntimeException("Invalid event data");
                }

                EnemyModel enemyModel = (EnemyModel) event.data;

                worldView.setEnemyState(LivingEntityState.DYING, enemyModel.getId());
                enemyTimeLeft.put(enemyModel, deathTime);
                enemyModel.deleteBody();
                break;
            }
            case ENEMY_ATTACKED: {
                if (!(event.data instanceof EnemyModel)) {
                    throw new RuntimeException("Invalid event data");
                }

                EnemyModel enemyModel = (EnemyModel) event.data;
                curDamageTime.put(enemyModel, baseDamageTime);
                break;
            }

            default: throw new RuntimeException("Invalid event data");
        }
    }

    @Override
    public void dispose() {
        unsubscribeOnEvents();
    }
}
