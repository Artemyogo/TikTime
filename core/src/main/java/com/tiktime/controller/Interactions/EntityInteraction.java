package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.controller.ContactMasks;
import com.tiktime.controller.WorldController;
import com.tiktime.model.entities.Category;

public class EntityInteraction extends Interaction {
    WorldController worldController;
    public EntityInteraction(WorldController worldController) {
        super(Category.combine(Category.ENEMY, Category.PLAYER), Category.combine(Category.ENEMY, Category.PLAYER));
        this.worldController = worldController;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {

    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }
    @Override
    protected void onPreSolveContactInterval(Contact contact){
        contact.setEnabled(false);
        worldController.pushApart(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
    }

}
