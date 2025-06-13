package com.tiktime.model.entities.components;

import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.weapons.Attackable;

public abstract class AttackComponent implements Attackable {
    protected final float attackCooldown;
    protected float attackCooldownTimer;
    protected boolean readyToAttack = true;
    protected float attackRange;
    protected int damage;
    protected World world;
    protected BodyManager bodyManager;

    public AttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        this.damage = damage;
        this.attackCooldown = attackCooldown;
        this.attackCooldownTimer = 0;
        this.attackRange = attackRange;
        this.world = world;
        this.bodyManager = bodyManager;
    }

    @Override
    public int getAttackDamage() {
        return damage;
    }

    @Override
    public void setAttackDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void updateAttackCooldownTimer(float delta) {
        attackCooldownTimer = Math.max(attackCooldownTimer - delta, 0);
        readyToAttack = (attackCooldownTimer == 0);
    }

    @Override
    public float getAttackRange(){
        return attackRange;
    }

    @Override
    public boolean isAttacking() {
        return !readyToAttack;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean tryAttack() {
        if (!readyToAttack) {
            return false;
        }

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldownTimer == 0);
        return true;
    }
}
