package com.rpfacco.oopquest.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.rpfacco.oopquest.game.data.ProjectileEntity;

import java.util.function.Consumer;

public class ProjectileSystem {

    private Array<ProjectileEntity> projectiles;

    public ProjectileSystem() {
        this.projectiles = new Array<>();
    }

    public void update(Player player, float delta, Consumer<ProjectileEntity> onHit) {
        for (int i = projectiles.size - 1; i >= 0; i--) {
            ProjectileEntity p = projectiles.get(i);
            if (!p.isAlive()) {
                projectiles.removeIndex(i);
                continue;
            }

            p.setX(p.getX() + p.getVx() * p.getSpeed() * delta);
            p.setY(p.getY() + p.getVy() * p.getSpeed() * delta);

            if (p.getX() < -p.getSize() || p.getX() > GameConfig.MAP_WIDTH + p.getSize()
                    || p.getY() < -p.getSize() || p.getY() > GameConfig.MAP_HEIGHT + p.getSize()) {
                p.setAlive(false);
                projectiles.removeIndex(i);
                continue;
            }

            if (circleRectCollision(p, player)) {
                onHit.accept(p);
                p.setAlive(false);
                projectiles.removeIndex(i);
            }
        }
    }

    private boolean circleRectCollision(ProjectileEntity p, Player player) {
        float cx = p.getX();
        float cy = p.getY();
        float r = p.getSize() / 2f;

        float closestX = Math.max(player.getX(), Math.min(cx, player.getX() + player.getWidth()));
        float closestY = Math.max(player.getY(), Math.min(cy, player.getY() + player.getHeight()));

        float dx = cx - closestX;
        float dy = cy - closestY;
        return dx * dx + dy * dy <= r * r;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 0, 0, 1);
        for (ProjectileEntity p : projectiles) {
            if (p.isAlive()) {
                shapeRenderer.circle(p.getX(), p.getY(), p.getSize() / 2f);
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
