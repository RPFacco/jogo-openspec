package com.rpfacco.oopquest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MainMenuScreen extends BaseScreen {

    private final GlyphLayout glyphLayout;

    public MainMenuScreen(OopQuest app) {
        super(app);
        this.glyphLayout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            app.resetGame();
            Gdx.app.log("MainMenuScreen", "reset -> lives=" + app.getGameState().getLives() + " quizzes=" + app.getGameState().getCompletedCount());
            app.setScreen(new GameplayScreen(app));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(5);
        glyphLayout.setText(font, "OopQuest");
        float titleX = (GameConfig.MAP_WIDTH - glyphLayout.width) / 2f;
        float titleY = GameConfig.MAP_HEIGHT / 2f + 100;
        font.draw(batch, "OopQuest", titleX, titleY);

        glyphLayout.setText(font, "Click to Start");
        float clickX = (GameConfig.MAP_WIDTH - glyphLayout.width) / 2f;
        float clickY = titleY - 120;
        font.draw(batch, "Click to Start", clickX, clickY);

        batch.end();
    }
}
