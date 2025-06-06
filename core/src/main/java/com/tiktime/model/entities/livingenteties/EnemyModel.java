package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.WeaponType;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.raycasts.InPathRaycast;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;

public abstract class EnemyModel extends WeaponableLivingEntityModel implements Categoriable {
    protected int reward;
    protected Category category;
    protected WeaponModel weaponModel;
    protected boolean isChasing = false;

    public EnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, WeaponModel weaponModel,
                      int reward, Body body)  {
        super(movementComponent, healthComponent, weaponModel, body, idNext++);
        if (!Category.ENEMY.intercept(category.getBits())) {
            throw new IllegalArgumentException("Invalid category");
        }

        this.category = category;
        this.reward = reward;
    }

    @Override
    public Category getCategory() {
        return category;
    }
    public void chasePlayer(float delta, PlayerModel player, World world){
        InPathRaycast callback = new InPathRaycast(player.getBody().getUserData());
        world.rayCast(callback, body.getPosition(), player.getBody().getPosition());
        isChasing = callback.isInPath();
        Gdx.app.log("EnemyModel",  "isChasing: " + isChasing);
        if(callback.isInPath()){
            Vector2 vec = new Vector2(player.getPosition()).sub(getPosition()).nor().scl(delta);
            setDirectionAndMove(vec, delta);
        } else {
            setDirectionAndMove(Vector2.Zero, delta);
        }
    }

    @Override
    public void applyDamage(int damage){
        super.applyDamage(damage);
        EventManager.fireEvent(new GameEvent(GameEventType.ENEMY_ATTACKED, this));
    }

    @Override
    public void death() {
        UpgradeModel.getInstance().addMoney(reward);
        EventManager.fireEvent(new GameEvent(GameEventType.ENEMY_DEATH, this));
    }

    public boolean isChasing() {
        return isChasing;
    }
}
