package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.controller.ContactMasks;
import com.tiktime.controller.WorldController;
import com.tiktime.model.entities.Category;

public class DoorInteraction extends Interaction {
    WorldController worldController;
    public DoorInteraction(WorldController worldController) {
        super(Category.DOOR.getBits(), Category.PLAYER.getBits());
        this.worldController = worldController;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        worldController.onDoorEntry();
    }

    @Override
    protected void onEndContactInternal(Contact contact) {
        worldController.onDoorExit();
    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
