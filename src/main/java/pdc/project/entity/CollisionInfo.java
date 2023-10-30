package pdc.project.entity;

public
class CollisionInfo {
    private final CollisionState state;
    private final CollisionDirection direction;

    public CollisionInfo(CollisionState state, CollisionDirection direction) {
        this.state = state;
        this.direction = direction;
    }

    public CollisionState getState() {
        return state;
    }

    public CollisionDirection getDirection() {
        return direction;
    }
}