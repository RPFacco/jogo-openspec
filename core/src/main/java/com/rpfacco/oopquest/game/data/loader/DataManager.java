package com.rpfacco.oopquest.game.data.loader;

import com.badlogic.gdx.utils.Array;
import com.rpfacco.oopquest.game.data.model.EnemyEntity;
import com.rpfacco.oopquest.game.data.model.MapData;
import com.rpfacco.oopquest.game.data.model.NpcEntity;
import com.rpfacco.oopquest.game.data.model.QuizData;

import java.util.Map;

public class DataManager {

    private final EnemyLoader enemyLoader;
    private final QuizLoader quizLoader;
    private final NpcLoader npcLoader;
    private final MapLoader mapLoader;

    public DataManager() {
        this.enemyLoader = new EnemyLoader();
        this.quizLoader = new QuizLoader();
        this.npcLoader = new NpcLoader();
        this.mapLoader = new MapLoader();
    }

    public Array<EnemyEntity> getEnemies(String mapId) {
        return enemyLoader.load().get(mapId);
    }

    public Map<String, Array<EnemyEntity>> getAllEnemies() {
        return enemyLoader.load();
    }

    public void reloadEnemies() {
        enemyLoader.reload();
    }

    public QuizData getQuiz(String id) {
        return quizLoader.load().get(id);
    }

    public int getQuizCount() {
        return quizLoader.load().size();
    }

    public Array<NpcEntity> getNpcs(String mapId) {
        Map<String, Array<NpcEntity>> all = npcLoader.load();
        return all != null ? all.get(mapId) : null;
    }

    public MapData getMapData() {
        return mapLoader.load();
    }

    public String getStartMap() {
        return mapLoader.load().getStartMap();
    }
}
