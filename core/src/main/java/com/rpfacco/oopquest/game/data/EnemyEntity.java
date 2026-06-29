package com.rpfacco.oopquest.game.data;

import com.rpfacco.oopquest.game.MovementStrategy;
import com.rpfacco.oopquest.game.ShootPattern;

public class EnemyEntity {
    public float x;
    public float y;
    public float width;
    public float height;
    public float speed;
    public float bulletSpeed;
    public boolean moving;
    public MovementStrategy strategy;
    public ShootPattern shootPattern;

    public EnemyEntity() {}
}
