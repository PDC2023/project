package pdc.project.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EntityTest {

    @Test
    public void testCollision() {
        Entity entity1 = new MockEntity(0, 0, new CollisionBox(10, 10));
        Entity entity2 = new MockEntity(5, 5, new CollisionBox(10, 10));
        assertTrue(Collision.checkCollision(entity1, entity2), "Entities should be colliding");
    }

    @Test
    public void testNoCollision() {
        Entity entity1 = new MockEntity(0, 0, new CollisionBox(10, 10));
        Entity entity2 = new MockEntity(20, 20, new CollisionBox(10, 10));
        assertFalse(Collision.checkCollision(entity1, entity2), "Entities should not be colliding");
    }

    @Test
    public void testEdgeCollision() {
        Entity entity1 = new MockEntity(0, 0, new CollisionBox(10, 10));
        Entity entity2 = new MockEntity(10, 10, new CollisionBox(10, 10));
        assertFalse(Collision.checkCollision(entity1, entity2), "Entities should not be colliding at edges");
    }

    @Test
    public void testCornerCollision() {
        Entity entity1 = new MockEntity(0, 0, new CollisionBox(10, 10));
        Entity entity2 = new MockEntity(9, 9, new CollisionBox(10, 10));
        assertTrue(Collision.checkCollision(entity1, entity2), "Entities should be colliding at corner");
    }

    @Test
    public void testInsideCollision() {
        Entity entity1 = new MockEntity(5, 5, new CollisionBox(20, 20));
        Entity entity2 = new MockEntity(7, 7, new CollisionBox(5, 5));
        assertTrue(Collision.checkCollision(entity1, entity2), "One entity is inside the other, they should be colliding");
    }

    @Test
    public void testNegativeCoordinates() {
        Entity entity1 = new MockEntity(-10, -10, new CollisionBox(10, 10));
        Entity entity2 = new MockEntity(-5, -5, new CollisionBox(10, 10));
        assertTrue(Collision.checkCollision(entity1, entity2), "Entities with negative coordinates should be colliding");
    }

    @Test
    public void testZeroSize() {
        Entity entity1 = new MockEntity(0, 0, new CollisionBox(0, 0));
        Entity entity2 = new MockEntity(5, 5, new CollisionBox(10, 10));
        assertFalse(Collision.checkCollision(entity1, entity2), "Entity with zero size should not collide");
    }
}
