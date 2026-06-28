package com.rpfacco.oopquest.game;

import com.badlogic.gdx.utils.Array;
import com.rpfacco.oopquest.game.data.EnemyEntity;
import com.rpfacco.oopquest.game.data.ProjectileEntity;

public interface ShootPattern {
    Array<ProjectileEntity> generate(EnemyEntity enemy, Player player, float delta);
}
