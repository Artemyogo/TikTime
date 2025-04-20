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
    }

    void onExitClicked(){
        game.setScreen(new MenuScreen(game));
    }

    void onUpgradeHPClicked(){
        if(model.tryAddHpLevel()) {
            view.setHpUpgradePrice(UpgradeModel.upgradeCost(model.getHpLevel()));
            view.setMoney(model.getMoney());
        }
    }

    void onUpgradeSpeedClicked(){
        if(model.tryAddSpeedLevel()) {
            view.setSpeedUpgradePrice(UpgradeModel.upgradeCost(model.getSpeedLevel()));
            view.setMoney(model.getMoney());
        }
    }

    void onUpgradeDamageClicked(){
        if(model.tryAddDamageLevel()) {
            view.setDamageUpgradePrice(UpgradeModel.upgradeCost(model.getDamageLevel()));
            view.setMoney(model.getMoney());
        }
    }
    void onUpgradeRegenClicked(){
        if(model.tryAddRegenLevel()) {
            view.setRegenUpgradePrice(UpgradeModel.upgradeCost(model.getRegenLevel()));
            view.setMoney(model.getMoney());
        }
    }

}
