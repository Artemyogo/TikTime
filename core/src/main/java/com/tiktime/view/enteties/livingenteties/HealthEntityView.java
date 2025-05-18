package com.tiktime.view.enteties.livingenteties;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class HealthEntityView extends AnimatedEntityView {
    protected int curHealth;
    protected int maxHealth;

    public HealthEntityView(float x, float y, float width, float height, int curHealth, int maxHealth,
                            String atlasPath, SpriteBatch batch) {
        super(x, y, width, height, atlasPath, batch);
        this.curHealth = curHealth;
        this.maxHealth = maxHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
