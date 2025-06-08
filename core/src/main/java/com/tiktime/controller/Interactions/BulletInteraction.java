package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.livingenteties.Damagable;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.Attackable;

public class BulletInteraction extends Interaction {
    private BodyManager bodyManager;
    public BulletInteraction(BodyManager bodyManager) {
        super(Category.combine(Category.BULLET), Category.combine(Category.OBSTACLE, Category.ENEMY));
        this.bodyManager = bodyManager;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        // TODO: CAN BE ON TWO WALLS TOGETHER
        Category categoryA = Category.fromBits(contact.getFixtureA().getFilterData().categoryBits);
        Category categoryB = Category.fromBits(contact.getFixtureB().getFilterData().categoryBits);

        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();

        if (categoryA.intercept(Category.OBSTACLE.getBits())) {
            bodyManager.setToDelete(contact.getFixtureB().getBody());
            return;
        }

        if (categoryB.intercept(Category.OBSTACLE.getBits())) {
            bodyManager.setToDelete(contact.getFixtureA().getBody());
            return;
        }

        Damagable damagable = null;
        Attackable attackable = null;

        if (categoryA.intercept(Category.ENEMY.getBits())) {
            damagable = (Damagable) dataA;
            attackable = (Attackable) dataB;
            bodyManager.setToDelete(contact.getFixtureB().getBody());
        }

        if (categoryB.intercept(Category.ENEMY.getBits())) {
            damagable = (Damagable) dataB;
            attackable = (Attackable) dataA;
            bodyManager.setToDelete(contact.getFixtureA().getBody());
        }

        damagable.applyDamage(attackable.getAttackDamage());
    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
