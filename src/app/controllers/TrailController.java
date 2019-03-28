package app.controllers;

import javafx.scene.shape.Circle;

public class TrailController extends FlexiblePaneController {

    private int trailLength, radius;

    public TrailController(int trailLength, int radius, int screenWidth, int screenHeight) {
        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        this.trailLength = trailLength;
        this.radius = radius;
    }

    public void addCoordinate(Coordinate coordinate) {
        getPane().getChildren().add(new Circle(coordinate.getX(), coordinate.getY(), radius));

        while (getPane().getChildren().size() > trailLength) {
            getPane().getChildren().remove(0);
        }
    }

    public static class Coordinate {

        private double x, y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double getX() {
            return x;
        }

        double getY() {
            return y;
        }
    }

    public void setTrailLength(int trailLength) {
        this.trailLength = trailLength;
    }
}
