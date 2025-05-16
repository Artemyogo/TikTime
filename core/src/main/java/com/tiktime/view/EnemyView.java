package com.tiktime.view;

public abstract class EnemyView extends LivingEntityView {
    private final int id;
    private final EnemyType enemyType;
    public EnemyView(float x, float y, float width, float height, int id, Direction direction, LivingEntityState livingEntityState,
                     EnemyType enemyType, String atlasPath) {
        super(x, y, width, height, direction, livingEntityState, atlasPath);
        this.enemyType = enemyType;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
