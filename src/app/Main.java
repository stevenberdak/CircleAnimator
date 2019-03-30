package app;

import app.controllers.CircleController;
import app.controllers.FxController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final String APP_STRINGS = "app_strings";
    private static final String LOCK_FILE_NAME = ".lock";
    private static final int INITIAL_WIDTH = 900;
    private static final int INITIAL_HEIGHT = 600;
    private static final int DOT_RADIUS = 4;
    private static final double WIDTH_OFFSET = 1.2;

    private static FxController fxController;

    private ResourceBundle appStrings;
    private Logger logger;
    private Timeline timeline;
    private CircleController circleController;

    public Main() {
        appStrings = ResourceBundle.getBundle(APP_STRINGS);
        logger = Logger.getLogger(appStrings.getString("app_name"), "log_messages");
        if (!SingleInstanceService.lockFile(LOCK_FILE_NAME, logger)) {
            logger.log(Level.INFO, "file_locked");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.log(Level.INFO, "application_start");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(appStrings.getString("main_fxml_file")),
                appStrings);
        Parent root = fxmlLoader.load();
        fxController = fxmlLoader.getController();

        circleController = new CircleController(DOT_RADIUS, (int) fxController.getTrailSlider().getValue(),
                offsetWidth(INITIAL_WIDTH, WIDTH_OFFSET), INITIAL_HEIGHT);
        initializeOscillators();
        circleController.update();

        initializeFxController(fxController);

        circleController.setSatelliteDistance((int) fxController.getSatelliteDistanceSlider().getValue());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circleController.getTrailGraphingPane(),
                circleController.getCenterDotPane(),
                circleController.getSatelliteDotPane(),
                root);

        // Add layout to scene.
        Scene scene = new Scene(stackPane, INITIAL_WIDTH, INITIAL_HEIGHT);
        attachSceneListeners(scene);

        setupTimeline();

        // Set up window.
        primaryStage.setTitle(appStrings.getString("app_name"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTimeline() {
        // Create timeline.
        timeline = new Timeline(new KeyFrame(Duration.millis(10), t -> circleController.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(fxController.getSpeedMultiplierSlider().getValue());
        timeline.play();
    }

    private void initializeFxController(FxController controller) {
        controller.getSpeedSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                circleController.setOscillatorSpeeds(newValue.doubleValue()));
        controller.getTrailSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                circleController.getTrailGraphingPane().setTrailLength(newValue.intValue()));
        controller.getSatelliteSpeedSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                circleController.setSatelliteSpeed(newValue.doubleValue()));
        controller.getSatelliteDistanceSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                circleController.setSatelliteDistance(newValue.intValue()));
        controller.getSpeedMultiplierSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                timeline.setRate(newValue.intValue()));
    }

    private void attachSceneListeners(Scene scene) {
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
                circleController.setScreenWidth(offsetWidth(newValue.intValue(), WIDTH_OFFSET)));

        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                circleController.setScreenHeight(newValue.intValue()));
    }

    private static int offsetWidth(int width, double coefficient) {
        return (int) (width * coefficient);
    }

    private void initializeOscillators() {
        circleController.setXOscillator(new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START,
                false, true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getXMultiplaierSlider().getValue() * (Math.cos(Math.toRadians(value)));
            }
        });

        circleController.setYOscillator(new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START,
                false, true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getYMultiplierSlider().getValue() * (Math.sin(Math.toRadians(value)));
            }
        });

        circleController.setSatelliteOscillator(new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.CENTER,
                false, true, fxController.getSatelliteSpeedSlider().getValue()) {

            @Override
            protected double step(double value) {
                return value;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
