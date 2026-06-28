package com.rpfacco.oopquest.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.rpfacco.oopquest.game.data.ProjectileEntity;

public class ProjectileSystem {

    private static final float MAP_WIDTH = 1920;
    private static final float MAP_HEIGHT = 1080;

    private Array<ProjectileEntity> projectiles;

    public ProjectileSystem() {
        this.projectiles = new Array<>();
    }

    public void update(float delta) {
        for (int i = projectiles.size - 1; i >= 0; i--) {
            ProjectileEntity p = projectiles.get(i);
            if (!p.alive) {
                projectiles.removeIndex(i);
                continue;
            }

            p.x += p.vx * p.speed * delta;
            p.y += p.vy * p.speed * delta;

            if (p.x < -p.size || p.x > MAP_WIDTH + p.size
                    || p.y < -p.size || p.y > MAP_HEIGHT + p.size) {
                p.alive = false;
                projectiles.removeIndex(i);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 0, 0, 1);
        for (ProjectileEntity p : projectiles) {
            if (p.alive) {
                shapeRenderer.circle(p.x, p.y, p.size / 2f);
            }
        }
    }

    public void add(ProjectileEntity projectile) {
        projectiles.add(projectile);
    }

    public void clear() {
        projectiles.clear();
    }
}
