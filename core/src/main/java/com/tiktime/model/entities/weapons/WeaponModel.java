package com.tiktime.model.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.WeaponType;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.components.AttackComponent;

public abstract class WeaponModel {
    protected final AttackComponent attackComponent;
    protected final WeaponType weaponType;
    protected float offsetAttackX;
    protected float offsetAttackY;

    public WeaponModel(AttackComponent attackComponent, WeaponType weaponType,
                       float offsetAttackX, float offsetAttackY) {
        this.attackComponent = attackComponent;
        this.weaponType = weaponType;
        this.offsetAttackX = offsetAttackX;
        this.offsetAttackY = offsetAttackY;
    }

    public int getAttackDamage() {
        return attackComponent.getAttackDamage();
    }

    public void setAttackDamage(int damage) {
        attackComponent.setAttackDamage(damage);
    }

    public void updateAttackCooldownTimer(float delta) {
        attackComponent.updateAttackCooldownTimer(delta);
    }

    public void doAttack(Vector2 playerPosition) {
        attackComponent.doAttack(playerPosition);
    }

    public boolean tryAttack(Vector2 playerPosition) {
        return attackComponent.tryAttack(playerPosition);
    }

    public void update(float delta) {
        attackComponent.updateAttackCooldownTimer(delta);
    }

    public void setWorld(World world) {
        attackComponent.setWorld(world);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getRange() {
        return attackComponent.getAttackRange();
    }
}
