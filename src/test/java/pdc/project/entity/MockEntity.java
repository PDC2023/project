package pdc.project.entity;

import pdc.project.Universe;

import java.awt.*;

public class MockEntity implements Entity {
    private int x, y;
    private CollisionBox collisionBox;

    public MockEntity(int x, int y, CollisionBox collisionBox) {
        this.x = x;
        this.y = y;
        this.collisionBox = collisionBox;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public boolean dead() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Universe getUniverse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void draw(Graphics2D g2d) {
        throw new UnsupportedOperationException();
    }
}
