package pdc.project;

import pdc.project.entity.Entity;
import pdc.project.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Universe {


    public Player player = new Player(this,0, 0);

    public Set<Entity> entities = new HashSet<>();

    {
        entities.add(player);
    }

    public List<Entity> getCollisionEntities(Entity entity) {
        var collisionEntities = new ArrayList<Entity>();
        for (var otherEntity : entities) {
            if (otherEntity == entity) {
                continue;
            }
            if (entity.checkCollision(otherEntity)) {
                collisionEntities.add(otherEntity);
            }
        }
        return collisionEntities;
    }

}
