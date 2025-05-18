package com.tiktime.model.entities.components;

public class AttackComponent {
    private int damage;
    private final float attackCooldown;
    private float attackCooldownTimer;
    private boolean readyToAttack = true;

    public AttackComponent(int damage, float attackCooldown) {
        this.damage = damage;
        this.attackCooldownTimer = this.attackCooldown = attackCooldown;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void updateAttackCycle(float delta) {
        attackCooldownTimer = Math.max(attackCooldownTimer - delta, 0);
        readyToAttack = (attackCooldown == 0);
    }

    public void doAttack() {
        if (!readyToAttack)
            throw new RuntimeException("is not readyToAttack");

        attackCooldownTimer = attackCooldown;
        readyToAttack = (attackCooldown == 0);
    }

    public boolean tryAttack(float delta) {
        updateAttackCycle(delta);
        if (readyToAttack) {
            doAttack();
            return true;
        }

        return false;
    }
}
