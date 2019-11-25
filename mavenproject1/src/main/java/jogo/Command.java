package jogo;

import jogo.Direction;

public class Command {

    public int direction;
    public Object object;

    public Command(int direction, Object object) {
        this.direction = direction;
        this.object = object;
    }

    public Direction getDirection() {
        if (direction > 3 || direction < 0) {
            return null;
        }
        return Direction.fromId(direction);
    }
}