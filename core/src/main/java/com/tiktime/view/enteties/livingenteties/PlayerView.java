package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.view.enteties.Direction;
import com.tiktime.view.enteties.weapons.WeaponFactory;
import com.tiktime.view.enteties.weapons.WeaponType;
import com.tiktime.view.enteties.weapons.WeaponView;

public class PlayerView extends LivingEntityView {
    public static final String atlasPath = "animations/player_1.atlas";
    private WeaponView weapon;

    public PlayerView(float x, float y, float width, float height, int curHealth, int maxHealth, Direction direction, LivingEntityState state, WeaponType weapon,
                      SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, direction, state, atlasPath, batch);
        this.weapon = WeaponFactory.createWeapon(weapon, x, y, batch);
        loadAnimations();
        updateAnimation();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        weapon.render(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        weapon.setPosition(x, y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        weapon.update(delta);
    }

    public void setWeaponRotationDeg(float rotationDeg) {
        weapon.setRotationDeg(rotationDeg);
    }

    @Override
    protected void loadAnimations() {
        float frameDuration  = 0.1f;

        animManager.add("player-running", getAnimation("player-running", frameDuration, LivingEntityState.RUNNING.playMode));
        animManager.add("player-idle", getAnimation("player-idle", frameDuration, LivingEntityState.IDLE.playMode));
    }

    @Override
    protected void updateAnimation() {
        String animName = String.format("%s-%s", "player", state.name().toLowerCase());
        animManager.set(animName);
    }

}
