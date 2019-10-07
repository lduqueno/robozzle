package fr.lucidiax.robozzle.play.action;

import fr.lucidiax.robozzle.play.ActionStep;
import fr.lucidiax.robozzle.play.stuff.Direction;
import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.play.stuff.Case;
import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.ui.Images;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ForwardAction extends ActionStep {

    private ImageView imageView;

    public ForwardAction(Color color) {
        super(color);
    }

    @Override
    public void call(PlayTask play) {
        Direction dir = play.getPuzzle().getCursorDirection();
        Case cursor = play.getPuzzle().getCursor();
        boolean inWall = false;
        try {
            cursor = play.getPuzzle().getCases()[cursor.getX() + dir.getAddX()][cursor.getY() + dir.getAddY()];
        } catch(ArrayIndexOutOfBoundsException e){
            inWall = true;
        }
        if(cursor.getColor() == Color.NONE)
            inWall = true;
        if(inWall){
            play.stop("Partie terminée, vous êtes rentré dans un mur !");
            return;
        }
        play.getPuzzle().setCursor(cursor);
    }

    @Override
    public void draw(Group root, Rectangle rectangle) {
        imageView = drawImage(root, rectangle, Images.ARROW);
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
