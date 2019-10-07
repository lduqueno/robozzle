package fr.lucidiax.robozzle.ui;

import javafx.scene.image.Image;

public enum Images {

    STAR("star.png"), ARROW("arrow.png"), ARROW_TURN_RIGHT("turn_right.png"), ARROW_TURN_LEFT("turn_left.png"), RED_CROSS("red_cross.png");

    private Image loadedImage;

    Images(String file){
        this.loadedImage = new Image(getClass().getClassLoader().getResourceAsStream(file));
    }

    public Image getLoadedImage() {
        return loadedImage;
    }

}
