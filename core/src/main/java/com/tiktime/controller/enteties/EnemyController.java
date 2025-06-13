package com.tiktime.controller.enteties;

import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.*;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.livingenteties.enemies.AnimanEnemyModel;
import com.tiktime.model.entities.livingenteties.enemies.BossEnemyModel;
import com.tiktime.model.entities.livingenteties.enemies.EnemyModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.view.enteties.livingenteties.enemies.AllEnemyView;
import com.tiktime.view.world.WorldView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnemyController implements Pausable, EventListener, Disposable {
    private final Set<EnemyModel> enemies;
    private final Map<EnemyModel, Float> enemyTimeLeft = new HashMap<>();
    private final Map<EnemyModel, Float> curDamageTime = new HashMap<>();
    private final AllEnemyView allEnemyView;
    private final float deathTime = MagicConstants.ENEMY_DEATH_TIME;
    private final float baseDamageTime = MagicConstants.ENEMY_BASE_DAMAGE_TIME;

    public EnemyController(AllEnemyView allEnemyView, Set<EnemyModel> enemies) {
        this.allEnemyView = allEnemyView;
        this.enemies = enemies;

        for (EnemyModel e : enemies) {
            float width, height;
            float runFrameDuration;
            switch (e.getCategory()) {
                case ENEMY_ANIMAN: {
                    runFrameDuration = GameConfig.getEnemyConfig(Category.ENEMY_ANIMAN).runFrameDuration();
                    width = GameConfig.getEnemyConfig(Category.ENEMY_ANIMAN).getWidth();
                    height = GameConfig.getEnemyConfig(Category.ENEMY_ANIMAN).getHeight();
                    break;
                }
                case ENEMY_BOSS: {
                    runFrameDuration = GameConfig.getEnemyConfig(Category.ENEMY_BOSS).runFrameDuration();
                    width = GameConfig.getEnemyConfig(Category.ENEMY_BOSS).getWidth();
                    height = GameConfig.getEnemyConfig(Category.ENEMY_BOSS).getHeight();
                    break;
                }
                case ENEMY_RUSHER: {
                    runFrameDuration = GameConfig.getEnemyConfig(Category.ENEMY_RUSHER).runFrameDuration();
                    width = GameConfig.getEnemyConfig(Category.ENEMY_RUSHER).getWidth();
                    height = GameConfig.getEnemyConfig(Category.ENEMY_RUSHER).getHeight();
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + e.getCategory());
                }
            }

            curDamageTime.put(e, 0f);
            allEnemyView.addEnemy(e.getBody().getPosition().x, e.getBody().getPosition().y,
                width,
                height,
                e.getCurrentHealth(), e.getMaxHealth(),
                e.getId(),
                (Math.random() < 0.5 ? Direction.EAST : Direction.WEST),
                LivingEntityState.IDLE, EnemyType.RUSHER,
                runFrameDuration);
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
            allEnemyView.deleteEnemy(e.getId());
            enemies.remove(e);
            curDamageTime.remove(e);
        });

        enemies.forEach(e -> {
            if (!enemyTimeLeft.containsKey(e)) {
                allEnemyView.setEnemyCoordinates(e.getBody().getPosition().x,
                    e.getBody().getPosition().y,
                    e.getId());

                if (e.getDirection().x != 0) {
                    allEnemyView.setEnemyDirection(Direction.getDirection(e.getDirection()), e.getId());
                }

                if (e.isAttacking())
                    allEnemyView.setEnemyState(LivingEntityState.ATTACKING, e.getId());
                else if (e.isRunning())
                    allEnemyView.setEnemyState(LivingEntityState.RUNNING, e.getId());
                else
                    allEnemyView.setEnemyState(LivingEntityState.IDLE, e.getId());
            }

            curDamageTime.put(e, Math.max(0f, curDamageTime.get(e) - delta));
            allEnemyView.setEnemyIsAttacked(e.getId(), curDamageTime.get(e) != 0f);
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
                allEnemyView.setEnemyState(LivingEntityState.DYING, enemyModel.getId());
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
            default: {
                throw new RuntimeException("Invalid event data");
            }
        }
    }

    @Override
    public void dispose() {
        unsubscribeOnEvents();
    }

    @Override
    public void setPaused(boolean paused) {
        allEnemyView.getEnemyViews().forEach(enemyView -> enemyView.setPaused(paused));
    }
}
