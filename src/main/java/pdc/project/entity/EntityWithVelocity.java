package pdc.project.entity;

public interface EntityWithVelocity extends MoveableEntity {
    double getVelocityX();
    double getVelocityY();
    void setVelocityX(double velocityX);
    void setVelocityY(double velocityY);
}
