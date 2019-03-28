package app.controllers;

import javafx.scene.layout.Pane;

public abstract class FlexiblePaneController implements HasPane {

    private Pane pane;
    private double originX, originY;

    protected FlexiblePaneController() {
        this.pane = new Pane();
    }

    public void setScreenWidth(int screenWidth) {
        this.originX = (double) screenWidth / 2;
    }

    public void setScreenHeight(int screenHeight) {
        this.originY = (double) screenHeight / 2;
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
