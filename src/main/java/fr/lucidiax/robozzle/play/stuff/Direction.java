package fr.lucidiax.robozzle.play.stuff;

public enum Direction {

    UP(0, -1, 0), RIGHT(1, 0, 90), DOWN(0, 1, 180), LEFT(-1, 0, 270);

    private int addX, addY, rotateDegrees = 0;

    Direction(int addX, int addY, int rotateDegrees){
        this.addX = addX;
        this.addY = addY;
        this.rotateDegrees = rotateDegrees;
    }

    public int getAddX() {
        return addX;
    }

    public int getAddY() {
        return addY;
    }

    public int getRotateDegrees() { return rotateDegrees; }

}
