package com.tiktime.model.consts;

import com.badlogic.gdx.physics.box2d.BodyDef;

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
            GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.WALL).getWidth(),
            GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.WALL).getHeight()
            );
    }

    public static BodyDef getDoorBodyDef(float x, float y) {
        return getBodyDef(x, y, BodyDef.BodyType.StaticBody,
            GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.FLOOR).getWidth(),
            GameConfig.getInstance().getStaticObjectConfig(GameConfig.StaticObjectType.FLOOR).getHeight()
            );
    }

    public static BodyDef getPlayerBodyDef(float x, float y){
        return getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getInstance().getPlayerConfig().getWidth(),
            GameConfig.getInstance().getPlayerConfig().getHeight());
    }

    public static BodyDef getRusherEnemyBodyDef(float x, float y){
        return getBodyDef(x, y, BodyDef.BodyType.DynamicBody,
            GameConfig.getInstance().getRusherEnemyConfig().getWidth(),
            GameConfig.getInstance().getRusherEnemyConfig().getHeight());
    }
}
