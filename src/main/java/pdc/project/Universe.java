package pdc.project;

import pdc.project.entity.*;
import pdc.project.ui.Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code Universe} class represents the game universe where entities and gameplay elements reside.
 * It manages player input, entity collisions, scoring, and game logic for a particular game instance.
 */
public final class Universe {

    public Player player = new Player(this, 0, 0);
    private int score = 0;
    private final Main main;

    public Set<Entity> entities = new HashSet<>();
    public List<Entity> entitiesToAdd = new ArrayList<>();
    public List<Entity> entitiesToRemove = new ArrayList<>();

    public SavePoint lastSavePoint;

    /**
     * Constructs a new game universe with the specified main application instance.
     *
     * @param main The main application instance associated with this universe.
     */
    public Universe(Main main) {
        this.main = main;
        entities.add(player);
        lastSavePoint = new SavePoint(0, this, 0, 0);
        entities.add(lastSavePoint);
    }
    public void Reset(){
        SavePoint newSavePoint = new SavePoint(0, this, 0, 0);
        entities.add(newSavePoint);
    }

    public boolean spacePressed() {
        return pressedKeys.contains(KeyEvent.VK_SPACE);
    }

    /**
     * Gets a list of entities that are colliding with the specified entity.
     *
     * @param entity The entity for which collision detection is performed.
     * @return A list of entities that are colliding with the specified entity.
     */
    public List<Entity> getCollisionEntities(Entity entity) {
        var collisionEntities = new ArrayList<Entity>();
        for (var otherEntity : entities) {
            if (otherEntity == entity) {
                continue;
            }
            if (Collision.checkCollision(entity, otherEntity)) {
                collisionEntities.add(otherEntity);
            }
        }
        return collisionEntities;
    }

    public List<CollisionRecord> getCollisionRecords(Entity entity) {
        var collisionEntities = new ArrayList<CollisionRecord>();
        for (var otherEntity : entities) {
            if (otherEntity == entity) {
                continue;
            }
            var result = Collision.getCollision(entity, otherEntity);
            if (result.getState() != CollisionState.NONE) {
                collisionEntities.add(new CollisionRecord(otherEntity, result));
            }
        }
        return collisionEntities;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getCollectedCoins() {
        return score;
    }


    /**
     * Fixes overlapping entities and returns a list of collision records.
     *
     * @param entity The entity for which collision resolution is performed.
     * @return A list of collision records indicating resolved collisions.
     */
    public List<CollisionRecord> fixOverlappingAndGetCollisionEntities(Entity entity) {
        var collisionEntities = new ArrayList<CollisionRecord>();
        for (var otherEntity : entities) {
            if (otherEntity == entity) {
                continue;
            }
            var result = Collision.getCollision(entity, otherEntity);
            if (result.getState() != CollisionState.NONE) {
                collisionEntities.add(new CollisionRecord(otherEntity, result));
            }
            if (entity instanceof MoveableEntity && !(otherEntity instanceof NoPhysicalCollisionEntity)) {
                if (result.getState() == CollisionState.OVERLAPPING) {
                    var direction = result.getDirection();
                    var otherBox = otherEntity.getCollisionBox();
                    var entityBox = entity.getCollisionBox();
                    var moveableEntity = (MoveableEntity) entity;

                    int x;
                    int y;
                    switch (direction) {
                        case DOWN:
                            var otherTopY = otherEntity.getY() - otherBox.getHeight() / 2;
                            y = otherTopY - entityBox.getHeight() / 2;
                            moveableEntity.setY(y);
                            if (moveableEntity instanceof EntityWithVelocity && ((EntityWithVelocity) moveableEntity).getVelocityY() > 0) {
                                ((EntityWithVelocity) moveableEntity).setVelocityY(0);
                            }
                            break;
                        case UP:
                            var otherBottomY = otherEntity.getY() + otherBox.getHeight() / 2;
                            y = otherBottomY + entityBox.getHeight() / 2;
                            moveableEntity.setY(y);
                            if (moveableEntity instanceof EntityWithVelocity && ((EntityWithVelocity) moveableEntity).getVelocityY() < 0) {
                                ((EntityWithVelocity) moveableEntity).setVelocityY(0);
                            }
                            break;
                        case LEFT:
                            var otherRightX = otherEntity.getX() + otherBox.getWidth() / 2;
                            x = otherRightX + entityBox.getWidth() / 2;
                            moveableEntity.setX(x);
                            if (moveableEntity instanceof EntityWithVelocity && ((EntityWithVelocity) moveableEntity).getVelocityX() < 0) {
                                ((EntityWithVelocity) moveableEntity).setVelocityX(0);
                            }
                            break;
                        case RIGHT:
                            var otherLeftX = otherEntity.getX() - otherBox.getWidth() / 2;
                            x = otherLeftX - entityBox.getWidth() / 2;
                            moveableEntity.setX(x);
                            if (moveableEntity instanceof EntityWithVelocity && ((EntityWithVelocity) moveableEntity).getVelocityX() > 0) {
                                ((EntityWithVelocity) moveableEntity).setVelocityX(0);
                            }
                            break;
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

    public void goingToSavePoint() {
        var effect = new SavePointResumeEffect(this, player.getX(), player.getY());
        effect.facingLeft = player.facingLeft;
        entitiesToAdd.add(effect);
        entitiesToRemove.add(player);
        main.gameScreen.pauseForReturningToSavePoint();
        SoundEffect.play(SoundEffect.death);
        Timer timer = new Timer(500, e -> {
            effect.die();
            entitiesToAdd.add(player);
            main.gameScreen.resumeForReturningToSavePoint();
            player.setVelocityX(0);
            player.setVelocityY(0);
            player.setX(lastSavePoint.getX());
            player.setY(lastSavePoint.getY());
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void win() {
        main.switchToWinScreen();
    }

    private void checkPlayerOutOfBound() {
        if (this.player.getY() > 1024) {
            this.player.goingToSavePoint();
        }
    }

    public void preTick() {
        this.entities.addAll(this.entitiesToAdd);
        this.entitiesToRemove.forEach(this.entities::remove);
        this.entitiesToAdd.clear();
        this.entitiesToRemove.clear();
    }
    public void nextLevel(){
        main.switchToLevel1();

    }

    public void tick() {
        var deaths = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (entity.dead()) {
                deaths.add(entity);
                continue;
            }
            entity.tick();
        }
        deaths.forEach(entities::remove);
        if (false) {
            if (getCollectedCoins() == 16) {
                main.switchToWinScreen();
            }
        }
        checkPlayerOutOfBound();
    }

}


