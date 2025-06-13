package com.tiktime.common.configs.configdata;

public class EntityData extends PhysicsData {
    protected int baseHp;
    protected int baseDamage;
    protected float baseSpeed;
    protected float baseAttackRange;
    protected float baseAttackCooldown;

    public float getBaseAttackCooldown() {
        return baseAttackCooldown;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseDamage() {
        return baseDamage;
    }
    public float getBaseAttackRange() { return baseAttackRange; }
}
