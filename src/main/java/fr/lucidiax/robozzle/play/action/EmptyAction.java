package fr.lucidiax.robozzle.play.action;

import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.ui.Images;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class EmptyAction extends ActionStep {

    public EmptyAction(Color color) {
        super(color);
    }

    @Override
    public void call(PlayTask play) {
    }

    @Override
    public void draw(Group root, Rectangle rectangle) {
        drawImage(root, rectangle, Images.RED_CROSS);
    }

    @Override
    public void undraw(Group root) {
    }

}
