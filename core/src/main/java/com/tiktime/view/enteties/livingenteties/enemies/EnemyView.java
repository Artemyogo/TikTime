package com.tiktime.view.enteties.livingenteties.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.livingenteties.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.LivingEntityView;

public abstract class EnemyView extends LivingEntityView {
    private final int id;
    private final EnemyType enemyType;
    public EnemyView(float x, float y, float width, float height, int curHealth, int maxHealth,
                     int id, Direction direction, LivingEntityState livingEntityState,
                     EnemyType enemyType, String atlasPath, SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, direction, livingEntityState, atlasPath, batch);
        this.enemyType = enemyType;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
