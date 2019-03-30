package app.controllers;

import javafx.scene.shape.Circle;

public class TrailGraphingPane extends GraphingPane {

    private int trailLength, radius;

    TrailGraphingPane(int trailLength, int radius, int screenWidth, int screenHeight) {
        setOriginXScreenWidth(screenWidth);
        setOriginYScreenHeight(screenHeight);
        this.trailLength = trailLength;
        this.radius = radius;
    }

    void addCoordinate(Coordinate coordinate) {
        getChildren().add(new Circle(coordinate.getX(), coordinate.getY(), radius));

        while (getChildren().size() * 2 > trailLength) {
            getChildren().remove(0);
        }
    }

    static class Coordinate {

        private double x, y;

        Coordinate(double x, double y) {
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
