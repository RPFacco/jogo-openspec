package com.rpfacco.oopquest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rpfacco.oopquest.game.OopQuest;

public class MainMenuScreen implements Screen {

    private static final float SCREEN_WIDTH = 1920;
    private static final float SCREEN_HEIGHT = 1080;

    private final OopQuest jogoGame;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenuScreen(OopQuest jogoGame) {
        this.jogoGame = jogoGame;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);
        camera.update();

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            GameState gs = jogoGame.getGameState();
            gs.reset();
            Gdx.app.log("MainMenuScreen", "reset -> lives=" + gs.lives + " quizzes=" + gs.completedQuizzes.size());
            jogoGame.setScreen(new GameplayScreen(jogoGame));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "OopQuest", 100, 200);
        font.draw(batch, "Click to Start", 100, 160);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
