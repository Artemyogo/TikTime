package com.tiktime.controller.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.tiktime.controller.world.ContactMasks;

public abstract class Interaction {
    protected final short catA;
    protected final short catB;

    protected Interaction(short catA, short catB) {
        this.catA = catA;
        this.catB = catB;
    }

    public final void beginContact(Contact contact) {
//        Gdx.app.log("Interaction", contact.getFixtureA().getFilterData().categoryBits + " " + contact.getFixtureB().getFilterData().categoryBits);
        if (contact == null)
            throw new NullPointerException("Contact cannot be null");
        if (!new ContactMasks(contact).check(this.catA, this.catB)) return;
        onBeginContactInternal(contact);
    }

    protected abstract void onBeginContactInternal(Contact contact);
    public void endContact(Contact contact) {
        if (contact == null)
            throw new NullPointerException("Contact cannot be null");
        if (!new ContactMasks(contact).check(this.catA, this.catB)) return;
        onEndContactInternal(contact);
    };

    protected abstract void onEndContactInternal(Contact contact);

    public final void preSolve(Contact contact) {
        if (contact == null)
            throw new NullPointerException("Contact cannot be null");
        if (!new ContactMasks(contact).check(this.catA, this.catB)) return;
        onPreSolveContactInterval(contact);
    };
    protected abstract void onPreSolveContactInterval(Contact contact);
    void postSolve() {};
}
