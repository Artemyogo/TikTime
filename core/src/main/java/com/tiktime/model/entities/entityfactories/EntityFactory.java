package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.GameConfig;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.AttackComponent;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.RusherEnemyModel;
import com.tiktime.model.entities.weapons.Ak47WeaponModel;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.view.enteties.weapons.WeaponType;

public class EntityFactory {
    public static PlayerModel createPlayerModel(World world, float x, float y) {
        GameConfig.PlayerConfig config = GameConfig.getInstance().getPlayerConfig();
        WeaponModel weaponModel = createAk47WeaponModel();
        GameConfig.PlayerConfig playerConfig = GameConfig.getInstance().getPlayerConfig();
        MovementComponent movementComponent = new MovementComponent(
            playerConfig.getBaseSpeed(),
            Vector2.Zero
        );
        HealthComponent healthComponent = new HealthComponent(
            playerConfig.getBaseHp(),
            playerConfig.getBaseHp()
        );

        return new PlayerModel(
            Category.PLAYER,
            weaponModel,
            movementComponent,
            healthComponent,
            BodyFactory.createPlayerBody(world, x, y),
            playerConfig.getWidth(),
            playerConfig.getHeight(),
            0
        );
    }

    public static RusherEnemyModel createRusherEnemyModel(World world, float x, float y) {
        GameConfig.EnemyConfig<?> rusherConfig = GameConfig.getInstance().getRusherEnemyConfig();
//        WeaponModel weaponModel = createAk47WeaponModel();
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
            BodyFactory.createRusherEnemyBody(world, x, y),
            rusherConfig.getWidth(),
            rusherConfig.getHeight()
        );
    }


    public static Ak47WeaponModel createAk47WeaponModel() {
        WeaponType weaponType = WeaponType.AK47;
        GameConfig.WeaponConfig<?> weaponConfig = GameConfig.getInstance().getWeaponConfig(weaponType);
        assert weaponConfig != null;
        AttackComponent attackComponent = new AttackComponent(
            weaponConfig.getDamage(),
            weaponConfig.getAttackCooldown()
        );

        return new Ak47WeaponModel(attackComponent, weaponType);
    }
}
