package com.tiktime.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.tiktime.controller.UpgradeController;
import com.tiktime.model.UpgradeModel;
import com.tiktime.view.UpgradeView;

public class UpgradeScreen extends ScreenAdapter {
    private final Game game;
    UpgradeController controller;
    UpgradeModel model;
    UpgradeView view;
    public UpgradeScreen(Game game){
        this.game = game;
        model = new UpgradeModel();
        view = new UpgradeView();
        controller = new UpgradeController(game, model, view);
        view.setController(controller);
    }
}
