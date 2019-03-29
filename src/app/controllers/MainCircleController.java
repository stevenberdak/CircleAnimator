package app.controllers;

import app.Oscillator;
import javafx.scene.shape.Circle;

public class MainCircleController {

    private MainDotController mainDotController;
    private TrailController trailController;
    private Oscillator xOscillator, yOscillator;

    public MainCircleController(int dotRadius, int initialTrailValue, int initialWidth, int initialHeight) {
        mainDotController = new MainDotController(new Circle(dotRadius), initialWidth, initialHeight);
        trailController = new TrailController(initialTrailValue, dotRadius, initialWidth, initialHeight);
    }

    public void setXOscillator(Oscillator oscillator) {
        this.xOscillator = oscillator;
    }

    public void setYOscillator(Oscillator oscillator) {
        this.yOscillator = oscillator;
    }

    public MainDotController getMainDotController() {
        return mainDotController;
    }

    public TrailController getTrailController() {
        return trailController;
    }

    public void update() {
        mainDotController.setPosition(xOscillator.doStep(), yOscillator.doStep());
        trailController.addCoordinate(new TrailController.Coordinate(
                mainDotController.getDot().getCenterX(),
                mainDotController.getDot().getCenterY())
        );
    }

    public void setScreenWidth(int width) {
        mainDotController.setOriginXScreenWidth(width);
        trailController.setOriginXScreenWidth(width);
    }

    public void setScreenHeight(int height) {
        mainDotController.setOriginYScreenHeight(height);
        trailController.setOriginYScreenHeight(height);
    }

    public void setOscillatorSpeeds(double speed) {
        xOscillator.setSpeed(speed);
        yOscillator.setSpeed(speed);
    }
}
