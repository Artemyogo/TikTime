package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tiktime.controller.UpgradeController;
import com.tiktime.model.Upgrade;
import com.tiktime.model.UpgradeManager;
import com.tiktime.model.enums.UpgradeType;

import java.util.EnumMap;
import java.util.Map;

public class UpgradeView implements Disposable, Renderable {
    private final Stage stage;
    private final Skin skin;
    private final BitmapFont font;
    private final Label coinLabel;
    private final TextButton backButton;
    private final Map<UpgradeType, TextButton> upgradeButtons;
    private final Table upgrades;
    private final ScreenViewport screenViewport;

    public UpgradeView(UpgradeManager upgradeManager) {
        screenViewport = new ScreenViewport();
        screenViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage(screenViewport);
        upgrades = new Table();
        upgrades.setFillParent(true);
        stage.addActor(upgrades);
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        font = new BitmapFont(Gdx.files.internal("flat-earth/raw/font-title-export.fnt"));
        font.getData().setScale(1.3f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        coinLabel = new Label("", labelStyle);
        backButton = new TextButton("Back", skin);
        upgradeButtons = new EnumMap<>(UpgradeType.class);
        for (Upgrade upgrade : upgradeManager.getUpgrades()) {
            upgradeButtons.put(upgrade.getType(), new TextButton("", skin));
        }
        createUI();
    }

    public void setController(UpgradeController controller) {

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onExitClicked();
            }
        });

        for (Map.Entry<UpgradeType, TextButton> entry : upgradeButtons.entrySet()) {
            entry.getValue().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.onBuyClicked(entry.getKey());
                }
            });
        }

    }

    private void createUI() {
        Table topLeft = new Table().top().left();
        topLeft.setFillParent(true);
        stage.addActor(topLeft);

        Table topRight = new Table().top().right();
        topRight.setFillParent(true);
        stage.addActor(topRight);

        topLeft.add(backButton).width(252).height(80).padTop(56).padLeft(32);

        Texture coin = new Texture("Fantasy Minimal Pixel Art GUI by eta-commercial-free/UI/CoinIcon_16x18.png");
        Image coinImage = new Image(coin);
        topRight.add(coinImage).size(64, 64).padTop(64).padRight(32);
        topRight.add(coinLabel).padTop(64).padRight(52);

        drawUpgradeButtons();
    }

    private void drawUpgradeButtons() {
        boolean first = true;
        for (Map.Entry<UpgradeType, TextButton> entry : upgradeButtons.entrySet()) {
            float topPadding = 0f;
            if (first) {
                topPadding = 100f;
            }

            upgrades.add(entry.getValue()).height(100).width(552).padBottom(70).padTop(topPadding).row();
            first = false;
        }

        /*
        upgrades.add(upgradeButtons.get(UpgradeType.HP)).height(buttonHeight).width(buttonWidth).padTop(100).padBottom(70).row();
        upgrades.add(upgradeButtons.get(UpgradeType.SPEED)).height(100).width(552).padBottom(70).row();
        upgrades.add(upgradeButtons.get(UpgradeType.DAMAGE)).height(100).width(552).padBottom(70).row();
        upgrades.add(upgradeButtons.get(UpgradeType.REGEN)).height(100).width(552).row();
        */
    }

    public void setUpgradePrice(UpgradeType upgradeType, int price) {
        upgradeButtons.get(upgradeType).setText("To upgrade " + upgradeType.getName() + ": " + price + " coins");
    }

    public void setMoney(int money) {
        coinLabel.setText(money + "");
    }

    public TextButton getButton(UpgradeType upgradeType) {
        return upgradeButtons.get(upgradeType);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public static void setActive(TextButton btn, boolean isActive) {
        if (isActive) {
            btn.setColor(Color.GREEN);
            btn.setDisabled(false);
        } else {
            btn.setColor(Color.RED);
            btn.setDisabled(true);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        screenViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}


