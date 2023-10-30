package pdc.project.entity;

public enum CollisionDirection {
    NONE,
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public boolean onX() {
        return this == LEFT || this == RIGHT;
    }

    public boolean onY() {
        return this == UP || this == DOWN;
    }
}
