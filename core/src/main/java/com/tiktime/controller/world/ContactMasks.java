package com.tiktime.controller.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.model.entities.Category;

public class ContactMasks {
    private final short A;
    private final short B;
    private final Fixture fixtureA;
    private final Fixture fixtureB;

    public ContactMasks(Contact contact){
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();
        A = fixtureA.getFilterData().categoryBits;
        B = fixtureB.getFilterData().categoryBits;
    }
    public Fixture getFixture(Category cat){
        if(cat.intercept(A))
            return fixtureA;
        else if(cat.intercept(B))
            return fixtureB;
        else
            throw new IllegalArgumentException("Category " + cat + " is not in this mask");
    }
    public boolean check(short catA, short catB){
        return ((catA&A) == A && (catB&B) == B) || ((catA&B) == B && (catB&A) == A);
    }

}
