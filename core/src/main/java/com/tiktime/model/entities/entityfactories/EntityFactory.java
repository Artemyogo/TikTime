package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.configs.GameConfig;
import com.tiktime.model.configs.configdata.PlayerData;
import com.tiktime.model.configs.configdata.RusherEnemyData;
import com.tiktime.model.configs.configdata.WeaponData;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.RusherEnemyModel;
import com.tiktime.model.entities.weapons.Ak47WeaponModel;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.common.WeaponType;

public class EntityFactory {
    public static PlayerModel createPlayerModel(World world, float x, float y) {
        WeaponModel weaponModel = createAk47WeaponModel();
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
            0
        );
    }

    public static PlayerModel createPlayerModelAtNextMap(World world, float x, float y, PlayerModel playerModel) {
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
            0
        );
    }

    public static RusherEnemyModel createRusherEnemyModel(World world, float x, float y) {
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
            rusherConfig.getReward(),
            BodyFactory.createRusherEnemyBody(world, x, y)
        );
    }

    public static Ak47WeaponModel createAk47WeaponModel() {
        WeaponType weaponType = WeaponType.AK47;
        WeaponData weaponConfig = GameConfig.getWeaponConfig(weaponType);
        assert weaponConfig != null;
        AttackComponent attackComponent = new AttackComponent(
            weaponConfig.getDamage(),
            weaponConfig.getAttackCooldown()
        );

        return new Ak47WeaponModel(attackComponent, weaponType);
    }
}
