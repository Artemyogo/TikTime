package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.common.MagicConstants;
import com.tiktime.view.enteties.weapons.WeaponFactory;
import com.tiktime.common.WeaponType;
import com.tiktime.view.enteties.weapons.WeaponView;

public class PlayerView extends LivingEntityView {
    // TODO magiiiic
    public static final String atlasPath = "animations/player_1.atlas";
    private WeaponView weaponView;
    private int coins;

    public PlayerView(float x, float y, float width, float height, int curHealth, int maxHealth, int coins,
                      Direction direction, LivingEntityState state, WeaponType weapon,
                      SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, direction, state, atlasPath, batch);
        this.weaponView = WeaponFactory.createWeapon(weapon, x, y, batch);
        this.coins = coins;
        loadAnimations();
        updateAnimation();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        weaponView.render(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        weaponView.setPosition(x, y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        weaponView.update(delta);
    }

    @Override
    public void setPaused(boolean paused) {
        super.setPaused(paused);
        weaponView.setPaused(paused);
    }

    public void updateWeaponRotationDeg(float rotationDeg) {
        if (pause)
            return;

        weaponView.setRotationDeg(rotationDeg);
    }

    public void setIsAttacking(boolean isAttacking) {
        weaponView.setIsAttacking(isAttacking);
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
