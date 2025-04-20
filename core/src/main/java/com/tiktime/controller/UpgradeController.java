package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.tiktime.model.UpgradeModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.UpgradeView;

public class UpgradeController {
    private final Game game;
    private final UpgradeModel model;
    private final UpgradeView view;

    public UpgradeController(Game game, UpgradeModel model, UpgradeView view){
        this.game = game;
        this.model = model;
        this.view = view;

        this.view.setHpUpgradePrice(UpgradeModel.getCost(this.model.getHpLevel()));
        this.view.setSpeedUpgradePrice(UpgradeModel.getCost(this.model.getSpeedLevel()));
        this.view.setDamageUpgradePrice(UpgradeModel.getCost(this.model.getDamageLevel()));
        this.view.setRegenUpgradePrice(UpgradeModel.getCost(this.model.getRegenLevel()));
        this.view.setMoney(this.model.getMoney());
    }

    void onExitClicked(){
        game.setScreen(new MenuScreen(game));
    }

    void onUpgradeHPClicked(){
        if(model.tryAddHpLevel()) {
            view.setHpUpgradePrice(UpgradeModel.getCost(model.getHpLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    void onUpgradeSpeedClicked(){
        if(model.tryAddSpeedLevel()) {
            view.setSpeedUpgradePrice(UpgradeModel.getCost(model.getSpeedLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    void onUpgradeDamageClicked(){
        if(model.tryAddDamageLevel()) {
            view.setDamageUpgradePrice(UpgradeModel.getCost(model.getDamageLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    void onUpgradeRegenClicked(){
        if(model.tryAddRegenLevel()) {
            view.setRegenUpgradePrice(UpgradeModel.getCost(model.getRegenLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

}
