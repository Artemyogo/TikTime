package com.tiktime.model.configs.configdata;

import com.sun.source.tree.BreakTree;

public class EntityData extends PhysicsData {
    protected int baseHp;
    protected float baseSpeed;
    protected float baseDamage;
    protected float baseAttackRange;

    public int getBaseHp() {
        return baseHp;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public float getBaseDamage() {
        return baseDamage;
    }
    public float getBaseAttackRange() { return baseAttackRange; }
}
