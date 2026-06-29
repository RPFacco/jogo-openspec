package com.rpfacco.oopquest.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudRenderer {

    private final SpriteBatch batch;
    private final BitmapFont font;

    public HudRenderer(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
    }

    public void render(GameState gameState) {
        font.draw(batch, "Lives: " + gameState.getLives(), 20, GameConfig.MAP_HEIGHT - 20);
    }
}
