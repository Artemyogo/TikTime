package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiktime.controller.UpgradeController;

public class UpgradeView implements Disposable {

    private final Stage stage;
    private final Skin skin;
    private final BitmapFont font;
    private final Label coinLabel;
    private final TextButton backButton;
    private final TextButton hpUpgradeButton;
    private final TextButton speedUpgradeButton;
    private final TextButton damageUpgradeButton;
    private final TextButton regenUpgradeButton;

    public UpgradeView(){
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        font = new BitmapFont(Gdx.files.internal("flat-earth/raw/font-title-export.fnt"));
        font.getData().setScale(1.3f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        coinLabel = new Label("", labelStyle);

        backButton = new TextButton("Back", skin);
        hpUpgradeButton = new TextButton("", skin);
        speedUpgradeButton = new TextButton("", skin);
        damageUpgradeButton = new TextButton("", skin);
        regenUpgradeButton = new TextButton("", skin);

        createUI();
    }

    public void setController(UpgradeController controller) {

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onExitClicked();
            }
        });

        hpUpgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onUpgradeHPClicked();
            }
        });

        speedUpgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onUpgradeSpeedClicked();
            }
        });

        damageUpgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onUpgradeDamageClicked();
            }
        });

        regenUpgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onUpgradeRegenClicked();
            }
        });

    }

    private void createUI() {
        Table topLeft = new Table().top().left();
        topLeft.setFillParent(true);
        stage.addActor(topLeft);

        Table topRight = new Table().top().right();
        topRight.setFillParent(true);
        stage.addActor(topRight);

        Table upgrades = new Table();
        upgrades.setFillParent(true);
        stage.addActor(upgrades);

        topLeft.add(backButton).width(252).height(80).padTop(56).padLeft(32);

        Texture coin = new Texture("coin.png");
        Image coinImage = new Image(coin);
        topRight.add(coinImage).size(64, 64).padTop(64).padRight(32);
        topRight.add(coinLabel).padTop(64).padRight(52);

        upgrades.add(hpUpgradeButton).height(100).width(552).padTop(152).padBottom(70).row();
        upgrades.add(speedUpgradeButton).height(100).width(552).padBottom(70).row();
        upgrades.add(damageUpgradeButton).height(100).width(552).padBottom(70).row();
        upgrades.add(regenUpgradeButton).height(100).width(552).row();
    }

    public void setHpUpgradePrice(int price) {
        hpUpgradeButton.setText("To upgrade health: " + price + " coins");
    }

    public void setSpeedUpgradePrice(int price) {
        speedUpgradeButton.setText("To upgrade speed: " + price + " coins");
    }

    public void setDamageUpgradePrice(int price) {
        damageUpgradeButton.setText("To upgrade damage: " + price + " coins");
    }

    public void setRegenUpgradePrice(int price) {
        regenUpgradeButton.setText("To upgrade regeneration: " + price + " coins");
    }

    public void setMoney(int money) {
        coinLabel.setText(money + "");
    }

    public TextButton getHPUpgradeButton() {
        return hpUpgradeButton;
    }

    public TextButton getSpeedUpgradeButton() {
        return speedUpgradeButton;
    }

    public TextButton getDamageUpgradeButton() {
        return damageUpgradeButton;
    }

    public TextButton getRegenUpgradeButton() {
        return regenUpgradeButton;
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

    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}


