package fr.lucidiax.robozzle.ui;

import fr.lucidiax.robozzle.play.PlayTask;
import fr.lucidiax.robozzle.play.stuff.Case;
import fr.lucidiax.robozzle.play.stuff.Puzzle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColoredSquare extends StackPane {

    private static final int SQUARE_SIZE = 50;

    private Case myCase;
    private Puzzle puzzle;
    private ImageView currentImage;
    private int x, y, scaledSquareX, scaledSquareY;

    public ColoredSquare(Case myCase, Puzzle puzzle) {
        this.myCase = myCase;
        this.puzzle = puzzle;

        this.scaledSquareX = SQUARE_SIZE * 11 / puzzle.getWidth();
        this.scaledSquareY = SQUARE_SIZE * 11 / puzzle.getHeight();

        Rectangle rectangle = new Rectangle(scaledSquareX, scaledSquareY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(myCase.getColor() == fr.lucidiax.robozzle.ui.Color.NONE ? new Color(28/255f, 29/255f, 30/255f,
                1) : myCase.getColor().getJavaFxColor());

        x = myCase.getX() * scaledSquareX + 1 + 150;
        y = myCase.getY() * scaledSquareY + 1 + 60;

        setLayoutX(x);
        setLayoutY(y);

        getChildren().add(rectangle);
        update(null);
    }

    void update(PlayTask task){
        Images image = myCase.isHasStar() ? Images.STAR : (myCase.isHasCursor() ? Images.ARROW : null);
        removeImage();
        if(image != null){
            if(myCase.isHasStar() && myCase.isHasCursor()){
                myCase.setHasStar(false);
                image = Images.ARROW;
                if(task != null)
                    task.incrementStarsCount();
            }
            currentImage = new ImageView(image.getLoadedImage());
            currentImage.setX(x);
            currentImage.setY(y);
            currentImage.setPreserveRatio(true);
            currentImage.setFitWidth(scaledSquareX - 10);
            currentImage.setFitHeight(scaledSquareY - 10);
            if(image == Images.ARROW)
                currentImage.setRotate(puzzle.getCursorDirection().getRotateDegrees());
            getChildren().add(currentImage);
        }
    }

    private void removeImage(){
        if(currentImage == null)
            return;
        currentImage.setVisible(false);
        getChildren().remove(currentImage);
        currentImage = null;
    }

}
