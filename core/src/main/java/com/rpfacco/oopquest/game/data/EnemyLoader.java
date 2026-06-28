package com.rpfacco.oopquest.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpfacco.oopquest.game.MovementStrategy;
import com.rpfacco.oopquest.game.WaypointMovement;

import java.util.HashMap;
import java.util.Map;

public class EnemyLoader {

    private static Map<String, Array<EnemyEntity>> cache;

    public static Map<String, Array<EnemyEntity>> load() {
        if (cache != null) return cache;

        FileHandle file = Gdx.files.internal("data/enemies.json");
        if (!file.exists()) {
            cache = new HashMap<>();
            return cache;
        }

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(file);

        cache = new HashMap<>();
        for (JsonValue entry = root.child; entry != null; entry = entry.next) {
            String mapId = entry.name;
            Array<EnemyEntity> enemies = new Array<>();
            for (JsonValue enemyVal = entry.child; enemyVal != null; enemyVal = enemyVal.next) {
                EnemyEntity enemy = new EnemyEntity();
                enemy.x = enemyVal.getFloat("x");
                enemy.y = enemyVal.getFloat("y");
                enemy.width = enemyVal.getFloat("width");
                enemy.height = enemyVal.getFloat("height");
                enemy.speed = enemyVal.getFloat("speed");
                enemy.moving = true;

                JsonValue movement = enemyVal.get("movement");
                if (movement != null) {
                    String type = movement.getString("type");
                    switch (type) {
                        case "waypoint":
                            enemy.strategy = WaypointMovement.fromJson(movement);
                            break;
                        default:
                            Gdx.app.error("EnemyLoader", "Unknown movement type: " + type);
                            break;
                    }
                }

                enemies.add(enemy);
            }
            cache.put(mapId, enemies);
        }
        return cache;
    }
}
