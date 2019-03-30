package app.controllers;

import app.Oscillator;
import javafx.scene.shape.Circle;

public class CircleController {

    private DotGraphingPane centerDotPane, satelliteDotPane;
    private TrailGraphingPane trailGraphingPane;
    private Oscillator xOscillator, yOscillator, satelliteOscillator;

    private int satelliteDistance;

    public CircleController(int dotRadius, int initialTrailValue, int initialWidth, int initialHeight) {
        centerDotPane = new DotGraphingPane(new Circle(dotRadius), initialWidth, initialHeight);
        satelliteDotPane = new DotGraphingPane(new Circle(dotRadius));
        trailGraphingPane = new TrailGraphingPane(initialTrailValue, dotRadius, initialWidth, initialHeight);
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

    public DotGraphingPane getCenterDotPane() {
        return centerDotPane;
    }

    public DotGraphingPane getSatelliteDotPane() {
        return satelliteDotPane;
    }

    public TrailGraphingPane getTrailGraphingPane() {
        return trailGraphingPane;
    }

    public void update() {
        centerDotPane.setPosition(xOscillator.doStep(), yOscillator.doStep());

        satelliteDotPane.setOriginXPoint((int) centerDotPane.getDot().getCenterX());
        satelliteDotPane.setOriginYPoint((int) centerDotPane.getDot().getCenterY());

        int angle = (int) satelliteOscillator.doStep();

        satelliteDotPane.setPosition(satelliteDistance * Math.cos(Math.toRadians(angle)),
                satelliteDistance * Math.sin(Math.toRadians(angle)));

        trailGraphingPane.addCoordinate(new TrailGraphingPane.Coordinate(centerDotPane.getDot().getCenterX(),
                centerDotPane.getDot().getCenterY()));

        trailGraphingPane.addCoordinate(new TrailGraphingPane.Coordinate(satelliteDotPane.getDot().getCenterX(),
                satelliteDotPane.getDot().getCenterY()));
    }

    public void setSatelliteSpeed(double speed) {
        satelliteOscillator.setSpeed(speed);
    }

    public void setSatelliteDistance(int distance) {
        satelliteDistance = distance;
    }

    public void setScreenWidth(int width) {
        centerDotPane.setOriginXScreenWidth(width);
        trailGraphingPane.setOriginXScreenWidth(width);
    }

    public void setScreenHeight(int height) {
        centerDotPane.setOriginYScreenHeight(height);
        trailGraphingPane.setOriginYScreenHeight(height);
    }

    public void setOscillatorSpeeds(double speed) {
        xOscillator.setSpeed(speed);
        yOscillator.setSpeed(speed);
    }
}
