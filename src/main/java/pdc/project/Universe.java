package pdc.project;

import pdc.project.entity.Entity;
import pdc.project.entity.Player;

import java.util.HashSet;
import java.util.Set;

public final class Universe {


    public Player player = new Player(0, 0);

    public Set<Entity> entities = new HashSet<>();

    {
        entities.add(player);
    }

}
