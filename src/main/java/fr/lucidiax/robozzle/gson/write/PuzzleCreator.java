package fr.lucidiax.robozzle.gson.write;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.lucidiax.robozzle.Robozzle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;

public class PuzzleCreator {

    public Scene render(){
        Group root = new Group();

        TextField name = new TextField("Nom du puzzle");
        name.setLayoutX(Robozzle.FRAME_WIDTH / 2 - 120 - 35);
        name.setLayoutY(70);

        TextField raw = new TextField("Raw puzzle");
        raw.setLayoutX(Robozzle.FRAME_WIDTH / 2 - 120 - 35);
        raw.setLayoutY(105);

        TextField cursorX = new TextField("Cursor X");
        cursorX.setLayoutX(Robozzle.FRAME_WIDTH / 2 - 120 - 35);
        cursorX.setLayoutY(140);

        TextField cursorDirection = new TextField("Cursor Direction");
        cursorDirection.setLayoutX(Robozzle.FRAME_WIDTH / 2 - 120 - 35);
        cursorDirection.setLayoutY(175);

        TextField width = new TextField("Width");
        width.setLayoutX(Robozzle.FRAME_WIDTH / 2 + 5);
        width.setLayoutY(70);

        TextField height = new TextField("Height");
        height.setLayoutX(Robozzle.FRAME_WIDTH / 2 + 5);
        height.setLayoutY(105);

        TextField cursorY = new TextField("Cursor Y");
        cursorY.setLayoutX(Robozzle.FRAME_WIDTH / 2 + 5);
        cursorY.setLayoutY(140);

        TextField functions = new TextField("Functions");
        functions.setLayoutX(Robozzle.FRAME_WIDTH / 2 + 5);
        functions.setLayoutY(175);

        Button create = new Button("Créer !");
        create.setOnAction(e -> createPuzzle(name.getText(), raw.getText(), width.getText(), height.getText(), cursorX.getText(),
                cursorY.getText(), cursorDirection.getText(), functions.getText()));
        create.setLayoutX(Robozzle.FRAME_WIDTH / 2 - 25);
        create.setLayoutY(220);

        root.getChildren().add(name);
        root.getChildren().add(raw);
        root.getChildren().add(cursorX);
        root.getChildren().add(cursorDirection);
        root.getChildren().add(width);
        root.getChildren().add(height);
        root.getChildren().add(cursorY);
        root.getChildren().add(functions);
        root.getChildren().add(create);
        return Robozzle.getInstance().createScene(root);
    }

    private void createPuzzle(String text, String raw, String width, String height, String cursorX, String cursorY,
                              String cursorDirection, String functions){
        File file = new File(System.getenv("APPDATA"), "Robozzle/" + text + ".json");
        try {
            if(!file.exists())
                file.createNewFile();
            JsonObject object = new JsonObject();
            object.addProperty("name", text);
            object.addProperty("raw", raw);
            object.addProperty("width", Integer.parseInt(width));
            object.addProperty("height", Integer.parseInt(height));
            object.addProperty("cursorDefaultX", Integer.parseInt(cursorX));
            object.addProperty("cursorDefaultY", Integer.parseInt(cursorY));
            object.addProperty("cursorDefaultDirection", cursorDirection);
            JsonArray functionsArray = new JsonArray();
            for(String s : functions.split(";"))
                functionsArray.add(new JsonPrimitive(Integer.parseInt(s)));
            object.add("functions", functionsArray);
            try (FileWriter writer = new FileWriter(file)) {
               writer.write(object.toString());
            }
            Robozzle.getInstance().getStage().setScene(Robozzle.getInstance().getMainMenu());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
