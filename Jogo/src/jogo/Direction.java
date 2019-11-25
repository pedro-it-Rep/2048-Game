package jogo;

public enum Direction { 
    up(0), left(1), down(2), right(3);

    private int direction;

    Direction (int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public static Direction fromId(int id) {
        for (Direction type : values()) {
            if (type.getDirection() == id) {
                return type;
            }
        }
        return null;
    }
}