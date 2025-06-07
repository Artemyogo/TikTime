package com.tiktime.model.entities.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.weapons.Attackable;

import java.awt.*;

public abstract class AttackComponent {
    protected final float attackCooldown;
    protected float attackCooldownTimer;
    protected boolean readyToAttack = true;
    protected float attackRange;
    protected int damage;
    protected World world;
    protected BodyManager bodyManager;

    public AttackComponent(int damage, float attackCooldown, float attackRange, World world, BodyManager bodyManager) {
        this.damage = damage;
        this.attackCooldownTimer = this.attackCooldown = attackCooldown;
        this.attackRange = attackRange;
        this.world = world;
        this.bodyManager = bodyManager;
    }

    public int getAttackDamage() {
        return damage;
    }

    public void setAttackDamage(int damage) {
        this.damage = damage;
    }

    public void updateAttackCooldownTimer(float delta) {
        attackCooldownTimer = Math.max(attackCooldownTimer - delta, 0);
        readyToAttack = (attackCooldownTimer == 0);
    }

    public abstract void doAttack(Vector2 position);

    public void doAttack() {
        if (!readyToAttack)
            throw new RuntimeException("is not readyToAttack");

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldownTimer == 0);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean tryAttack(Vector2 position) {
        if (readyToAttack) {
            doAttack(position);
            return true;
        }

        return false;
    }

    public float getAttackRange(){
        return attackRange;
    }
}
