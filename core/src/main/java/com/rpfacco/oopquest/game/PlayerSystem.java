package com.rpfacco.oopquest.game;

import com.rpfacco.oopquest.game.data.model.Player;

public class PlayerSystem {

    private static final float ARRIVAL_DISTANCE = 2f;

    public void update(Player player, float delta) {
        if (player.getInvincibleTimer() > 0)
            player.setInvincibleTimer(Math.max(0, player.getInvincibleTimer() - delta));
        if (!player.isMoving()) return;

        float dx = player.getTargetX() - player.getX();
        float dy = player.getTargetY() - player.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= ARRIVAL_DISTANCE) {
            player.setX(player.getTargetX());
            player.setY(player.getTargetY());
            player.setMoving(false);
            return;
        }

        float step = player.getSpeed() * delta;
        player.setX(player.getX() + (dx / distance) * step);
        player.setY(player.getY() + (dy / distance) * step);
    }

    public void clampToBounds(Player player) {
        if (player.getX() < 0) player.setX(0);
        if (player.getY() < 0) player.setY(0);
        if (player.getX() + player.getWidth() > GameConfig.MAP_WIDTH)
            player.setX(GameConfig.MAP_WIDTH - player.getWidth());
        if (player.getY() + player.getHeight() > GameConfig.MAP_HEIGHT)
            player.setY(GameConfig.MAP_HEIGHT - player.getHeight());
    }
}
