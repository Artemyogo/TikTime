package com.tiktime.view;

public class EnemyView extends LivingEntityView {
    private final int id;
    private final EnemyType enemyType;
    /// TODO ATLAS PATH
    public static String[] atlasPaths = {"1, 2, 3", "s2"};
    public EnemyView(float x, float y, float width, float height, int id, Direction direction, LivingEntityState livingEntityState,
                     EnemyType enemyType) {
        /// TODO CALC ATLAS PATH WITH THE CONSTANCE WITH ENEMY TYPE
        /// SAME
        super(x, y, width, height, direction, livingEntityState, atlasPaths[enemyType.getId()]);
        this.enemyType = enemyType;
        this.id = id;
    }

    @Override
    protected void loadAnimations() {
        /// TODO
        /// MAYBE THER CONSTANT runFrame should be
        float runFrameDuration  = 0.1f;

        /// BEWARE OF NAMES OF THE PAST, MB THEY WILL BE DIFFERENT
//        animManager.add("run_north", getAnimation("run_n", runFrameDuration));
//        animManager.add("run_east", getAnimation("run_e", runFrameDuration));
//        animManager.add("run_south", getAnimation("run_s", runFrameDuration));
//        animManager.add("run_north_east", getAnimation("run_ne", runFrameDuration));
//        animManager.add("run_south_east", getAnimation("run_se", runFrameDuration));
//        animManager.add("dead", getAnimation("dead", runFrameDuration));
    }

    @Override
    protected void updateAnimation() {

    }
}
