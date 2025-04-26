package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Comparator;

public class EnemyView extends EntityView {
    private final EnemyType enemyType;
    public EnemyView(float x, float y, float width, float height, String atlasPath, EnemyType enemyType) {
        super(x, y, width, height, atlasPath);
        this.enemyType = enemyType;
    }

    public EnemyView(float x, float y, float width, float height, Direction direction, EntityState entityState, String atlasPath,
            EnemyType enemyType) {
        super(x, y, width, height, direction, entityState, atlasPath);
        this.enemyType = enemyType;
    }

    @Override
    protected void loadAnimations() {
        /// TODO
        /// MAYBE THER CONSTANT runFrame should be
        float runFrameDuration  = 0.1f;

        /// BEWARE OF NAMES OF THE PAST, MB THEY WILL BE DIFFERENT
        animManager.add("run_north", loadAnimation("run_n", runFrameDuration));
        animManager.add("run_east", loadAnimation("run_e", runFrameDuration));
        animManager.add("run_south", loadAnimation("run_s", runFrameDuration));
        animManager.add("run_north_east", loadAnimation("run_ne", runFrameDuration));
        animManager.add("run_south_east", loadAnimation("run_se", runFrameDuration));
        animManager.add("dead", loadAnimation("dead", runFrameDuration));
    }
}
