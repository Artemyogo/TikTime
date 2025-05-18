package com.tiktime.controller.Interactions;

import com.tiktime.controller.ContactMasks;
import com.tiktime.model.entities.Category;

public abstract class Interaction {
    protected final Category catA;
    protected final Category catB;

    protected Interaction(Category catA, Category catB) {
        if (catA == null || catB == null) {
            throw new IllegalArgumentException("Interaction categories cannot be null");
        }
        this.catA = catA;
        this.catB = catB;
    }

    public final void beginContact(ContactMasks masks) {
        if (masks == null)
            throw new NullPointerException("ContactMasks cannot be null");
        if (!masks.check(this.catA, this.catB)) {
            return;
        }
        onBeginContactInternal(masks);
    }

    protected abstract void onBeginContactInternal(ContactMasks masks);
    void endContact(ContactMasks masks) {
        if (masks == null)
            throw new NullPointerException("ContactMasks cannot be null");
         if (!masks.check(this.catA, this.catB)) return;

    };

    protected abstract void onEndContactInternal(ContactMasks masks);

    void preSolve() {};
    void postSolve() {};
}
