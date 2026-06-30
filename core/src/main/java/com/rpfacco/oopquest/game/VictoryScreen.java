package com.rpfacco.oopquest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class VictoryScreen extends BaseScreen {

    private final GlyphLayout glyphLayout;

    public VictoryScreen(OopQuest app) {
        super(app);
        this.glyphLayout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            app.setScreen(new MainMenuScreen(app));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0.15f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(5);
        glyphLayout.setText(font, "YOU FINISHED!");
        float titleX = (GameConfig.MAP_WIDTH - glyphLayout.width) / 2f;
        float titleY = GameConfig.MAP_HEIGHT / 2f + 150;
        font.draw(batch, "YOU FINISHED!", titleX, titleY);

        glyphLayout.setText(font, "Congratulations!");
        float subX = (GameConfig.MAP_WIDTH - glyphLayout.width) / 2f;
        float subY = titleY - 120;
        font.draw(batch, "Congratulations!", subX, subY);

        glyphLayout.setText(font, "Click to continue");
        float clickX = (GameConfig.MAP_WIDTH - glyphLayout.width) / 2f;
        float clickY = subY - 120;
        font.draw(batch, "Click to continue", clickX, clickY);

        batch.end();
    }
}
