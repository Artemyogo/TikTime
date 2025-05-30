package com.tiktime.controller;

import com.badlogic.gdx.physics.box2d.Body;

public interface IExplosive {
    void explosion(Body body, float radius, float force);

}
