package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.livingenteties.Damagable;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.Attackable;

public class MeleeAttackInteraction extends Interaction {
    protected MeleeAttackInteraction() {
        super(Category.combine(Category.PLAYER), Category.combine(Category.ENEMY_ATTACK));
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        Damagable damagable;
        Attackable attackable;

        if (contact.getFixtureA().getUserData() instanceof PlayerModel && contact.getFixtureB().getUserData() instanceof PlayerModel) {
            throw new RuntimeException("Cannot two PlayerModels");
        }

        if (contact.getFixtureA().getUserData() instanceof PlayerModel) {
            damagable = (Damagable) contact.getFixtureA().getUserData();
            if (!(contact.getFixtureB().getUserData() instanceof AttackComponent)) {
                throw new RuntimeException("Must be AttackComponent");
            }
            attackable = (Attackable) contact.getFixtureB().getUserData();
        } else if (contact.getFixtureB().getUserData() instanceof PlayerModel) {
            damagable = (Damagable) contact.getFixtureB().getUserData();
            if (!(contact.getFixtureA().getUserData() instanceof AttackComponent)) {
                throw new RuntimeException("Must be AttackComponent");
            }
            attackable = (Attackable) contact.getFixtureA().getUserData();
        } else {
            throw new RuntimeException("Cannot zero PlayerModels");
        }

        damagable.applyDamage(attackable.getAttackDamage());
    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {
        contact.setEnabled(false);
    }
}
