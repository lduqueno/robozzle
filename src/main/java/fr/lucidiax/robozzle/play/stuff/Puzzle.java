package fr.lucidiax.robozzle.play.stuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Puzzle {

    private String name;
    private int width, height;
    private Case[][] cases;
    private Case cursor, defaultCursor;
    private Direction cursorDirection, defaultDirection;
    private List<Function> functions;
    private int starsCount;

    public Puzzle(String name, int width, int height, Case[][] cases, Case cursor, Direction cursorDirection,
                  Map<Integer, Integer> functionsCount, int starsCount) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.cases = cases;
        this.cursor = defaultCursor = cursor;
        this.cursorDirection = defaultDirection = cursorDirection;
        this.functions = new ArrayList<>(functionsCount.size());
        int id = 0;
        for(Integer maxAction : functionsCount.values())
            functions.add(new Function(id++, maxAction));
        this.starsCount = starsCount;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Case[][] getCases() {
        return cases;
    }

    public Case getCursor() {
        return cursor;
    }

    public void setCursor(Case cursor){
        if(this.cursor != null)
            this.cursor.setHasCursor(false);
        this.cursor = cursor;
        cursor.setHasCursor(true);
    }

    public Direction getCursorDirection() {
        return cursorDirection;
    }

    public Case getDefaultCursor() {
        return defaultCursor;
    }

    public Direction getDefaultDirection() {
        return defaultDirection;
    }

    public void setCursorDirection(Direction cursorDirection) {
        this.cursorDirection = cursorDirection;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public int getStarsCount() {
        return starsCount;
    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", cases=" + Arrays.toString(cases) +
                ", cursor=" + cursor +
                ", direction=" + cursorDirection.name() +
                ", functions=" + functions +
                ", starsCount=" + starsCount +
                '}';
    }
}
