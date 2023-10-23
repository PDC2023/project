package pdc.project;

public abstract class AbstractEntity implements Entity {
    protected double x, y;
    protected CollisionBox collisionBox;

    public AbstractEntity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.collisionBox = new CollisionBox(width, height);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public boolean checkCollision(AbstractEntity other) {
        double otherX = other.getX();
        double otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        return this.x < otherX + otherBox.getWidth() &&
                this.x + this.collisionBox.getWidth() > otherX &&
                this.y < otherY + otherBox.getHeight() &&
                this.y + this.collisionBox.getHeight() > otherY;
    }
}
