package app;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

class DotController {

    private Pane pane;
    private Circle dot;
    private double originX, originY;

    DotController(Circle dot, int screenWidth, int screenHeight) {
        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        init(dot);
    }

    private void init(Circle dot) {
        this.dot = dot;
        pane = new Pane();
        pane.getChildren().add(dot);
    }

    Pane getPane() {
        return pane;
    }

    void setScreenWidth(int screenWidth) {
        this.originX = (double) screenWidth / 2;
    }

    void setScreenHeight(int screenHeight) {
        this.originY = (double) screenHeight / 2;
    }

    void setPosition(double x, double y) {
        dot.setCenterX(originX + -x);
        dot.setCenterY(originY + -y);
    }
}
