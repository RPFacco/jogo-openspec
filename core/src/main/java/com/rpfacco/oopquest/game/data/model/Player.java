package com.rpfacco.oopquest.game.data.model;

public class Player {

    private float x;
    private float y;
    private float width = 24;
    private float height = 24;
    private float speed = 320f;
    private float invincibleTimer;

    private float targetX;
    private float targetY;
    private boolean moving;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.targetX = startX;
        this.targetY = startY;
        this.moving = false;
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getInvincibleTimer() { return invincibleTimer; }
    public void setInvincibleTimer(float invincibleTimer) { this.invincibleTimer = invincibleTimer; }

    public float getTargetX() { return targetX; }
    public float getTargetY() { return targetY; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }
    public float getSpeed() { return speed; }

    public void setTarget(float tx, float ty) {
        this.targetX = tx;
        this.targetY = ty;
        this.moving = true;
    }

    public float getCenterX() {
        return x + width / 2f;
    }

    public float getCenterY() {
        return y + height / 2f;
    }
}
