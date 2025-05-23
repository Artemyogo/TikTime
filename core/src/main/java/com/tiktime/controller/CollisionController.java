package com.tiktime.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.controller.Interactions.Interaction;
import com.tiktime.model.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CollisionController implements ContactListener {
    private final WorldController worldController;
    private final List<Interaction> interactions = new ArrayList<>();

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public CollisionController(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public void beginContact(Contact contact) {
        ContactMasks contactMasks = new ContactMasks(contact);
        if(contactMasks.check(Category.PLAYER, Category.DOOR)) {
            worldController.onDoorEntry();
        }
        if(contactMasks.check(Category.ENEMY_RUSHER, Category.DYNAMITE) || contactMasks.check(Category.PLAYER, Category.DYNAMITE)) {
            Fixture dynamiteFixture = contactMasks.getFixture(Category.DYNAMITE);
            worldController.explosion(dynamiteFixture.getBody(), 10, 100f);
            worldController.deleteBody(dynamiteFixture.getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
        ContactMasks contactMasks = new ContactMasks(contact);
        if(contactMasks.check(Category.PLAYER, Category.DOOR)) {
            worldController.onDoorExit();
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        ContactMasks contactMasks = new ContactMasks(contact);
        if(contactMasks.check(Category.ENEMY_RUSHER, Category.PLAYER) || contactMasks.check(Category.ENEMY_RUSHER, Category.ENEMY_RUSHER)){
            contact.setEnabled(false);
            worldController.pushApart(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
