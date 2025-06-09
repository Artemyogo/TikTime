package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.AnimanEnemyData;
import com.tiktime.common.configs.configdata.RusherEnemyData;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.*;
import com.tiktime.model.entities.livingenteties.AnimanEnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.RusherEnemyModel;
import com.tiktime.model.entities.weapons.*;
import com.tiktime.common.WeaponType;

import static com.badlogic.gdx.math.MathUtils.ceil;

public class EntityFactory {
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

        return new PlayerModel(
            Category.PLAYER,
            weaponModel,
            movementComponent,
            healthComponent,
            BodyFactory.createPlayerBody(world, x, y),
            bodyManager
        );
    }

    public static RusherEnemyModel createRusherEnemyModel(World world, BodyManager bodyManager, float x, float y, int level) {
        RusherEnemyData rusherConfig = GameConfig.getRusherEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            rusherConfig.getBaseSpeed(),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            rusherConfig.getBaseHp()*level,
            rusherConfig.getBaseHp()*level
        );

        return new RusherEnemyModel(
            Category.ENEMY_RUSHER,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager, rusherConfig.getBaseDamage()*level),
            ceil(rusherConfig.getReward()*level),
            BodyFactory.createRusherEnemyBody(world, x, y),
            bodyManager,
            rusherConfig.getWidth(),
            rusherConfig.getHeight()
        );
    }
    public static AnimanEnemyModel createAnimanEnemyModel(World world, BodyManager bodyManager, float x, float y, int level) {
        AnimanEnemyData animanConfig = GameConfig.getAnimanEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            animanConfig.getBaseSpeed(),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            animanConfig.getBaseHp()*level,
            animanConfig.getBaseHp()*level
        );
        AnimanEnemyModel res = new AnimanEnemyModel(
            Category.ENEMY_ANIMAN,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager, animanConfig.getBaseDamage()*level),
            animanConfig.getReward(),
            BodyFactory.createAnimanEnemyBody(world, x, y),
            bodyManager,
            animanConfig.getWidth(),
            animanConfig.getHeight()
        );
        return res;
    }

    public static FistsWeaponModel createFistsWeaponModel(World world, BodyManager bodyManager, float damageMultiplier) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.FISTS);
        assert weaponConfig != null;
        MeleeAttackComponent attackComponent = new MeleeAttackComponent(
            ceil(weaponConfig.getDamage()*damageMultiplier),
            weaponConfig.getAttackCooldown(),
            weaponConfig.getAttackRange(),
            world,
            bodyManager
        );

        return new FistsWeaponModel(attackComponent);
    }
    public static Ak47WeaponModel createAk47WeaponModel(World world, BodyManager bodyManager) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.AK47);
        assert weaponConfig != null;
        GunshotAttackComponent attackComponent = new GunshotAttackComponent(
            weaponConfig.getDamage(),
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
}
