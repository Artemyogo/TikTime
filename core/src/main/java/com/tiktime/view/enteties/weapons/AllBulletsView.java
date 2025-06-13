package com.tiktime.view.enteties.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AllBulletsView {
    private final SpriteBatch worldBatch;
    public AllBulletsView(SpriteBatch worldBatch) {
        this.worldBatch = worldBatch;
    }

    private final Map<Integer, BulletView> bulletViews = new HashMap<>();
    public void addBullet(float x, float y, float width, float height, float rotationDeg, int id) {
        bulletViews.put(id, new BulletView(x, y, width, height, rotationDeg, worldBatch));
    }

    public void deleteBullet(int id) {
        bulletViews.remove(id);
    }

    public void setBulletPosition(float x, float y, int id) {
        bulletViews.get(id).setPosition(x, y);
    }

    public void clear() {
        bulletViews.clear();
    }

    public Collection<BulletView> getBulletViews() {
        return bulletViews.values();
    }
}
