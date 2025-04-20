package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.controller.MenuController;

public class MenuView implements Disposable {
    private final MenuController menuController;
    private final Stage stage;
    private final Skin skin;
    private TextButton startButton;
    private TextButton exitButton;
    private Label titleLabel;
    FitViewport viewport;

    public MenuView(MenuController menuController) {
        this.menuController = menuController;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));

        createUI();
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

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true); // Растягиваем на весь экран
        stage.addActor(table);

//        titleLabel = new Label("TikTime", skin, "title");
        table.add(titleLabel).padBottom(50).row();

        startButton = new TextButton("Start Game", skin);
        table.add(startButton).width(400).height(80).padBottom(20).row();

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Через menuController обрабатываем
                menuController.onPlayClicked();
            }
        });

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).width(400).height(80);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Через menuController обрабатываем
                menuController.onExitClicked();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public TextButton getStartButton() {
        return startButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }
}
