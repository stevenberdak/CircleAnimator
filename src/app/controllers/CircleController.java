package app.controllers;

import app.Oscillator;
import javafx.scene.shape.Circle;

public class CircleController {

    private DotController centerDotController, satelliteDotController;
    private TrailController trailController;
    private Oscillator xOscillator, yOscillator, satelliteOscillator;

    private int satelliteDistance;

    public CircleController(int dotRadius, int initialTrailValue, int initialWidth, int initialHeight) {
        centerDotController = new DotController(new Circle(dotRadius), initialWidth, initialHeight);
        satelliteDotController = new DotController(new Circle(dotRadius));
        trailController = new TrailController(initialTrailValue, dotRadius, initialWidth, initialHeight);
    }

    public void setXOscillator(Oscillator oscillator) {
        this.xOscillator = oscillator;
    }

    public void setYOscillator(Oscillator oscillator) {
        this.yOscillator = oscillator;
    }

    public void setSatelliteOscillator(Oscillator oscillator) {
        this.satelliteOscillator = oscillator;
    }

    public DotController getCenterDotController() {
        return centerDotController;
    }

    public DotController getSatelliteDotController() {
        return satelliteDotController;
    }

    public TrailController getTrailController() {
        return trailController;
    }

    public void update() {
        centerDotController.setPosition(xOscillator.doStep(), yOscillator.doStep());

        satelliteDotController.setOriginXPoint((int) centerDotController.getDot().getCenterX());
        satelliteDotController.setOriginYPoint((int) centerDotController.getDot().getCenterY());

        int angle = (int) satelliteOscillator.doStep();

        satelliteDotController.setPosition(satelliteDistance * Math.cos(Math.toRadians(angle)),
                satelliteDistance * Math.sin(Math.toRadians(angle)));

        trailController.addCoordinate(new TrailController.Coordinate(centerDotController.getDot().getCenterX(),
                centerDotController.getDot().getCenterY()));

        trailController.addCoordinate(new TrailController.Coordinate(satelliteDotController.getDot().getCenterX(),
                satelliteDotController.getDot().getCenterY()));
    }

    public void setSatelliteSpeed(double speed) {
        satelliteOscillator.setSpeed(speed);
    }

    public void setSatelliteDistance(int distance) {
        satelliteDistance = distance;
    }

    public void setScreenWidth(int width) {
        centerDotController.setOriginXScreenWidth(width);
        trailController.setOriginXScreenWidth(width);
    }

    public void setScreenHeight(int height) {
        centerDotController.setOriginYScreenHeight(height);
        trailController.setOriginYScreenHeight(height);
    }

    public void setOscillatorSpeeds(double speed) {
        xOscillator.setSpeed(speed);
        yOscillator.setSpeed(speed);
    }
}
