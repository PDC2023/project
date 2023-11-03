package pdc.project.entity;

public final class Collision {
    public static boolean checkCollision(Entity self, Entity other) {
        int otherX = other.getX();
        int otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = self.getCollisionBox();

        int thisHalfWidth = thisBox.getWidth() / 2;
        int thisHalfHeight = thisBox.getHeight() / 2;
        int otherHalfWidth = otherBox.getWidth() / 2;
        int otherHalfHeight = otherBox.getHeight() / 2;

        int deltaX = Math.abs(self.getX() - otherX);
        int deltaY = Math.abs(self.getY() - otherY);

        boolean isTouchingX = deltaX == (thisHalfWidth + otherHalfWidth);
        boolean isTouchingY = deltaY == (thisHalfHeight + otherHalfHeight);

        if (isTouchingX && isTouchingY) return false;

        return deltaX <= (thisHalfWidth + otherHalfWidth) && deltaY <= (thisHalfHeight + otherHalfHeight);
    }

    public static CollisionInfo getCollision(Entity self, Entity other){
        int otherX = other.getX();
        int otherY = other.getY();
        CollisionBox otherBox = other.getCollisionBox();
        CollisionBox thisBox = self.getCollisionBox();

        int thisHalfWidth = thisBox.getWidth() / 2;
        int thisHalfHeight = thisBox.getHeight() / 2;
        int otherHalfWidth = otherBox.getWidth() / 2;
        int otherHalfHeight = otherBox.getHeight() / 2;

        int deltaX = Math.abs(self.getX() - otherX);
        int deltaY = Math.abs(self.getY() - otherY);

        boolean isTouchingX = deltaX == (thisHalfWidth + otherHalfWidth);
        boolean isTouchingY = deltaY == (thisHalfHeight + otherHalfHeight);
        boolean isOverlappingX = deltaX < (thisHalfWidth + otherHalfWidth);
        boolean isOverlappingY = deltaY < (thisHalfHeight + otherHalfHeight);

        boolean isCollisionX = isTouchingX || isOverlappingX;
        boolean isCollisionY = isTouchingY || isOverlappingY;

        if (isTouchingX && isTouchingY) return new CollisionInfo(CollisionState.NONE, CollisionDirection.NONE);

        if (isCollisionX && isCollisionY) {
            int overlappingByX = (thisHalfWidth + otherHalfWidth) - deltaX;
            int overlappingByY = (thisHalfHeight + otherHalfHeight) - deltaY;

            CollisionState state;
            CollisionDirection direction;

            if (overlappingByX > overlappingByY) {
                direction = (self.getY() < otherY) ? CollisionDirection.DOWN : CollisionDirection.UP;
                state = isTouchingX ? CollisionState.TOUCHING : CollisionState.OVERLAPPING;
            } else {
                direction = (self.getX() < otherX) ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
                state = isTouchingY ? CollisionState.TOUCHING : CollisionState.OVERLAPPING;
            }
            return new CollisionInfo(state, direction);
        } else {
            return new CollisionInfo(CollisionState.NONE, CollisionDirection.NONE);
        }
    }
}
