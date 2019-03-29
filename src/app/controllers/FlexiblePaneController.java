package app.controllers;

import javafx.scene.layout.Pane;

public abstract class FlexiblePaneController implements HasPane {

    private Pane pane;
    private double originX, originY;

    FlexiblePaneController() {
        this.pane = new Pane();
    }

    void setOriginXScreenWidth(int screenWidth) {
        this.originX = (double) screenWidth / 2;
    }

    void setOriginYScreenHeight(int screenHeight) {
        this.originY = (double) screenHeight / 2;
    }
    
    void setOriginXPoint(int originX) {
        this.originX = originX;
    }

    void setOriginYPoint(int originY) {
        this.originY = originY;
    }

    double graphX(double value) {
        return originX + -value;
    }

    double graphY(double value) {
        return originY + -value;
    }

    public Pane getPane() {
        return pane;
    }
}
