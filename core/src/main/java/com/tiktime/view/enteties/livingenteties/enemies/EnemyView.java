package com.tiktime.view.enteties.livingenteties.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.Direction;
import com.tiktime.common.EnemyType;
import com.tiktime.common.LivingEntityState;
import com.tiktime.view.enteties.livingenteties.LivingEntityView;

public abstract class EnemyView extends LivingEntityView {
    private final int id;
    public EnemyView(float x, float y, float width, float height, int curHealth, int maxHealth,
                     int id, Direction direction, LivingEntityState livingEntityState,
                     EnemyType enemyType, String atlasPath, SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, direction, livingEntityState, atlasPath, batch);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
