package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.Direction;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.weapons.WeaponModel;

public abstract class WeaponableLivingEntityModel extends LivingEntityModel implements Weaponable {
    protected WeaponModel weaponModel;
    public WeaponableLivingEntityModel(MovementComponent movementComponent, HealthComponent healthComponent, WeaponModel weaponModel,
                                       Body body, BodyManager bodyManager, int id) {
        super(movementComponent, healthComponent, body, bodyManager, id);
        this.weaponModel = weaponModel;
    }

    @Override
    public WeaponModel getWeaponModel(){
        return weaponModel;
    }

    @Override
    public int getAttackDamage() {
        return weaponModel.getAttackDamage();
    }

    @Override
    public void setAttackDamage(int damage) {
        weaponModel.setAttackDamage(damage);
    }

    @Override
    public void updateAttackCooldownTimer(float delta) {
        weaponModel.updateAttackCooldownTimer(delta);
    }

    @Override
    public float getAttackRange() {
        return weaponModel.getAttackRange();
    }

    @Override
    public boolean isAttacking() {
        return weaponModel.isAttacking();
    }

    @Override
    public void setWeaponModel(WeaponModel weaponModel){
        this.weaponModel = weaponModel;
    }

    public void setWorld(World world){
        weaponModel.setWorld(world);
    }
}
