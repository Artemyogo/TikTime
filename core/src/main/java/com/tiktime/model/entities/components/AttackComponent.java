package com.tiktime.model.entities.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.BodyManager;

import java.awt.*;

public abstract class AttackComponent {
    protected int damage;
    protected final float attackCooldown;
    protected float attackCooldownTimer;
    protected boolean readyToAttack = true;
    protected float attackRange;
    protected World world;
    protected BodyManager bodyManager;

    public AttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        this.damage = damage;
        this.attackCooldownTimer = this.attackCooldown = attackCooldown;
        this.attackRange = attackRange;
        this.world = world;
        this.bodyManager = bodyManager;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void updateAttackCycle(float delta) {
        attackCooldownTimer = Math.max(attackCooldownTimer - delta, 0);
        readyToAttack = (attackCooldownTimer == 0);
    }

    public void doAttack(float x, float y) {
        if (!readyToAttack)
            throw new RuntimeException("is not readyToAttack");

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldownTimer == 0);

    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean tryAttack(float x, float y) {
        if (readyToAttack) {
            doAttack(x, y);
            return true;
        }

        return false;
    }
    public float getAttackRange(){
        return attackRange;
    }
}
