package com.rpfacco.oopquest.game;

import com.badlogic.gdx.Game;
import com.rpfacco.oopquest.game.data.loader.DataManager;

public class OopQuest extends Game {
    private GameState gameState;
    private DataManager dataManager;

    @Override
    public void create() {
        gameState = new GameState();
        dataManager = new DataManager();
        setScreen(new MainMenuScreen(this));
    }

    public GameState getGameState() {
        return gameState;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void resetGame() {
        gameState.reset();
        dataManager.reloadEnemies();
    }
}