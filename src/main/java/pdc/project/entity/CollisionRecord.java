package pdc.project.entity;
public class CollisionRecord {
    private final Entity entity;
    private final CollisionInfo collisionInfo;

    public CollisionRecord(Entity entity, CollisionInfo collisionInfo) {
        this.entity = entity;
        this.collisionInfo = collisionInfo;
    }

    public Entity getEntity() {
        return entity;
    }

    public CollisionInfo getCollisionInfo() {
        return collisionInfo;
    }
}