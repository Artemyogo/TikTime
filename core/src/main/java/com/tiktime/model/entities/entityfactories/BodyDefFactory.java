package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.tiktime.model.configs.GameConfig;

public class BodyDefFactory {
    public static BodyDef getBodyDef(float x, float y, BodyDef.BodyType bodyType, float width, float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x + width / 2f, y + height / 2f);
        return bodyDef;
    }

    public static BodyDef getWallBodyDef(float x, float y){
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            GameConfig.getWallConfig().getWidth(),
            GameConfig.getWallConfig().getHeight()
            );
    }

    public static BodyDef getDoorBodyDef(float x, float y) {
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            GameConfig.getFloorConfig().getWidth(),
            GameConfig.getFloorConfig().getHeight()
            );
    }

    public static BodyDef getPlayerBodyDef(float x, float y){
        BodyDef bodyDef = getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getPlayerConfig().getWidth(),
            GameConfig.getPlayerConfig().getHeight());
        bodyDef.linearDamping = 4f;
        return bodyDef;
    }

    public static BodyDef getRusherEnemyBodyDef(float x, float y){
        BodyDef bodyDef = getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getRusherEnemyConfig().getWidth(),
            GameConfig.getRusherEnemyConfig().getHeight());
        bodyDef.linearDamping = 4f;
        return bodyDef;
    }

    public static BodyDef getFistAttackBodyDef(float x, float y, float width, float height){
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            width, height);
    }

    public static BodyDef getBulletBodyDef(float x, float y, float width, float height){
        BodyDef bodyDef = getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            width, height);
        bodyDef.bullet = true;
        return bodyDef;
    }
}
