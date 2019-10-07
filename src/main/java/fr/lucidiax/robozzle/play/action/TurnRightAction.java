package fr.lucidiax.robozzle.play.action;

import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.stuff.Direction;
import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.ui.Images;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class TurnRightAction extends ActionStep {

    private ImageView imageView;

    public TurnRightAction(Color color) {
        super(color);
    }

    @Override
    public void call(PlayTask play) {
        Direction dir = play.getPuzzle().getCursorDirection();

        int newId = dir.ordinal() + 1;
        if(newId >= Direction.values().length)
            newId = 0;
        dir = Direction.values()[newId];

        play.getPuzzle().setCursorDirection(dir);
    }

    @Override
    public void draw(Group root, Rectangle rectangle) {
        imageView = drawImage(root, rectangle, Images.ARROW_TURN_RIGHT);
    }

    @Override
    public void undraw(Group root) {
        if(imageView != null){
            imageView.setVisible(false);
            root.getChildren().remove(imageView);
            imageView = null;
        }
    }
}
