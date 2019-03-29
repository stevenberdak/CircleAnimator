package app.controllers;

import javafx.scene.shape.Circle;

public class DotController extends FlexiblePaneController {

    private Circle dot;

    DotController(Circle dot, int screenWidth, int screenHeight) {
        super();
        setOriginXScreenWidth(screenWidth);
        setOriginYScreenHeight(screenHeight);
        init(dot);
    }

    DotController(Circle dot) {
        super();
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
