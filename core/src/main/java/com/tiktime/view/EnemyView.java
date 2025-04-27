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
    private final int id;
    private final EnemyType enemyType;
    /// TODO ATLAS PATH
    public static String[] atlasPaths = {"1, 2, 3", "s2"};
    public EnemyView(float x, float y, float width, float height, int id, EnemyType enemyType) {
        /// TODO CALC ATLAS PATH WITH THE CONSTANCE WITH ENEMY TYPE
        super(x, y, width, height, atlasPaths[enemyType.getId()]);
        this.enemyType = enemyType;
        this.id = id;
    }

    public EnemyView(float x, float y, float width, float height, int id, Direction direction, EntityState entityState,
            EnemyType enemyType) {
        /// SAME
        super(x, y, width, height, direction, entityState, atlasPaths[enemyType.getId()]);
        this.enemyType = enemyType;
        this.id = id;
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
