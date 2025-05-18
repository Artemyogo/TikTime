package com.tiktime.model.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.enums.Category;
import com.tiktime.model.raycasts.InPathRaycast;

public abstract class EnemyModel extends EntityModel {
    public static int idNext = 0;
    private final int id;
    public EnemyModel(EntityData data, GameConfig.EntityConfig config, Body body)  {
        super(data, config, body);
        id = idNext++;
    }

    public int getId() {
        return id;
    }
    public void chasePlayer(float delta, PlayerModel player, World world){
        InPathRaycast callback = new InPathRaycast(player.getBody().getUserData());
        world.rayCast(callback, getBody().getPosition(), player.getBody().getPosition());
        if(callback.isInPath()){
            Vector2 vec = new Vector2(player.getPosition()).sub(getPosition()).nor().scl(delta);
            move(vec, delta);
        }
    }
}
