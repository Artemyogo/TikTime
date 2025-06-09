package com.tiktime.controller.Interactions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.tiktime.controller.world.ContactMasks;
import com.tiktime.controller.world.IExplosive;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.weapons.BulletModel;

public class DynamiteInteraction extends Interaction{
    IExplosive explosion;
    BodyManager bodyManager;
    public DynamiteInteraction(IExplosive explosion, BodyManager bodyManager) {
        super(Category.combine(Category.PLAYER, Category.ENEMY, Category.BULLET), Category.DYNAMITE.getBits());
        this.explosion = explosion;
        this.bodyManager = bodyManager;
    }

    @Override
    protected void onBeginContactInternal(Contact contact) {
        ContactMasks masks = new ContactMasks(contact);
        Fixture dynamiteFixture = masks.getFixture(Category.DYNAMITE);
        // TODO: magic constants
        explosion.explosion(dynamiteFixture.getBody(), 5f, 100f);
        // TODO: i think there should be dynamite model in which we should call method delete, and Body manager should be only in model part
        bodyManager.setToDelete(dynamiteFixture.getBody());
        if (Category.BULLET.is(contact.getFixtureA().getFilterData().categoryBits)) {
            BulletModel bulletModel = (BulletModel) contact.getFixtureA().getBody().getUserData();
            bulletModel.deleteBody();
        }
        if (Category.BULLET.is(contact.getFixtureB().getFilterData().categoryBits)) {
            BulletModel bulletModel = (BulletModel) contact.getFixtureB().getBody().getUserData();
            bulletModel.deleteBody();
        }
    }

    @Override
    protected void onEndContactInternal(Contact masks) {
    }

    @Override
    protected void onPreSolveContactInterval(Contact masks) {

    }
}
