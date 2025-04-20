package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiktime.controller.UpgradeController;

public class UpgradeView implements Disposable {
    private UpgradeController controller;
    private final Stage stage;
    private final Skin skin;
    private final Label coinLabel;

    public UpgradeView(){
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        coinLabel = new Label("", skin);

        createUI();
    }

    public void setController(UpgradeController controller) {
        this.controller = controller;
    }

    private void createUI() {
        Table table = new Table().top().right();
        table.setFillParent(true);
        stage.addActor(table);

        Texture coin = new Texture("coin.png");
        Image coinImage = new Image(coin);
        table.add(coinImage).size(32, 32).pad(10);
        table.add(coinLabel).padRight(20);
    }

    public void setHpUpgradePrice(int i) {
    }

    public void setSpeedUpgradePrice(int i) {
    }

    public void setDamageUpgradePrice(int i) {
    }

    public void setRegenUpgradePrice(int i) {
    }

    public void setMoney(int money) {
        coinLabel.setText(money + "");
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
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
    }
}


