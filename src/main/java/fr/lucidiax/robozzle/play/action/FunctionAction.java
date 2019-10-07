package fr.lucidiax.robozzle.play.action;

import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.ui.Color;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class FunctionAction extends ActionStep {

    private int functionId;
    private Label label;

    public FunctionAction(Color color, int functionId) {
        super(color);
        this.functionId = functionId;
    }

    @Override
    public void call(PlayTask play) {
        play.callFunction(play.getPuzzle().getFunctions().get(functionId));
    }

    @Override
    public void draw(Group root, Rectangle rectangle) {
        label = drawText(root, rectangle, "F" + (functionId + 1));
    }

    @Override
    public void undraw(Group root) {
        if(label != null){
            label.setVisible(false);
            root.getChildren().remove(label);
            label = null;
        }
    }

    public int getFunctionId() {
        return functionId;
    }

}
