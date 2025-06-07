package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.RusherEnemyData;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.*;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.RusherEnemyModel;
import com.tiktime.model.entities.weapons.Ak47WeaponModel;
import com.tiktime.model.entities.weapons.BulletModel;
import com.tiktime.model.entities.weapons.FistsWeaponModel;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.common.WeaponType;

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
            bodyManager,
            0
        );
    }

    public static PlayerModel createPlayerModelAtNextMap(World world, BodyManager bodyManager, float x, float y, PlayerModel playerModel) {
        WeaponModel weaponModel = playerModel.getWeaponModel();
        MovementComponent movementComponent = playerModel.getMovementComponent();
        HealthComponent healthComponent = playerModel.getHealthComponent();
        healthComponent.regenerateHealth(PlayerModel.CurrentStats.getRegenHealth());

        return new PlayerModel(
            Category.PLAYER,
            weaponModel,
            movementComponent,
            healthComponent,
            BodyFactory.createPlayerBody(world, x, y),
            bodyManager,
            0
        );
    }

    public static RusherEnemyModel createRusherEnemyModel(World world, BodyManager bodyManager, float x, float y) {
        RusherEnemyData rusherConfig = GameConfig.getRusherEnemyConfig();
        MovementComponent movementComponent = new MovementComponent(
            rusherConfig.getBaseSpeed(),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            rusherConfig.getBaseHp(),
            rusherConfig.getBaseHp()
        );

        return new RusherEnemyModel(
            Category.ENEMY_RUSHER,
            movementComponent,
            healthComponent,
            createFistsWeaponModel(world, bodyManager),
            rusherConfig.getReward(),
            BodyFactory.createRusherEnemyBody(world, x, y),
            bodyManager
        );
    }

    public static FistsWeaponModel createFistsWeaponModel(World world, BodyManager bodyManager) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.FISTS);
        assert weaponConfig != null;
        MeleeAttackComponent attackComponent = new MeleeAttackComponent(
            weaponConfig.getDamage(),
            weaponConfig.getAttackCooldown(),
            weaponConfig.getAttackRange(),
            world,
            bodyManager
        );

        return new FistsWeaponModel(attackComponent, weaponConfig.getOffsetAttackX(), weaponConfig.getOffsetAttackY());
    }
    public static Ak47WeaponModel createAk47WeaponModel(World world, BodyManager bodyManager) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(WeaponType.AK47);
        assert weaponConfig != null;
        GunshotAttackComponent attackComponent = new GunshotAttackComponent(
            weaponConfig.getDamage(),
            weaponConfig.getAttackCooldown(),
            weaponConfig.getAttackRange(),
            world,
            bodyManager
        );

        return new Ak47WeaponModel(attackComponent, weaponConfig.getOffsetAttackX(), weaponConfig.getOffsetAttackY());
    }
    public static BulletModel createBulletModel(World world, BodyManager bodyManager, float x, float y) {
        return new BulletModel(BodyFactory.createBulletBody(world, x, y), bodyManager);
    }
}
