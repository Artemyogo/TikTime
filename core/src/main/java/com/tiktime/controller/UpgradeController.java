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

        setUpgradesPrice();
        setButtonsState();
    }

    private void setUpgradesPrice() {
        view.setHpUpgradePrice(UpgradeModel.getCost(model.getHpLevel()));
        view.setSpeedUpgradePrice(UpgradeModel.getCost(model.getSpeedLevel()));
        view.setDamageUpgradePrice(UpgradeModel.getCost(model.getDamageLevel()));
        view.setRegenUpgradePrice(UpgradeModel.getCost(model.getRegenLevel()));
        view.setMoney(model.getMoney());
    }

    private void setButtonsState() {
        UpgradeView.setActive(view.getHPUpgradeButton(), isAvailableHPUpgrade());
        UpgradeView.setActive(view.getSpeedUpgradeButton(), isAvailableSpeedUpgrade());
        UpgradeView.setActive(view.getDamageUpgradeButton(), isAvailableDamageUpgrade());
        UpgradeView.setActive(view.getRegenUpgradeButton(), isAvailableRegenUpgrade());
    }

    public void onExitClicked(){
        view.dispose();
        game.setScreen(new MenuScreen(game));
    }

    public void onUpgradeHPClicked(){
        if (model.tryAddHpLevel()) {
            model.save();
            setUpgradesPrice();
            setButtonsState();
        }
    }

    public void onUpgradeSpeedClicked(){
        if (model.tryAddSpeedLevel()) {
            model.save();
            setUpgradesPrice();
            setButtonsState();
        }
    }

    public void onUpgradeDamageClicked(){
        if (model.tryAddDamageLevel()) {
            model.save();
            setUpgradesPrice();
            setButtonsState();
        }
    }

    public void onUpgradeRegenClicked(){
        if (model.tryAddRegenLevel()) {
            model.save();
            setUpgradesPrice();
            setButtonsState();
        }
    }

    private boolean isAvailableHPUpgrade() {
        return UpgradeModel.getCost(model.getHpLevel()) <= model.getMoney();
    }

    private boolean isAvailableSpeedUpgrade() {
        return UpgradeModel.getCost(model.getSpeedLevel()) <= model.getMoney();
    }

    private boolean isAvailableDamageUpgrade() {
        return UpgradeModel.getCost(model.getDamageLevel()) <= model.getMoney();
    }

    private boolean isAvailableRegenUpgrade() {
        return UpgradeModel.getCost(model.getRegenLevel()) <= model.getMoney();
    }

}
