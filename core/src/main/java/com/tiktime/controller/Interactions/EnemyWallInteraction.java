package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.controller.world.ContactMasks;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.livingenteties.enemies.EnemyModel;

public class EnemyWallInteraction extends Interaction {
    public EnemyWallInteraction() {
        super(Category.ENEMY.getBits(), Category.WALL.getBits());
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        ContactMasks masks = new ContactMasks(contact);
        Fixture enemyFixture = masks.getFixture(Category.ENEMY);
        EnemyModel enemyModel = (EnemyModel) enemyFixture.getBody().getUserData();
        enemyModel.updateDirection();
    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
