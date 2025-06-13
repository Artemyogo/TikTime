package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.common.MagicConstants;
import com.tiktime.controller.world.ContactMasks;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.HealthPotion;
import com.tiktime.model.entities.livingenteties.PlayerModel;

public class HealthPotionInteraction extends Interaction {
    public HealthPotionInteraction() {
        super(Category.PLAYER.getBits(), Category.HEALTH_POTION.getBits());
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        ContactMasks masks = new ContactMasks(contact);
        Fixture healthPotionFixture = masks.getFixture(Category.HEALTH_POTION);
        Fixture playerFixture = masks.getFixture(Category.PLAYER);
        HealthPotion healthPotion = (HealthPotion) healthPotionFixture.getBody().getUserData();
        PlayerModel playerModel = (PlayerModel) playerFixture.getBody().getUserData();

        healthPotion.onTouch(playerModel);
        healthPotion.deleteBody();
    }

    @Override
    protected void onEndContactInternal(Contact contact) {

    }

    @Override
    protected void onPreSolveContactInterval(Contact contact) {

    }
}
