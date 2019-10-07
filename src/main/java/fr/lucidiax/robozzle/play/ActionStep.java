package fr.lucidiax.robozzle.play;

import fr.lucidiax.robozzle.ui.Color;
import fr.lucidiax.robozzle.ui.Images;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class ActionStep {

    protected Color color = Color.NONE;

    public ActionStep(Color color){
        this.color = color;
    }

    public abstract void call(PlayTask play);
    public abstract void draw(Group root, Rectangle rectangle);
    public abstract void undraw(Group root);

    protected ImageView drawImage(Group root, Rectangle rectangle, Images image){
        ImageView currentImage = new ImageView(image.getLoadedImage());
        currentImage.setX(rectangle.getX());
        currentImage.setY(rectangle.getY());
        currentImage.setPreserveRatio(true);
        currentImage.setFitWidth(rectangle.getWidth() - 2);
        currentImage.setFitHeight(rectangle.getHeight() - 2);
        root.getChildren().add(currentImage);
        return currentImage;
    }

    protected Label drawText(Group root, Rectangle rectangle, String text){
        Label label = new Label(text);
        label.setFont(Font.font( "Dotum", FontWeight.BOLD, 18));
        label.setLayoutX(rectangle.getX() + 5);
        label.setLayoutY(rectangle.getY() + 2);
        root.getChildren().add(label);
        return label;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

}
