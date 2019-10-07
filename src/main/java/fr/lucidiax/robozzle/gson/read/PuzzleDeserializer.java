package fr.lucidiax.robozzle.gson.read;

import com.google.gson.*;
import fr.lucidiax.robozzle.play.stuff.Direction;
import fr.lucidiax.robozzle.play.stuff.Case;
import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.play.stuff.Puzzle;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

class PuzzleDeserializer implements JsonDeserializer<Puzzle> {

    public Puzzle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String name = object.get("name").getAsString();
        int width = object.get("width").getAsInt();
        int height = object.get("height").getAsInt();
        Case[][] cases = new Case[width][height];
        String raw = object.get("raw").getAsString();
        int currentFillWidth = 0, currentFillHeight = 0;
        int starsCount = 0;
        for(char c : raw.toCharArray()) {
            Color color = Color.NONE;
            boolean hasStar = false;
            switch (c) {
                case ' ':
                    color = Color.NONE;
                    break;
                case 'B':
                    hasStar = true;
                case 'b':
                    color = Color.BLUE;
                    break;
                case 'R':
                    hasStar = true;
                case 'r':
                    color = Color.RED;
                    break;
                case 'G':
                    hasStar = true;
                case 'g':
                    color = Color.GREEN;
                    break;
            }
            if (++currentFillWidth > width) {
                if(++currentFillHeight > height)
                    currentFillHeight = height - 1;
                currentFillWidth = 1;
            }
            if(hasStar)
                ++starsCount;
            cases[currentFillWidth - 1][currentFillHeight] = new Case(currentFillWidth - 1, currentFillHeight, color, hasStar, false);
        }
        int cursorDefaultX = object.get("cursorDefaultX").getAsInt();
        int cursorDefaultY = object.get("cursorDefaultY").getAsInt();
        Case cursor = cases[cursorDefaultX][cursorDefaultY];
        cursor.setHasCursor(true);
        Direction direction = Direction.valueOf(object.get("cursorDefaultDirection").getAsString());
        Map<Integer, Integer> functions = new HashMap<>();
        JsonArray array = object.get("functions").getAsJsonArray();
        for(int i = 0; i < array.size(); i++)
            functions.put(i, array.get(i).getAsInt());
        return new Puzzle(name, width, height, cases, cursor, direction, functions, starsCount);
    }
}
