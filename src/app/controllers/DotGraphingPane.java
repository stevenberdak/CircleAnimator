package app.controllers;

import javafx.scene.shape.Circle;

class DotGraphingPane extends GraphingPane {

    private Circle dot;

    DotGraphingPane(Circle dot, int screenWidth, int screenHeight) {
        super();
        setOriginXScreenWidth(screenWidth);
        setOriginYScreenHeight(screenHeight);
        init(dot);
    }

    DotGraphingPane(Circle dot) {
        super();
        init(dot);
    }

    private void init(Circle dot) {
        this.dot = dot;
        getChildren().add(dot);
    }

    void setPosition(double x, double y) {
        dot.setCenterX(graphX(x));
        dot.setCenterY(graphY(y));
    }

    Circle getDot() {
        return dot;
    }
}
