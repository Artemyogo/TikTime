package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.controller.ContactMasks;
import com.tiktime.controller.IExplosive;
import com.tiktime.controller.WorldController;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;

public class DynamiteInteraction extends Interaction{
    IExplosive explosion;
    BodyManager bodyManager;
    public DynamiteInteraction(IExplosive explosion, BodyManager bodyManager) {
        super(Category.combine(Category.PLAYER, Category.ENEMY_RUSHER), Category.DYNAMITE.getBits());
        this.explosion = explosion;
        this.bodyManager = bodyManager;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        ContactMasks masks = new ContactMasks(contact);
        Fixture dynamiteFixture = masks.getFixture(Category.DYNAMITE);
        explosion.explosion(dynamiteFixture.getBody(), 10, 100f);
        bodyManager.setToDelete(dynamiteFixture.getBody());
    }

    @Override
    protected void onEndContactInternal(Contact masks) {
    }

    @Override
    protected void onPreSolveContactInterval(Contact masks) {

    }
}
