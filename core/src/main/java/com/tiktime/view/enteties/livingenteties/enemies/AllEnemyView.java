package com.tiktime.view.enteties.livingenteties.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tiktime.common.Direction;
import com.tiktime.common.EnemyType;
import com.tiktime.common.LivingEntityState;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AllEnemyView {
    private final Map<Integer, EnemyView> enemyViews = new HashMap<>();
    private final SpriteBatch worldBatch;

    public AllEnemyView(SpriteBatch worldBatch) {
        this.worldBatch = worldBatch;
    }

    public void addEnemy(float x, float y, float width, float height, int curHealth, int maxHealth, int id, Direction direction,
                         LivingEntityState state, EnemyType enemyType, float speedFrameDuration, float attackFrameDuration) {
        switch (enemyType) {
            case RUSHER: {
                enemyViews.put(id, new RusherEnemyView(x, y, width, height, curHealth, maxHealth, id,
                    direction, state, worldBatch, speedFrameDuration, attackFrameDuration));
                break;
            }

            case ANIMAN:
            case MARKSMAN:
            default:
                throw new RuntimeException("Invalid enemyType");
        }
    }

    public void deleteEnemy(int id) {
        enemyViews.remove(id);
    }

    public void setEnemyCoordinates(float x, float y, int id) {
        EnemyView enemyView = enemyViews.get(id);
        if (enemyView == null) {
            Gdx.app.log("AllEnemyView", String.valueOf(id));
            enemyViews.values().forEach(e -> {
                Gdx.app.log("AllEnemyView", String.valueOf(e.getId()));
            });
        }
        enemyView.setPosition(x, y);
    }

    public void setEnemyState(LivingEntityState state, int id) {
        EnemyView enemyView = enemyViews.get(Integer.valueOf(id));
        enemyView.setState(state);
    }

    public void setEnemyDirection(Direction direction, int id) {
        EnemyView enemyView = enemyViews.get(id);
        enemyView.setDirection(direction);
    }

    public void setEnemyIsAttacked(int id, boolean isAttacked) {
        enemyViews.get(id).setIsAttacked(isAttacked);
    }

    public void clear() {
        enemyViews.clear();
    }

    public Collection<EnemyView> getEnemyViews() {
        return enemyViews.values();
    }
}
