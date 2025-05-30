package com.tiktime.model.entities.entityfactories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.tiktime.model.GameConfig;

public class BodyDefFactory {
    public static BodyDef getBodyDef(float x, float y, BodyDef.BodyType bodyType, float width, float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x + width / 2f, y + height / 2f);
        return bodyDef;
    }

    public static BodyDef getBulletBodyDef(float x, float y){
        return getBodyDef(x,y,BodyDef.BodyType.StaticBody,
            GameConfig.getInstance().getBulletConfig().getWidth(),
            GameConfig.getInstance().getBulletConfig().getHeight()
        );
    }

    public static BodyDef getWallBodyDef(float x, float y){
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            GameConfig.getInstance().getWallConfig().getWidth(),
            GameConfig.getInstance().getWallConfig().getHeight()
            );
    }

    public static BodyDef getDoorBodyDef(float x, float y) {
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            GameConfig.getInstance().getFloorConfig().getWidth(),
            GameConfig.getInstance().getFloorConfig().getHeight()
            );
    }

    public static BodyDef getPlayerBodyDef(float x, float y){
        BodyDef bodyDef = getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getInstance().getPlayerConfig().getWidth(),
            GameConfig.getInstance().getPlayerConfig().getHeight());
        bodyDef.linearDamping = 4f;
        return bodyDef;
    }

    public static BodyDef getRusherEnemyBodyDef(float x, float y){
        BodyDef bodyDef = getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getInstance().getRusherEnemyConfig().getWidth(),
            GameConfig.getInstance().getRusherEnemyConfig().getHeight());
        bodyDef.linearDamping = 4f;
        return bodyDef;
    }
}
