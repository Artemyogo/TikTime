package com.tiktime.model.entities.livingenteties.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.common.MagicConstants;
import com.tiktime.model.BodyManager;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.livingenteties.WeaponableLivingEntityModel;
import com.tiktime.model.entities.raycasts.InPathRaycast;
import com.tiktime.model.entities.weapons.WeaponModel;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;

public abstract class EnemyModel extends WeaponableLivingEntityModel implements Categoriable {
    protected int reward;
    protected Category category;
    protected boolean isRunning = false;
    Vector2 movingDirection;

    public void updateDirection(){
        float angle = MathUtils.random(0f, MathUtils.PI2); // Random angle between 0-2π radians
        movingDirection = new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
    }

    public EnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, WeaponModel weaponModel,
                      int reward, Body body, BodyManager bodyManager)  {
        super(movementComponent, healthComponent, weaponModel, body, bodyManager);
        if (!Category.ENEMY.intercept(category.getBits())) {
            throw new IllegalArgumentException("Invalid category");
        }

        updateDirection();
        this.category = category;
        this.reward = reward;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    protected void chasePlayer(float delta, PlayerModel player, World world){
        InPathRaycast callback = new InPathRaycast(player.getBody().getUserData());
        world.rayCast(callback, body.getPosition(), player.getBody().getPosition());

        Vector2 playerPosition = new Vector2(player.getBody().getPosition()).sub(body.getPosition());
        isRunning = (callback.isInPath() || Math.abs(playerPosition.x) <= MagicConstants.ENEMY_VISION_X
            || Math.abs(playerPosition.y) <= MagicConstants.ENEMY_VISION_Y);
        if(callback.isInPath() && !weaponModel.isAttacking()){
            Vector2 vec = new Vector2(player.getPosition()).sub(getPosition()).nor().scl(delta);
            setDirectionAndMove(vec, delta);
        } else if (!weaponModel.isAttacking()) {
            setDirectionAndMove(movingDirection, delta);
            isRunning = true;
//            setDirectionAndMove(Vector2.Zero, delta);
        } else {
            setDirectionAndMove(Vector2.Zero, delta);
        }
    }

    public void update(float delta, PlayerModel player, World world){
        updateAttackCooldownTimer(delta);
        tryAttack(player.getBody().getPosition().x, player.getBody().getPosition().y);
        chasePlayer(delta, player, world);
    }

    public abstract boolean tryAttack(float playerX, float playerY);

    protected float getDist(Vector2 v1) {
        Vector2 v2 = new Vector2(body.getPosition());
        return Vector2.dst(v1.x, v1.y, v2.x, v2.y);
    }

    protected float getDist(float x, float y) {
        return getDist(new Vector2(x, y));
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

    public boolean isRunning() {
        return isRunning;
    }
}
