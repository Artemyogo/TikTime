package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.model.world.DoorSensorModel;
import com.tiktime.model.entities.Category;

public class DoorInteraction extends Interaction {
    DoorSensorModel doorSensorModel;
    public DoorInteraction(DoorSensorModel doorSensorModel) {
        super(Category.DOOR.getBits(), Category.PLAYER.getBits());
        this.doorSensorModel = doorSensorModel;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        doorSensorModel.onDoorEntry();
    }

    @Override
    protected void onEndContactInternal(Contact contact) {
        doorSensorModel.onDoorExit();
    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
