package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

import javax.swing.text.View;
import java.util.Comparator;

public class PlayerView extends EntityView {
    /// TODO ADD ATLAS PATH
    public static String atlasPath = "animations/player.atlas";
    public PlayerView(float x, float y, float width, float height) {
        super(x, y, width, height, atlasPath);
    }

    public PlayerView(float x, float y, float width, float height, Direction direction, EntityState state) {
        super(x, y, width, height, direction, state, atlasPath);
    }

    @Override
    protected void loadAnimations() {
        /// TODO
        /// MAYBE THERE CONSTANT runFrame should be
        float runFrameDuration  = 0.1f;

        ///  PATHPREFIX TO ATLAS
        animManager.add("player-running", loadAnimation("player-running-", runFrameDuration));
        /// BEWARE OF NAMES OF THE PAST, MB THEY WILL BE DIFFERENT
//        animManager.add("run_north", loadAnimation("run_n", runFrameDuration));
//        animManager.add("run_east", loadAnimation("run_e", runFrameDuration));
//        animManager.add("run_south", loadAnimation("run_s", runFrameDuration));
//        animManager.add("run_north_east", loadAnimation("run_ne", runFrameDuration));
//        animManager.add("run_south_east", loadAnimation("run_se", runFrameDuration));
//        animManager.add("dead", loadAnimation("dead", runFrameDuration));
    }
}
