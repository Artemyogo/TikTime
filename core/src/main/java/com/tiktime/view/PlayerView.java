package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tiktime.model.consts.GameConfig;

public class PlayerView extends LivingEntityView {
    /// TODO ADD ATLAS PATH
    public static String atlasPath = "animations/player_1.atlas";
//    public static String atlasPath = "animations/player.atlas";

    public PlayerView(float x, float y, float width, float height, Direction direction, LivingEntityState state) {
        super(x, y, width, height, direction, state, atlasPath);
        loadAnimations();
        updateAnimation();
        GameConfig.Ak47WeaponConfig ak47WeaponConfig = GameConfig.getInstance().getAk47WeaponConfig();
    }

    @Override
    protected void loadAnimations() {
        /// TODO MAYBE THERE CONSTANT runFrame should be
        float frameDuration  = 0.1f;

        ///  PATHPREFIX TO ATLAS
        animManager.add("player-running", getAnimation("player-running", frameDuration, LivingEntityState.RUNNING.playMode));
        animManager.add("player-idle", getAnimation("player-idle", frameDuration, LivingEntityState.IDLE.playMode));
        /// BEWARE OF NAMES OF THE PAST, MB THEY WILL BE DIFFERENT
//        animManager.add("run_north", loadAnimation("run_n", runFrameDuration));
//        animManager.add("run_east", loadAnimation("run_e", runFrameDuration));
//        animManager.add("run_south", loadAnimation("run_s", runFrameDuration));
//        animManager.add("run_north_east", loadAnimation("run_ne", runFrameDuration));
//        animManager.add("run_south_east", loadAnimation("run_se", runFrameDuration));
//        animManager.add("dead", loadAnimation("dead", runFrameDuration));
    }

    @Override
    protected void updateAnimation() {
        /// TODO BEWARE animName should be translated to the correct one
        String animName = String.format("%s-%s",
            "player",
            state.name().toLowerCase());
        animManager.set(animName);
    }
}
