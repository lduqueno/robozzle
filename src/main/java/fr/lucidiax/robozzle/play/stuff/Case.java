package fr.lucidiax.robozzle.play.stuff;

import fr.lucidiax.robozzle.ui.Color;

public class Case {

    private int x, y;
    private Color color;
    private boolean hasStar, defaultHasStar;
    private boolean hasCursor;

    public Case(int x, int y, Color color, boolean hasStar, boolean hasCursor) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.hasStar = defaultHasStar = hasStar;
        this.hasCursor = hasCursor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isHasStar() {
        return hasStar;
    }

    public void setHasStar(boolean hasStar) {
        this.hasStar = hasStar;
    }

    public boolean isDefaultHasStar() {
        return defaultHasStar;
    }

    public boolean isHasCursor() {
        return hasCursor;
    }

    public void setHasCursor(boolean hasCursor) {
        this.hasCursor = hasCursor;
    }
}
