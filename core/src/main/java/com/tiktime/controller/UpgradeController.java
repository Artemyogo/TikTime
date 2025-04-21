package com.tiktime.controller;

import com.tiktime.Main;
import com.tiktime.model.UpgradeModel;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.UpgradeView;

public class UpgradeController {
    private final Main game;
    private final UpgradeModel model;
    private final UpgradeView view;

    public UpgradeController(Main game, UpgradeView view){
        this.game = game;
        this.view = view;
        this.model = UpgradeModel.getInstance();

        this.view.setHpUpgradePrice(UpgradeModel.getCost(this.model.getHpLevel()));
        this.view.setSpeedUpgradePrice(UpgradeModel.getCost(this.model.getSpeedLevel()));
        this.view.setDamageUpgradePrice(UpgradeModel.getCost(this.model.getDamageLevel()));
        this.view.setRegenUpgradePrice(UpgradeModel.getCost(this.model.getRegenLevel()));
        this.view.setMoney(this.model.getMoney());
    }

    public void onExitClicked(){
        view.dispose();
        game.setScreen(new MenuScreen(game));
    }

    public void onUpgradeHPClicked(){
        if(model.tryAddHpLevel()) {
            view.setHpUpgradePrice(UpgradeModel.getCost(model.getHpLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    public void onUpgradeSpeedClicked(){
        if(model.tryAddSpeedLevel()) {
            view.setSpeedUpgradePrice(UpgradeModel.getCost(model.getSpeedLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    public void onUpgradeDamageClicked(){
        if(model.tryAddDamageLevel()) {
            view.setDamageUpgradePrice(UpgradeModel.getCost(model.getDamageLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

    public void onUpgradeRegenClicked(){
        if(model.tryAddRegenLevel()) {
            view.setRegenUpgradePrice(UpgradeModel.getCost(model.getRegenLevel()));
            view.setMoney(model.getMoney());
            model.save();
        }
    }

}
