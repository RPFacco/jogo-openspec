package com.rpfacco.oopquest.game.data;

import com.rpfacco.oopquest.game.MovementStrategy;

public class EnemyEntity {
    public float x;
    public float y;
    public float width;
    public float height;
    public float speed;
    public boolean moving;
    public MovementStrategy strategy;

    public EnemyEntity() {}
}
