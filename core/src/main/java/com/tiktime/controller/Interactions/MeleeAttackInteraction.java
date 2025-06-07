package com.tiktime.controller.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.livingenteties.Damagable;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.Attackable;

public class MeleeAttackInteraction extends Interaction {
    public MeleeAttackInteraction() {
        super(Category.combine(Category.PLAYER), Category.combine(Category.ENEMY_ATTACK));
        Gdx.app.log("MeleeAt", "I MADE");
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        Damagable damagable;
        Attackable attackable;

        Gdx.app.log("Interactions", "AAAAAAAAAAA");

        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();

        if (dataA instanceof PlayerModel && dataB instanceof PlayerModel) {
            throw new RuntimeException("Cannot two PlayerModels");
        }

        if (dataA instanceof PlayerModel) {
            damagable = (Damagable) dataA;
            if (!(dataB instanceof AttackComponent)) {
                throw new RuntimeException("Must be AttackComponent");
            }
            attackable = (Attackable) dataB;
        } else if (dataB instanceof PlayerModel) {
            damagable = (Damagable) dataB;
            if (!(dataA instanceof AttackComponent)) {
                throw new RuntimeException("Must be AttackComponent");
            }
            attackable = (Attackable) dataA;
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
