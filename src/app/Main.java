package app;

import app.controllers.DotController;
import app.controllers.FxController;
import app.controllers.TrailController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final String APP_STRINGS = "app_strings";
    private static final String LOCK_FILE_NAME = ".lock";
    private static final int INITIAL_WIDTH = 800;
    private static final int INITIAL_HEIGHT = 500;
    private static final int DOT_RADIUS = 5;

    private static FxController fxController;

    private ResourceBundle appStrings;
    private Logger logger;
    private DotController dotController;
    private TrailController trailController;
    private Oscillator xOscillator, yOscillator;

    public Main() {
        logger = Logger.getLogger(getResourceString("app_name"), "log_messages");
        if (!SingleInstanceService.lockFile(LOCK_FILE_NAME, logger)) {
            logger.log(Level.INFO, "file_locked");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.log(Level.INFO, "application_start");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(appStrings.getString("main_fxml_file")));
        Parent root = fxmlLoader.load();

        initializeFxController(fxmlLoader.getController());

        // Initialize node object controllers.
        dotController = new DotController(new Circle(DOT_RADIUS), INITIAL_WIDTH, INITIAL_HEIGHT);
        trailController = new TrailController((int) fxController.getTrailSlider().getValue(),
                DOT_RADIUS, INITIAL_WIDTH, INITIAL_HEIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(trailController.getPane(), dotController.getPane(), root);

        // Add layout to scene.
        Scene scene = new Scene(stackPane, INITIAL_WIDTH, INITIAL_HEIGHT);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            dotController.setScreenWidth(newValue.intValue());
            trailController.setScreenWidth(newValue.intValue());
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            dotController.setScreenHeight(newValue.intValue());
            trailController.setScreenHeight(newValue.intValue());
        });

        // Create oscillators.
        xOscillator = new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START, false,
                true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getXMpSlider().getValue() * Math.cos(Math.toRadians(value));
            }
        };

        yOscillator = new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START, false,
                true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getYMpSlider().getValue() * Math.sin(Math.toRadians(value));
            }
        };

        // Create timeline.
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
                t -> {
                    dotController.setPosition(xOscillator.doStep(), yOscillator.doStep());
                    trailController.addCoordinate(new TrailController.Coordinate(dotController.getDot().getCenterX(),
                            dotController.getDot().getCenterY()));
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Set up window.
        primaryStage.setTitle(getResourceString("app_name"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeFxController(FxController controller) {
        fxController = controller;

        controller.setAboutLabelText(getResourceString("about_label"));
        controller.setSpeedLabelText(getResourceString("speed_label"));
        controller.setTrailLabelText(getResourceString("trail_label"));
        controller.setXMpLabel(getResourceString("x_mp_label"));
        controller.setYMpLabel(getResourceString("y_mp_label"));
        controller.getSpeedSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                setOscillatorSpeeds(newValue.doubleValue()));
        controller.getTrailSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                trailController.setTrailLength(newValue.intValue()));
    }

    private void setOscillatorSpeeds(double speed) {
        xOscillator.setSpeed(speed);
        yOscillator.setSpeed(speed);
    }

    private String getResourceString(String key) {
        if (appStrings == null) {
            appStrings = ResourceBundle.getBundle(APP_STRINGS);
        }

        return appStrings.getString(key);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
