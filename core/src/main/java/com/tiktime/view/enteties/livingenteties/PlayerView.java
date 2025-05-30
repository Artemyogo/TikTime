package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.view.enteties.weapons.WeaponFactory;
import com.tiktime.common.WeaponType;
import com.tiktime.view.enteties.weapons.WeaponView;

public class PlayerView extends LivingEntityView {
    public static final String atlasPath = "animations/player_1.atlas";
    private WeaponView weapon;
    private int coins;

    public PlayerView(float x, float y, float width, float height, int curHealth, int maxHealth, int coins,
                      Direction direction, LivingEntityState state, WeaponType weapon,
                      SpriteBatch batch) {
        super(x, y, width, height, curHealth, maxHealth, direction, state, atlasPath, batch);
        this.weapon = WeaponFactory.createWeapon(weapon, x, y, batch);
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

    public void updateWeaponRotationDeg(float rotationDeg) {
        if (pause)
            return;

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
