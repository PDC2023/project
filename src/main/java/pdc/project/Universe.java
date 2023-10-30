package pdc.project;

import pdc.project.entity.*;
import pdc.project.ui.Main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Universe {

    public Player player = new Player(this, 0, 0);
    private int score = 0;
    private Main main;

    public Set<Entity> entities = new HashSet<>();
    public Universe(Main main) {
        this.main = main;
        entities.add(player);
        Mushroom mushroom1 = new Mushroom(this, 0, 270);
        entities.add(mushroom1);
    }
    public boolean spacePressed() {
        return pressedKeys.contains(KeyEvent.VK_SPACE);
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

    public List<CollisionRecord> fixOverlappingAndGetCollisionEntities(Entity entity) {
        var collisionEntities = new ArrayList<CollisionRecord>();
        for (var otherEntity : entities) {
            if (otherEntity == entity) {
                continue;
            }
            var result = entity.getCollision(otherEntity);
            if(result.getState() != CollisionState.NONE) {
                collisionEntities.add(new CollisionRecord(otherEntity, result));
            }
            if(entity instanceof MoveableEntity) {
                if(result.getState() == CollisionState.OVERLAPPING) {
                    if(result.getDirection() == CollisionDirection.DOWN) {
                        var otherTopY = otherEntity.getY() - otherEntity.getCollisionBox().getHeight() / 2;
                        var y = otherTopY - entity.getCollisionBox().getHeight() / 2;
                        ((MoveableEntity) entity).setY(y);
                    }
                }
            }
        }
        return collisionEntities;
    }

    public Set<Integer> pressedKeys = new HashSet<>();

    public boolean upPressed() {
        return pressedKeys.contains(KeyEvent.VK_UP) || pressedKeys.contains(KeyEvent.VK_W);
    }

    public boolean leftPressed() {
        return pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A);
    }

    public boolean rightPressed() {
        return pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D);
    }

    public boolean downPressed() {
        return pressedKeys.contains(KeyEvent.VK_DOWN) || pressedKeys.contains(KeyEvent.VK_S);
    }

    public void tick() {
        for (Entity entity : entities) {
            if (entity instanceof Coin && player.checkCollision(entity)) {
                Coin coin = (Coin) entity;
                if (!coin.isCollected()) {
                    coin.collect();
                    increaseScore(1);
                }
            } else if (entity instanceof Mushroom && player.checkCollision(entity)) {
                main.switchToLossScreen();
            }
        }
    }
    public void increaseScore(int increment) {
        score += increment;
    }

    public int getScore() {
        return score;
    }
}


