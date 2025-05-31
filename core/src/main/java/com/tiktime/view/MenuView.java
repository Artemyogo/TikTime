package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.MenuController;
import com.tiktime.view.world.Renderable;

public class MenuView implements Disposable, Renderable {
    private final MenuController menuController;
    private final Stage stage;
    private final Skin skin;
    private TextButton startButton;
    private TextButton exitButton;
    private Label titleLabel;
    private TextButton upgradeButton;
    private Viewport screenViewport;

    public MenuView(MenuController menuController) {
        this.menuController = menuController;
        screenViewport = new ScreenViewport();
        screenViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage(screenViewport);
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));

        createUI();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
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
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //titleLabel = new Label("TikTime", skin, "title");
        //table.add(titleLabel).padBottom(50).row();

        startButton = new TextButton("Start Game", skin);
        table.add(startButton).width(400).height(80).padBottom(20).row();

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuController.onPlayClicked();
            }
        });

        upgradeButton = new TextButton("Upgrade", skin);
        table.add(upgradeButton).width(400).height(80).padBottom(20).row();

        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuController.onUpgradeClicked();
            }
        });

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).width(400).height(80);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuController.onExitClicked();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
