package com.tiktime.common.configs.configdata;

public class EnemyData extends EntityData {
    protected int reward;
    private float runFrameDuration;
    private float attackRange;
    public int getReward() {
        return reward;
    }
    public float runFrameDuration() {
        return runFrameDuration;
    }
    public float getAttackRange() {
        return attackRange;
    }

}
