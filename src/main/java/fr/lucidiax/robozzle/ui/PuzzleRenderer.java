package fr.lucidiax.robozzle.ui;

import fr.lucidiax.robozzle.Robozzle;
import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.ActionType;
import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.play.action.*;
import fr.lucidiax.robozzle.play.stuff.Function;
import fr.lucidiax.robozzle.play.stuff.Puzzle;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleRenderer {

    private Puzzle puzzle;
    private Rectangle selectionnedRectangle;
    private Function selectionnedFunction;
    private int selectionnedStepId;
    private List<ColoredSquare> allSquares = new ArrayList<>();
    private PlayTask playTask;
    private boolean lockFunctions = false;
    private Button play;
    private Label errorMsg;

    public PuzzleRenderer(Puzzle puzzle){
        this.puzzle = puzzle;
        this.playTask = new PlayTask(this);
    }

    public Scene render(){
        Group root = new Group();

        //Creating puzzle
        for(int x = 0; x < puzzle.getWidth(); x++)
            for(int y = 0; y < puzzle.getHeight(); y++) {
                ColoredSquare square = new ColoredSquare(puzzle.getCases()[x][y], puzzle);
                root.getChildren().add(square);
                allSquares.add(square);
            }

        //Creating play/pause button
        play = new Button("Lancer");
        play.setLayoutX(Robozzle.FRAME_WIDTH / 3);
        play.setLayoutY(Robozzle.FRAME_HEIGHT - 100);
        play.setOnAction(e -> {
            if (lockFunctions)
                playTask.stop("Arrêt de la partie en cours..");
            else
                playTask.start();
            });
        root.getChildren().add(play);

        int x = Robozzle.FRAME_WIDTH / 3 * 2;
        int y = Robozzle.FRAME_HEIGHT / 2;

        //Creating error msg label
        errorMsg = createLabel("", Font.font( "Lucidia", FontWeight.NORMAL, 16), x - 50, y - 215);
        root.getChildren().add(errorMsg);

        //Creating animation speed slider
        Slider slider = new Slider();
        slider.setLayoutX(Robozzle.FRAME_WIDTH / 3 / 2 - 5);
        slider.setLayoutY(Robozzle.FRAME_HEIGHT - 100);
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> playTask.setAnimationSpeed((newValue.floatValue() + 1) * 2 / 100));
        root.getChildren().add(slider);

        root.getChildren().add(createLabel("Vitesse", Font.font( "Lucidia", FontWeight.NORMAL, 16), (int) slider.getLayoutX()
                + 46, (int) slider.getLayoutY() + 35)); //#easter egg font lucidiax hehe

        //Creating command board
        Group selectionGroup = new Group();
        List<Rectangle> allRectangles = new ArrayList<>();

        //Make selection tool rectangle
        Rectangle selRec = createRectangle(x, y - 170, 190, 110, Color.SNOW, Color.BLACK);
        allRectangles.add(selRec);
        selectionGroup.getChildren().add(selRec);

        //Put in selection rectangle the colors
        int iterCount = 0;
        for(fr.lucidiax.robozzle.ui.Color color : fr.lucidiax.robozzle.ui.Color.values()){
            Rectangle rectangle = createRectangle(x + iterCount++ * 32 + 8, y - 95, 30, 30, color.getJavaFxColor(),
                    Color.BLACK);
            rectangle.getProperties().put("selectionGroup", "color:" + color.name());
            allRectangles.add(rectangle);
            selectionGroup.getChildren().add(rectangle);
        }

        //Put in selection rectangle the functions
        iterCount = 0;
        for(Function function : puzzle.getFunctions()){
            Rectangle rectangle = createRectangle(x + iterCount * 32 + 8, y - 130, 30, 30, Color.GRAY, Color.BLACK);
            rectangle.getProperties().put("selectionGroup", "function:" + function.getId());
            allRectangles.add(rectangle);
            selectionGroup.getChildren().add(rectangle);
            new FunctionAction(fr.lucidiax.robozzle.ui.Color.NONE, iterCount++).draw(selectionGroup, rectangle);
        }

        //Put in selection rectangle the actions
        iterCount = 0;
        for(ActionType action : ActionType.values()){
            Rectangle rectangle = createRectangle(x + iterCount++ * 32 + 8, y - 165, 30, 30, Color.GRAY,
                    Color.BLACK);
            rectangle.getProperties().put("selectionGroup", "action:" + action.name());
            allRectangles.add(rectangle);
            selectionGroup.getChildren().add(rectangle);
            stepFromActionType(action, fr.lucidiax.robozzle.ui.Color.NONE).draw(selectionGroup, rectangle);
        }

        selectionGroup.setVisible(false); //Hide tool selection
        root.getChildren().add(selectionGroup);

        //Creating functions list render
        root.getChildren().add(createLabel("Liste fonctions :", Font.font( "Dotum", FontWeight.BOLD, 20), x, y - 28));
        for(Function function : puzzle.getFunctions()){
            for(int count = 0; count < function.getMaxActions(); count++) {
                Rectangle rectangle = createRectangle(x + count * 32 + 28, y - 1, 30, 30, Color.GRAY, Color.WHITE);
                rectangle.getProperties().put("function", function.getId() + ";" + count);
                allRectangles.add(rectangle);
                root.getChildren().add(rectangle);
            }
            root.getChildren().add(createLabel("F" + (function.getId() + 1), Font.font( "Dotum", FontWeight.NORMAL, 18), x, y));
            y += 33;
        }

        Collections.reverse(allRectangles); //Place the tiniest rectangle in priority (for click precision)

        //Creating clickable part
        Scene scene = Robozzle.getInstance().createScene(root);
        scene.setOnMouseClicked(e -> {
            if(lockFunctions)
                return;
            Rectangle rectangle = allRectangles.stream().filter(r -> r.contains(e.getX(), e.getY())).findFirst().orElse(null);
            if(rectangle != null){ //If clicked on rectangle
                if(rectangle == selRec || !rectangle.isVisible()) //If clicked on selectionbox rectangle ot invisible rectangle, do nothing
                    return;
                if(rectangle.getProperties().containsKey("function") && selectionnedRectangle != rectangle) { //If clicked on a function rectangle
                    if(selectionnedRectangle != null)
                        selectionnedRectangle.setStroke(Color.WHITE);
                    selectionnedRectangle = rectangle;
                    selectionnedRectangle.setStroke(Color.BLACK);
                    String[] split = rectangle.getProperties().get("function").toString().split(";");
                    selectionnedFunction = puzzle.getFunctions().get(Integer.parseInt(split[0]));
                    selectionnedStepId = Integer.parseInt(split[1]);
                    selectionGroup.setVisible(true);
                }else if(rectangle.getProperties().containsKey("selectionGroup")) { //If clicked on a selection tool
                    if(selectionnedFunction != null){
                        String[] split = rectangle.getProperties().get("selectionGroup").toString().split(":");
                        String type = split[0];
                        ActionStep step = selectionnedFunction.getSteps().get(selectionnedStepId);
                        fr.lucidiax.robozzle.ui.Color actualColor = step.getColor();
                        switch (type){
                            case "color":
                                fr.lucidiax.robozzle.ui.Color color = fr.lucidiax.robozzle.ui.Color.valueOf(split[1]);
                                step.setColor(color);
                                selectionnedRectangle.setFill(color.getJavaFxColor());
                                break;
                            case "function":
                                step.undraw(root);
                                step = new FunctionAction(actualColor, Integer.parseInt(split[1]));
                                step.draw(root, selectionnedRectangle);
                                break;
                            case "action":
                                ActionType actionType = ActionType.valueOf(split[1]);
                                step.undraw(root);
                                if(actionType == ActionType.EMPTY) { //If clearing rectangle, close all (else bug)
                                    step = new EmptyAction(actualColor);
                                    selectionnedFunction.getSteps().set(selectionnedStepId, step);
                                    closeBox(selectionGroup);
                                    return;
                                } else {
                                    step = stepFromActionType(actionType, actualColor);
                                    step.draw(root, selectionnedRectangle);
                                }
                                break;
                        }
                        selectionnedFunction.getSteps().set(selectionnedStepId, step);
                    }
                }
            }else //Set selection box invisible, release current selectioned box
                closeBox(selectionGroup);

        });
        return scene;
    }

    public void updatePuzzle(PlayTask task){
        Platform.runLater(() -> allSquares.forEach(square -> square.update(task)));
    }

    public void lockFunctions(boolean lock){
        lockFunctions = lock;
        Platform.runLater(() -> play.setText(lock ? "Arreter" : "Lancer"));
    }

    public void setErrorMessage(String errorMessage) {
        Platform.runLater(() -> errorMsg.setText(errorMessage));
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    private void closeBox(Group toClose) {
        toClose.setVisible(false);
        if(selectionnedRectangle != null)
            selectionnedRectangle.setStroke(Color.WHITE);
        selectionnedRectangle = null;
        selectionnedFunction = null;
        selectionnedStepId = 0;
    }

    private ActionStep stepFromActionType(ActionType actionType, fr.lucidiax.robozzle.ui.Color actualColor){
        switch (actionType) {
            case FORWARD:
                return new ForwardAction(actualColor);
            case TURN_RIGHT:
                return new TurnRightAction(actualColor);
            case TURN_LEFT:
                return new TurnLeftAction(actualColor);
        }
        return new EmptyAction(actualColor);
    }

    private Label createLabel(String text, Font font, int x, int y){
        Label label = new Label(text);
        label.setFont(font);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private Rectangle createRectangle(int x, int y, int width, int height, Paint color, Paint stroke){
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setStroke(stroke);
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }

}
