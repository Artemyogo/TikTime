package com.tiktime.view.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiktime.controller.WorldController;
import com.tiktime.controller.inputprocessors.SeparatorFromWorldInputProcessor;

public class PauseView implements Pausable, Renderable, Disposable {
    private final Stage stage;
    private final TextButton continueButton;
    private final TextButton exitButton;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final ShapeRenderer pauseShape;
    private final Table pauseMenu;
    private boolean paused = false;

    public PauseView(Viewport viewport, OrthographicCamera camera) {
        this.viewport = viewport;
        this.camera = camera;
        pauseShape = new ShapeRenderer();
        stage = new Stage(viewport);
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        continueButton = new TextButton("Continue game", new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json")));
        exitButton = new TextButton("Exit to menu", new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json")));
        pauseMenu.center();
        pauseMenu.add(continueButton).width(300).height(80).padBottom(20).row();
        pauseMenu.add(exitButton).width(300).height(80);
        stage.addActor(pauseMenu);
    }

    public void setController(WorldController worldController) {
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.setPaused(false);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.goToMenu();
            }
        });
        worldController.getInputMultiplexer().addProcessor(new SeparatorFromWorldInputProcessor(worldController));
        worldController.getInputMultiplexer().addProcessor(stage);
    }

    @Override
    public void setPause(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            return;
        }

        drawPauseDarkening(delta);
        stage.act(delta);
        stage.draw();
    }

    public void drawPauseDarkening(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        pauseShape.setProjectionMatrix(camera.combined);
        pauseShape.begin(ShapeRenderer.ShapeType.Filled);

        float width = viewport.getScreenWidth();
        float height = viewport.getScreenHeight();
        int stepW = 100;
        int stepH = 100;
        float rectWidth = width / stepW;
        float rectHeight = height / stepH;
        float centerX = width / 2f;
        float centerY = height / 2f;
        float maxDist = (float) Math.sqrt(centerX * centerX + centerY * centerY);
        float maxAlpha = 1.1f;

        for (int i = 0; i < stepW; i++) {
            for (int j = 0; j < stepH; j++) {
                float x = i * rectWidth, y = j * rectHeight;
                float curDist = (float) Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                float t = (maxAlpha * (curDist / maxDist));
                float alpha = t * maxAlpha;
                pauseShape.setColor(0, 0, 0, alpha);
                pauseShape.rect(
                    x,
                    y,
                    rectWidth,
                    rectHeight
                );
            }
        }

        pauseShape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        stage.dispose();
        pauseShape.dispose();
        pauseMenu.clear();
    }
}
