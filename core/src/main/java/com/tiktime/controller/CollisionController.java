package com.tiktime.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.enums.Category;

import static java.util.Collections.swap;

public class CollisionController implements ContactListener {
    private final WorldController worldController;

    public CollisionController(WorldController worldController) {
        this.worldController = worldController;
    }

    private class Masks{
        private short A;
        private short B;
        public Masks(Contact contact){
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();
            A = fixtureA.getFilterData().categoryBits;
            B = fixtureB.getFilterData().categoryBits;
        }
        public boolean check(Category catA, Category catB){
            return (catA.is(A) && catB.is(B)) || (catA.is(B) && catB.is(A));
        }
    }



    @Override
    public void beginContact(Contact contact) {
        Masks masks = new Masks(contact);
        if(masks.check(Category.PLAYER, Category.DOOR)) {
            worldController.onDoorEntry();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Masks masks = new Masks(contact);
        if(masks.check(Category.PLAYER, Category.DOOR)) {
            worldController.onDoorExit();
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
