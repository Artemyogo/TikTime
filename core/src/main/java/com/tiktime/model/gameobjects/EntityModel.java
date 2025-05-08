package com.tiktime.model.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tiktime.model.components.HealthComponent;
import com.tiktime.model.enums.Category;
import com.tiktime.model.enums.Fraction;

import com.tiktime.model.consts.GameConfig;
import com.tiktime.model.consts.GameConfig.EntityConfig;

public abstract class EntityModel {
    private final Body body;
    private final EntityData data;
    private final EntityConfig config;

    private FixtureDef getFixtureDef(float width, float height){
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        fixtureDef.shape = shape;

        fixtureDef.density = config.getDensity();
        fixtureDef.restitution = config.getRestitution();
        fixtureDef.friction = config.getFriction();

        fixtureDef.filter.categoryBits = data.category.getBit();
        fixtureDef.filter.maskBits = Category.combine(Category.BULLET, Category.WALL);
//        fixtureDef.filter.maskBits = Category.combine(Category.BULLET, Category.ENEMY, Category.WALL, Category.PLAYER);
        shape.dispose();
        return fixtureDef;
    }

    public EntityModel(World world, float x, float y,
                       EntityData data, EntityConfig config) {
        this.data = data;
        this.config = config;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        body.createFixture(getFixtureDef(data.getWidth(), data.getHeight()));
    }

    public EntityData getData(){
        return data;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

//    public void move(Vector2 direction){
//        EntityConfig entityConfig = GameConfig.getInstance().getEntityConfig();
//        ///  TODO THERE ALSO
////        Vector2 force = new Vector2(direction).scl(entityConfig.getAcceleration());
////        body.applyForceToCenter(force, true);
//    }

    public void move(Vector2 direction) {
        if (direction.x < -1 || direction.x > 1 || direction.y < -1 || direction.y > 1) {
            throw new IllegalArgumentException("Invalid move direction");
        }

        Vector2 velocity = new Vector2(direction);
        velocity.x *= data.getSpeed();
        velocity.y *= data.getSpeed();
        body.setLinearVelocity(velocity);
    }

    public Body getBody() {
        return body;
    }
}
