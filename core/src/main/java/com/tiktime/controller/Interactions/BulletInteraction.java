package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.livingenteties.Damagable;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.Attackable;
import com.tiktime.model.entities.weapons.BulletModel;

public class BulletInteraction extends Interaction {
    public BulletInteraction() {
        super(Category.combine(Category.BULLET), Category.combine(Category.OBSTACLE, Category.ENEMY));
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        // TODO: CAN BE ON TWO WALLS TOGETHER
        Category categoryA = Category.fromBits(contact.getFixtureA().getFilterData().categoryBits);
        Category categoryB = Category.fromBits(contact.getFixtureB().getFilterData().categoryBits);

        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();

        if (categoryA.intercept(Category.OBSTACLE.getBits())) {
            BulletModel bulletModel = (BulletModel) dataB;
            bulletModel.deleteBody();
            return;
        }

        if (categoryB.intercept(Category.OBSTACLE.getBits())) {
            BulletModel bulletModel = (BulletModel) dataA;
            bulletModel.deleteBody();
            return;
        }

        Damagable damagable = null;
        Attackable attackable = null;

        if (categoryA.intercept(Category.ENEMY.getBits())) {
            damagable = (Damagable) dataA;
            attackable = (Attackable) dataB;
            BulletModel bulletModel = (BulletModel) dataB;
            if (bulletModel.isDelete()) {
                return;
            }
            bulletModel.deleteBody();
        }

        if (categoryB.intercept(Category.ENEMY.getBits())) {
            damagable = (Damagable) dataB;
            attackable = (Attackable) dataA;
            BulletModel bulletModel = (BulletModel) dataA;
            if (bulletModel.isDelete()) {
                return;
            }
            bulletModel.deleteBody();
        }

        assert damagable != null;
        damagable.applyDamage(attackable.getAttackDamage());
    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
