package app.controllers;

import javafx.scene.shape.Circle;

public class SecondaryDotController extends FlexiblePaneController {

    private Circle dot;

    public SecondaryDotController(Circle circle, int initialX, int initialY) {
        super();
        setOriginXPoint(initialX);
        setOriginYPoint(initialY);

        init(circle);

        setPosition(initialX, initialY);
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
