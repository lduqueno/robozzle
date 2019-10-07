package fr.lucidiax.robozzle.ui;

public enum Color {

    BLUE(new javafx.scene.paint.Color(12/255f, 105/255f, 1, 1)), RED(javafx.scene.paint.Color.RED),
    GREEN(javafx.scene.paint.Color.FORESTGREEN), NONE(javafx.scene.paint.Color.GRAY);

    private javafx.scene.paint.Color javaFxColor;

    Color(javafx.scene.paint.Color javaFxColor){
        this.javaFxColor = javaFxColor;
    }

    public javafx.scene.paint.Color getJavaFxColor() {
        return javaFxColor;
    }
}
