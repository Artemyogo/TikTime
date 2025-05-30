package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.controller.ContactMasks;
import com.tiktime.controller.WorldController;
import com.tiktime.model.entities.Category;

public class DynamiteInteraction extends Interaction{
    WorldController worldController;
    public DynamiteInteraction(WorldController worldController) {
        super(Category.combine(Category.PLAYER, Category.ENEMY_RUSHER), Category.DYNAMITE.getBits());
        this.worldController = worldController;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        ContactMasks masks = new ContactMasks(contact);
        Fixture dynamiteFixture = masks.getFixture(Category.DYNAMITE);
        worldController.explosion(dynamiteFixture.getBody(), 10, 100f);
        worldController.deleteBody(dynamiteFixture.getBody());
    }

    @Override
    protected void onEndContactInternal(Contact masks) {
    }

    @Override
    protected void onPreSolveContactInterval(Contact masks) {

    }
}
