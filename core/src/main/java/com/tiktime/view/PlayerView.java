package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.model.consts.GameConfig;

public class PlayerView extends LivingEntityView {
    public static final String atlasPath = "animations/player_1.atlas";
    private WeaponView weapon;

    public PlayerView(float x, float y, float width, float height, Direction direction, LivingEntityState state, WeaponType weapon) {
        super(x, y, width, height, direction, state, atlasPath);
        this.weapon = WeaponFactory.createWeapon(weapon, x, y);
        loadAnimations();
        updateAnimation();
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        weapon.render(delta, batch);
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
