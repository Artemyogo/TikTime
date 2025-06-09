package com.tiktime.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.DeathController;
import com.tiktime.controller.MenuController;
import com.tiktime.model.MapModel;
import com.tiktime.view.world.Renderable;

public class DeathView implements Disposable, Renderable {
    private final DeathController deathController;
    private final Stage stage;
    private final Skin skin;
    private TextButton menuButton;
    private TextButton exitButton;
    private TextButton goToMenuButton;
    private Viewport screenViewport;
    private Label titleLabel;
    private Label mapCounterLabel;

    public DeathView(DeathController deathController) {
        this.deathController = deathController;
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

        titleLabel = new Label("GAME OVER", skin, "title");
//        titleLabel.setFontScale(10);
        table.add(titleLabel).padBottom(70).row();

        mapCounterLabel = new Label("Maps cleared: " + (MapModel.getCounter() - 1), skin, "title");
        table.add(mapCounterLabel).padBottom(50).row();

        menuButton = new TextButton("Back to menu", skin);
        table.add(menuButton).width(400).height(80).padBottom(20).row();

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deathController.onMenuClicked();
            }
        });

        exitButton = new TextButton("Exit", skin);
        table.add(exitButton).width(400).height(80);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deathController.onExitClicked();
            }
        });
    }
}
