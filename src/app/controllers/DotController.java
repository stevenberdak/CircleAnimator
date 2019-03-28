package app.controllers;

import javafx.scene.shape.Circle;

public class DotController extends FlexiblePaneController {

    private Circle dot;

    public DotController(Circle dot, int screenWidth, int screenHeight) {
        super();
        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        init(dot);
    }

    private void init(Circle dot) {
        this.dot = dot;
        getPane().getChildren().add(dot);
    }

    public void setPosition(double x, double y) {
        dot.setCenterX(graphX(x));
        dot.setCenterY(graphY(y));
    }

    public Circle getDot() {
        return dot;
    }
}
