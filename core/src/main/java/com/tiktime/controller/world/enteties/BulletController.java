package com.tiktime.controller.world.enteties;

import com.tiktime.model.entities.weapons.BulletModel;

import java.util.Set;

public class BulletController {
    private Set<BulletModel> bulletModels;

    BulletController(Set<BulletModel> bulletModels) {

    }

    public void setBulletModels(Set<BulletModel> bulletModels) {
        this.bulletModels = bulletModels;
    }
}
