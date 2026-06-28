package com.rpfacco.oopquest.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.rpfacco.oopquest.game.data.EnemyEntity;
import com.rpfacco.oopquest.game.data.ProjectileEntity;

public class BurstPattern implements ShootPattern {

    private static final float BURST_INTERVAL = 1f / 4f;

    private int burstSize;
    private float cooldown;
    private float timer;
    private int burstIndex;
    private boolean onCooldown;

    public BurstPattern(int burstSize, float cooldown) {
        this.burstSize = burstSize;
        this.cooldown = cooldown;
        this.timer = 0;
        this.burstIndex = 0;
        this.onCooldown = false;
    }

    @Override
    public Array<ProjectileEntity> generate(EnemyEntity enemy, Player player, float delta) {
        timer += delta;

        if (onCooldown) {
            if (timer >= cooldown) {
                onCooldown = false;
                burstIndex = 0;
                timer = 0;
            }
            return new Array<>();
        }

        if (burstIndex >= burstSize) {
            onCooldown = true;
            timer = 0;
            return new Array<>();
        }

        float fireTime = (burstIndex == 0) ? 0 : BURST_INTERVAL;
        if (timer < fireTime) return new Array<>();

        timer = 0;
        burstIndex++;

        ProjectileEntity p = createProjectile(enemy, player);
        Array<ProjectileEntity> result = new Array<>();
        result.add(p);
        return result;
    }

    private ProjectileEntity createProjectile(EnemyEntity enemy, Player player) {
        float cx = enemy.x + enemy.width / 2f;
        float cy = enemy.y + enemy.height / 2f;
        float px = player.x + player.width / 2f;
        float py = player.y + player.height / 2f;

        float dx = px - cx;
        float dy = py - cy;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        float vx = dx / dist;
        float vy = dy / dist;

        ProjectileEntity p = new ProjectileEntity();
        p.x = cx;
        p.y = cy;
        p.vx = vx;
        p.vy = vy;
        p.speed = enemy.speed;
        p.size = 16;
        p.alive = true;
        return p;
    }

    public static BurstPattern fromJson(JsonValue config) {
        int burstSize = config.getInt("burstSize");
        float cooldown = config.getFloat("cooldown");
        return new BurstPattern(burstSize, cooldown);
    }
}
