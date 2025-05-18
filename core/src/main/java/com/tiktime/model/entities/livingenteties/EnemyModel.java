package com.tiktime.model.entities.livingenteties;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.GameConfig;
import com.tiktime.model.entities.Categoriable;
import com.tiktime.model.entities.Category;
import com.tiktime.model.entities.EntityModel;
import com.tiktime.model.entities.components.HealthComponent;
import com.tiktime.model.entities.components.MovementComponent;
import com.tiktime.model.entities.raycasts.InPathRaycast;

public abstract class EnemyModel extends LivingEntityModel implements Categoriable {
    public static int idNext = 0;
    Category category;

    public EnemyModel(Category category, MovementComponent movementComponent, HealthComponent healthComponent, Body body,
                    float width, float height)  {
        super(movementComponent, healthComponent, body, width, height, idNext++);
        if (!Category.ENEMY.intercept(category.getBits())) {
            throw new IllegalArgumentException("Invalid category");
        }

        this.category = category;
    }

    @Override
    public Category getCategory() {
        return category;
    }
    public void chasePlayer(float delta, PlayerModel player, World world){
        InPathRaycast callback = new InPathRaycast(player.getBody().getUserData());
        world.rayCast(callback, getBody().getPosition(), player.getBody().getPosition());
        if(callback.isInPath()){
            Vector2 vec = new Vector2(player.getPosition()).sub(getPosition()).nor().scl(delta);
            setDirectionAndMove(vec, delta);
        }
    }
}
