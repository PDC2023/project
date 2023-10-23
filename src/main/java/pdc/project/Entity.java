package pdc.project;

public interface Entity {
    double getX();

    double getY();

    CollisionBox getCollisionBox();

    boolean checkCollision(Entity other);
}
