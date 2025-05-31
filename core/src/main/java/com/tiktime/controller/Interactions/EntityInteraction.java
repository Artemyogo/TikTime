package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.controller.world.PhysicsController;
import com.tiktime.model.entities.Category;

public class EntityInteraction extends Interaction {
    public EntityInteraction() {
        super(Category.combine(Category.ENEMY, Category.PLAYER), Category.combine(Category.ENEMY, Category.PLAYER));
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
        PhysicsController.pushApart(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
    }

}
