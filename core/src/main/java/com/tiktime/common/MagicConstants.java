package com.tiktime.common;

import com.tiktime.view.world.WorldView;

public final class MagicConstants {
    private MagicConstants() {}
    public static final boolean DEBUG = false;
    public static final boolean DEBUG_WORLD_CONTROLLER = DEBUG || false;
    public static final boolean DEBUG_WORLD_VIEW = DEBUG || false;


    public static final String UPGRADES_PREFERENCES_NAME = "com.tiktime.upgrades";
    public static final String SETTINGS_PREFERENCES_NAME = "com.tiktime.settings";

    public static final float BODY_WIDTH = 1f;
    public static final float BODY_HEIGHT = 1f;

    public static final float ENEMY_VISION_X = 0.6f;
    public static final float ENEMY_VISION_Y = 0.6f;

    public static final float AK47_ATTACK_FRAME_DURATION = 0.04f;
    public static final float AK47_IDLE_FRAME_DURATION = 0.1f;
    public static final float RUSHER_ATTACK_FRAME_DURATION = 0.1f;
    public static final float RUSHER_DEATH_FRAME_DURATION = 0.1f;

    public static final float ENEMY_DEATH_TIME = 0.5f;
    public static final float ENEMY_BASE_DAMAGE_TIME = 0.1f;

    public static final float PLAYER_BASE_DAMAGE_TIME = 0.1f;
    public static final float PLAYER_DAMAGE_TIME_LEFT = 0f;

    public static final float EXPLOSION_RADIUS = 5f;
    public static final float EXPLOSION_FORCE = 100f;

    public static final float TIME_STEP = 1 / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final String AK47_ATLAS_PATH = "animations/ak47weapon.atlas";
    public static final String RUSHER_ATLAS_PATH = "animations/rusher.atlas";

    public static final String AK47_ATTACK_ANIMATION_NAME = "ak47-attacking";
    public static final String AK47_IDLE_ANIMATION_NAME = "ak47-idle";
    public static final String RUSHER_RUNNING_ANIMATION_NAME = "rusher-running";
    public static final String RUSHER_IDLE_ANIMATION_NAME = "rusher-idle";
    public static final String RUSHER_ATTACKING_ANIMATION_NAME = "rusher-attacking";
    public static final String RUSHER_DYING_ANIMATION_NAME = "rusher-dying";

    public static final String BULLET_TEXTURE_PATH = "bullet.png";

    public static final String OBJECTS_LAYER_NAME = "objects";
    public static final String ENEMIES_LAYER_NAME = "enemies";
    public static final String ANIMAN_ENEMIES_LAYER_NAME = "animanEnemies";
    public static final String BOSS_ENEMIES_LAYER_NAME = "bossEnemies";
    public static final String WALLS_LAYER_NAME = "walls";
    public static final String DOORS_LAYER_NAME = "doors";
    public static final String DYNAMITES_LAYER_NAME = "dynamite";

    public static final String PLAYER_SPAWN_NAME = "playerSpawn";

    public static final float MLT = 0.1f;
}
