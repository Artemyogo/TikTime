package com.tiktime.controller.world;

import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.controller.Interactions.Interaction;

import java.util.ArrayList;
import java.util.List;

public class CollisionController implements ContactListener {
    private final WorldController worldController;
    private final List<Interaction> interactions = new ArrayList<>();

    public CollisionController addInteraction(Interaction interaction) {
        interactions.add(interaction);
        return this;
    }

    public CollisionController(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public void beginContact(Contact contact) {
        for(Interaction interaction : interactions)
            interaction.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        for(Interaction interaction : interactions)
            interaction.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        for(Interaction interaction : interactions)
            interaction.preSolve(contact);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
