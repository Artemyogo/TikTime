package com.tiktime.controller.Interactions;

import com.tiktime.controller.ContactMasks;
import com.tiktime.model.enums.Category;

public class DynamiteInteraction extends Interaction{
    protected DynamiteInteraction(Category catA, Category catB) {
        super(catA, catB);
    }

    @Override
    protected void onBeginContactInternal(ContactMasks masks) {

    }

    @Override
    protected void onEndContactInternal(ContactMasks masks) {

    }
}
