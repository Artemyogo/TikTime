package com.tiktime.controller;

import com.tiktime.Main;
import com.tiktime.model.upgrades.Upgrade;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.model.upgrades.UpgradeType;
import com.tiktime.screens.MenuScreen;
import com.tiktime.screens.Screen;
import com.tiktime.screens.ScreenHandler;
import com.tiktime.view.UpgradeView;

public class UpgradeController {
    private final ScreenHandler screenHandler;
    private final UpgradeModel model;
    private final UpgradeView view;

    public UpgradeController(ScreenHandler screenHandler, UpgradeView view) {
        this.screenHandler = screenHandler;
        this.view = view;
        this.model = UpgradeModel.getInstance();

        setUpgradesPrice();
        setButtonsState();
    }

    private void setUpgradesPrice() {
        view.setMoney(model.getMoney());
        for (Upgrade upg : model.getManager().getUpgrades()) {
            view.setUpgradePrice(upg.getType(), Upgrade.getCost(model.getUpgrade(upg.getType()).getLevel()));
        }
    }

    private void setButtonsState() {
        for (Upgrade upg : model.getManager().getUpgrades()) {
            UpgradeView.setActive(view.getButton(upg.getType()), isAvailableUpgrade(upg.getType()));
        }
    }

    public void onExitClicked() {
        view.dispose();
        screenHandler.setScreen(Screen.MAIN_MENU);
    }

    private void updateState() {
        model.save();
        setUpgradesPrice();
        setButtonsState();
    }

    public void onBuyClicked(UpgradeType type) {
        if (isAvailableUpgrade(type)) {
            Upgrade upg = model.getUpgrade(type);
            model.addMoney(-Upgrade.getCost(upg.getLevel()));
            upg.doUpgrade();
            updateState();
        }
    }

    private boolean isAvailableUpgrade(UpgradeType type) {
        return Upgrade.getCost(model.getUpgrade(type).getLevel()) <= model.getMoney();
    }

}
