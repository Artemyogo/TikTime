package com.tiktime.controller;

import com.badlogic.gdx.Game;
import com.tiktime.Main;
import com.tiktime.model.Player;
import com.tiktime.screens.MenuScreen;
import com.tiktime.view.UpgradeView;

public class UpgradeController {
    private final Main game;
    private final Player player;
    private final UpgradeView view;

    public UpgradeController(Main game, UpgradeView view){
        this.game = game;
        this.player = game.getPlayer();
        this.view = view;

        this.view.setHpUpgradePrice(Player.getCost(this.player.getHpLevel()));
        this.view.setSpeedUpgradePrice(Player.getCost(this.player.getSpeedLevel()));
        this.view.setDamageUpgradePrice(Player.getCost(this.player.getDamageLevel()));
        this.view.setRegenUpgradePrice(Player.getCost(this.player.getRegenLevel()));
        this.view.setMoney(this.player.getMoney());
    }

    public void onExitClicked(){
        view.dispose();
        game.setScreen(new MenuScreen(game));
    }

    public void onUpgradeHPClicked(){
        if(player.tryAddHpLevel()) {
            view.setHpUpgradePrice(Player.getCost(player.getHpLevel()));
            view.setMoney(player.getMoney());
            player.save();
        }
    }

    public void onUpgradeSpeedClicked(){
        if(player.tryAddSpeedLevel()) {
            view.setSpeedUpgradePrice(Player.getCost(player.getSpeedLevel()));
            view.setMoney(player.getMoney());
            player.save();
        }
    }

    public void onUpgradeDamageClicked(){
        if(player.tryAddDamageLevel()) {
            view.setDamageUpgradePrice(Player.getCost(player.getDamageLevel()));
            view.setMoney(player.getMoney());
            player.save();
        }
    }

    public void onUpgradeRegenClicked(){
        if(player.tryAddRegenLevel()) {
            view.setRegenUpgradePrice(Player.getCost(player.getRegenLevel()));
            view.setMoney(player.getMoney());
            player.save();
        }
    }

}
