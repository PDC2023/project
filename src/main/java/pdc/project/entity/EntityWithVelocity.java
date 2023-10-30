package pdc.project.entity;

public interface EntityWithVelocity extends MoveableEntity {
    int getVelocityX();
    int getVelocityY();
    void setVelocityX(int velocityX);
    void setVelocityY(int velocityY);
}
