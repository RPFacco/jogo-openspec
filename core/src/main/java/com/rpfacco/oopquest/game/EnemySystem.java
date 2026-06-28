package com.rpfacco.oopquest.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.rpfacco.oopquest.game.data.EnemyEntity;
import com.rpfacco.oopquest.game.data.ProjectileEntity;

public class EnemySystem {

    private Array<EnemyEntity> enemies;

    public void setEnemies(Array<EnemyEntity> enemies) {
        this.enemies = enemies;
    }

    public void update(float delta) {
        if (enemies == null) return;

        for (EnemyEntity enemy : enemies) {
            if (!enemy.moving || enemy.strategy == null) continue;
            enemy.strategy.update(enemy, delta);
        }
    }

    public void updateShooting(Player player, float delta, ProjectileSystem projectileSystem) {
        if (enemies == null) return;

        for (EnemyEntity enemy : enemies) {
            if (enemy.shootPattern == null) continue;
            Array<ProjectileEntity> projs = enemy.shootPattern.generate(enemy, player, delta);
            for (ProjectileEntity p : projs) {
                if (p != null) projectileSystem.add(p);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (enemies == null) return;

        shapeRenderer.setColor(1, 0, 0, 1);
        for (EnemyEntity enemy : enemies) {
            shapeRenderer.rect(enemy.x, enemy.y, enemy.width, enemy.height);
        }
    }
}
