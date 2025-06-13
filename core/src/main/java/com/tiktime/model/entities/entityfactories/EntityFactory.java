package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.MagicConstants;
import com.tiktime.common.configs.configdata.*;
import com.tiktime.model.entities.HealthPotion;
import com.tiktime.model.world.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.DynamiteModel;
import com.tiktime.model.entities.components.*;
import com.tiktime.model.entities.livingenteties.*;
import com.tiktime.model.entities.livingenteties.enemies.AnimanEnemyModel;
import com.tiktime.model.entities.livingenteties.enemies.BossEnemyModel;
import com.tiktime.model.entities.livingenteties.enemies.EnemyModel;
import com.tiktime.model.entities.livingenteties.enemies.RusherEnemyModel;
import com.tiktime.model.entities.weapons.*;
import com.tiktime.common.WeaponType;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.ceil;

public class EntityFactory {
    private static final float mlt = MagicConstants.MLT;

    public static PlayerModel createPlayerModel(World world, BodyManager bodyManager, float x, float y) {
        WeaponModel weaponModel = createAk47WeaponModel(world, bodyManager);
        MovementComponent movementComponent = new MovementComponent(
                PlayerModel.CurrentStats.getSpeed(),
                Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
                PlayerModel.CurrentStats.getHealth(),
                PlayerModel.CurrentStats.getHealth()
        );

        return new PlayerModel(
            Category.PLAYER,
            weaponModel,
            movementComponent,
            healthComponent,
            BodyFactory.createPlayerBody(world, x, y),
            bodyManager
        );
    }

    public static PlayerModel createPlayerModelAtNextMap(World world, BodyManager bodyManager, float x, float y, PlayerModel playerModel) {
        // TODO: redo this
        WeaponModel weaponModel = createAk47WeaponModel(world, bodyManager);
        MovementComponent movementComponent = playerModel.getMovementComponent();
        HealthComponent healthComponent = playerModel.getHealthComponent();
        healthComponent.regenerateHealth(PlayerModel.CurrentStats.getRegenHealth());

        PlayerModel newPlayerModel = new PlayerModel(
            Category.PLAYER,
            weaponModel,
            movementComponent,
            healthComponent,
            BodyFactory.createPlayerBody(world, x, y),
            bodyManager
        );

        return newPlayerModel;
    }

    public static EnemyModel createEnemyModel(World world, BodyManager bodyManager, float x, float y, int level, Category enemy) {
        if (!Category.ENEMY.intercept(enemy.getBits())) throw new IllegalArgumentException("Unknown enemy");

        EnemyData enemyConfig = GameConfig.getEnemyConfig(enemy);
        MovementComponent movementComponent = new MovementComponent(
            enemyConfig.getBaseSpeed() * (1 + level * mlt),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            ceil(enemyConfig.getBaseHp() * (1 + level * mlt)),
            ceil(enemyConfig.getBaseHp() * (1 + level * mlt))
        );

        switch (enemy) {
            case ENEMY_RUSHER: return new RusherEnemyModel(
                enemy,
                movementComponent,
                healthComponent,
                createFistsWeaponModel(world, bodyManager, enemyConfig.getBaseDamage()*level, enemyConfig.getAttackRange()),
                ceil(enemyConfig.getReward() * (1 + level * mlt)),
                BodyFactory.createEnemyBody(world, x, y, enemy),
                bodyManager,
                enemyConfig.getWidth(),
                enemyConfig.getHeight()
            );

            case ENEMY_ANIMAN: return new AnimanEnemyModel(
                enemy,
                movementComponent,
                healthComponent,
                createFistsWeaponModel(world, bodyManager, enemyConfig.getBaseDamage()*level, enemyConfig.getAttackRange()),
                ceil(enemyConfig.getReward() * (1 + level * mlt)),
                BodyFactory.createEnemyBody(world, x, y, enemy),
                bodyManager,
                enemyConfig.getWidth(),
                enemyConfig.getHeight()
            );

            case ENEMY_BOSS: return new BossEnemyModel(
                enemy,
                movementComponent,
                healthComponent,
                createFistsWeaponModel(world, bodyManager, enemyConfig.getBaseDamage()*level, enemyConfig.getAttackRange()),
                ceil(enemyConfig.getReward() * (1 + level * mlt)),
                BodyFactory.createEnemyBody(world, x, y, enemy),
                bodyManager,
                enemyConfig.getWidth(),
                enemyConfig.getHeight()
            );

            default: throw new IllegalArgumentException("Unknown enemy");
        }
    }

    /*
    public static RusherEnemyModel createRusherEnemyModel(World world, BodyManager bodyManager, float x, float y, int level) {
        RusherEnemyData rusherConfig = GameConfig.getRusherEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            rusherConfig.getBaseSpeed()*(1 + level*mlt),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            ceil(rusherConfig.getBaseHp()*(1 + level*mlt)),
            ceil(rusherConfig.getBaseHp()*(1 + level*mlt))
        );

        return new RusherEnemyModel(
            Category.ENEMY_RUSHER,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager, rusherConfig.getBaseDamage()*level, rusherConfig.getAttackRange()),
            ceil(rusherConfig.getReward()*(1 + level*mlt)),
            BodyFactory.createRusherEnemyBody(world, x, y),
            bodyManager,
            rusherConfig.getWidth(),
            rusherConfig.getHeight()
        );
    }
    public static AnimanEnemyModel createAnimanEnemyModel(World world, BodyManager bodyManager, float x, float y, int level) {
        AnimanEnemyData animanConfig = GameConfig.getAnimanEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            animanConfig.getBaseSpeed()*(1 + level*mlt),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            ceil(animanConfig.getBaseHp()*(1 + level*mlt)),
            ceil(animanConfig.getBaseHp()*(1 + level*mlt))
        );
        AnimanEnemyModel res = new AnimanEnemyModel(
            Category.ENEMY_ANIMAN,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager, animanConfig.getBaseDamage()*level, animanConfig.getAttackRange()),
            ceil(animanConfig.getReward()*(1 + level*mlt)),
            BodyFactory.createAnimanEnemyBody(world, x, y),
            bodyManager,
            animanConfig.getWidth(),
            animanConfig.getHeight()
        );
        return res;
    }

    public static BossEnemyModel createBossEnemyModel(World world, BodyManager bodyManager, float x, float y, int level) {
        BossEnemyData bossConfig = GameConfig.getBossEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            bossConfig.getBaseSpeed()*(1 + level*mlt),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            ceil(bossConfig.getBaseHp()*(1 + level*mlt)),
            ceil(bossConfig.getBaseHp()*(1 + level*mlt))
        );

        return new BossEnemyModel(
            Category.ENEMY_BOSS,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager, bossConfig.getBaseDamage()*level, bossConfig.getAttackRange()),
            ceil(bossConfig.getReward()*(1 + level*mlt)),
            BodyFactory.createBossEnemyBody(world, x, y),
            bodyManager,
            bossConfig.getWidth(),
            bossConfig.getHeight());
    }
    */

    public static FistsWeaponModel createFistsWeaponModel(World world, BodyManager bodyManager, float damage, float attackRange) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.FISTS);
        assert weaponConfig != null;
        MeleeAttackComponent attackComponent = new MeleeAttackComponent(
            ceil(damage),
            weaponConfig.getAttackCooldown(),
            attackRange,
            world,
            bodyManager
        );

        return new FistsWeaponModel(attackComponent);
    }
    public static Ak47WeaponModel createAk47WeaponModel(World world, BodyManager bodyManager) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.AK47);
        assert weaponConfig != null;
        GunshotAttackComponent attackComponent = new GunshotAttackComponent(
            PlayerModel.CurrentStats.getDamage(),
            weaponConfig.getAttackCooldown(),
            weaponConfig.getAttackRange(),
            0,
            world,
            bodyManager
        );

        return new Ak47WeaponModel(attackComponent);
    }
    public static BulletModel createBulletModel(World world, BodyManager bodyManager, Attackable attackable,
                                                float x, float y, float rotationDeg, Vector2 direction) {
        return new BulletModel(BodyFactory.createBulletBody(world, x, y, direction), bodyManager, attackable, rotationDeg);
    }
    public static Array<DynamiteModel> createDynamiteModels(World world, BodyManager bodyManager, TiledMapTileLayer dynamiteLayer) {
        ArrayList<Body> dynamiteBodies = BodyFactory.createBodiesOnLayer(world, dynamiteLayer, Category.DYNAMITE, BodyDef.BodyType.StaticBody);
        Array<DynamiteModel> res = new Array<>();
        dynamiteBodies.forEach(body -> res.add(new DynamiteModel(body, bodyManager, (TiledMapTileLayer.Cell) body.getUserData())));
        return res;
    }

    public static Array<HealthPotion> createHealthPotionModels(World world, BodyManager bodyManager, TiledMapTileLayer healthPotionLayer) {
        ArrayList<Body> healthPotionBodies = BodyFactory.createBodiesOnLayer(world, healthPotionLayer, Category.HEALTH_POTION, BodyDef.BodyType.StaticBody);
        Array<HealthPotion> res = new Array<>();
        healthPotionBodies.forEach(body -> res.add(new HealthPotion(body, bodyManager, (TiledMapTileLayer.Cell) body.getUserData())));
        return res;
    }
}
