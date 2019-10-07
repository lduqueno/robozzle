package fr.lucidiax.robozzle;

import fr.lucidiax.robozzle.gson.read.PuzzleReader;
import fr.lucidiax.robozzle.gson.write.PuzzleCreator;
import fr.lucidiax.robozzle.ui.PuzzleRenderer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Robozzle extends Application {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 720;

    private static Robozzle instance;
    private Stage stage;
    private Scene mainMenu;

    public static void main(String[] args) {
        launch(args);
    }

    public static Robozzle getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;
        primaryStage.setTitle("Robozzle");
        primaryStage.setWidth(FRAME_WIDTH);
        primaryStage.setHeight(FRAME_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setScene(getMainMenu());


        primaryStage.show();
    }

    public Scene getMainMenu(){
        if(mainMenu == null){

            Group root = new Group();
            Scene theScene = createScene(root);

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choisissez un puzzle");
            File file = new File("Maps");
            if(!file.exists())
                file.mkdirs();
            chooser.setInitialDirectory(file);
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON File", "*.json"));

            Button chooseButton = new Button("Choisir puzzle");
            chooseButton.setLayoutX(FRAME_WIDTH / 2 - 120);
            chooseButton.setLayoutY(FRAME_HEIGHT / 2);
            chooseButton.setOnAction(e -> {
                File choosed = chooser.showOpenDialog(stage);
                if(choosed == null)
                    return;
                stage.setScene(new PuzzleRenderer(new PuzzleReader(choosed).read()).render());
            });
            root.getChildren().add(chooseButton);

            Button createButton = new Button("Creer puzzle");
            createButton.setLayoutX(FRAME_WIDTH / 2 + 60);
            createButton.setLayoutY(FRAME_HEIGHT / 2);
            createButton.setOnAction(e -> stage.setScene(new PuzzleCreator().render()));
            root.getChildren().add(createButton);
            mainMenu = theScene;
        }
        return mainMenu;
    }

    public Scene createScene(Group root){
        root.setMouseTransparent(false);
        Canvas canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
        root.getChildren().add(canvas);
        canvas.setMouseTransparent(true);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        Font theFont = Font.font( "Impact", FontWeight.NORMAL, 24);
        gc.setFont(theFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText( "Robozzle", FRAME_WIDTH / 2, 10);

        Button mainMenu = new Button("Menu principal");
        mainMenu.setOnAction(e -> stage.setScene(getMainMenu()));
        root.getChildren().add(mainMenu);
        return new Scene(root);
    }

    public Stage getStage() {
        return stage;
    }

}
