package app.controllers;

import javafx.scene.shape.Circle;

public class MainDotController extends FlexiblePaneController {

    private Circle dot;

    MainDotController(Circle dot, int screenWidth, int screenHeight) {
        super();
        setOriginXScreenWidth(screenWidth);
        setOriginYScreenHeight(screenHeight);
        init(dot);
    }

    private void init(Circle dot) {
        this.dot = dot;
        getPane().getChildren().add(dot);
    }

    void setPosition(double x, double y) {
        dot.setCenterX(graphX(x));
        dot.setCenterY(graphY(y));
    }

    public Circle getDot() {
        return dot;
    }
}
