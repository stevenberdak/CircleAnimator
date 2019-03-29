package app;

import app.controllers.FxController;
import app.controllers.MainCircleController;
import app.controllers.SecondaryDotController;
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
    private static final double WIDTH_OFFSET = 1.2;

    private static FxController fxController;

    private ResourceBundle appStrings;
    private Logger logger;

    private MainCircleController mainCircleController;
    private SecondaryDotController secondaryDotController;

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
        fxController = fxmlLoader.getController();

        mainCircleController = new MainCircleController(DOT_RADIUS, (int) fxController.getTrailSlider().getValue(),
                offsetWidth(INITIAL_WIDTH, WIDTH_OFFSET), INITIAL_HEIGHT);
        setMainOscillators();
        mainCircleController.update();

        initializeFxController(fxController);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(mainCircleController.getTrailController().getPane(),
                mainCircleController.getMainDotController().getPane());

        secondaryDotController = new SecondaryDotController(new Circle(DOT_RADIUS),
                (int) mainCircleController.getMainDotController().getDot().getCenterX(),
                (int) mainCircleController.getMainDotController().getDot().getCenterY());

        stackPane.getChildren().add(secondaryDotController.getPane());

        // Add root.
        stackPane.getChildren().add(root);

        // Add layout to scene.
        Scene scene = new Scene(stackPane, INITIAL_WIDTH, INITIAL_HEIGHT);
        attachSceneListeners(scene);

        // Create timeline.
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), t -> mainCircleController.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Set up window.
        primaryStage.setTitle(getResourceString("app_name"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeFxController(FxController controller) {
        controller.setAboutLabelText(getResourceString("about_label"));
        controller.setSpeedLabelText(getResourceString("speed_label"));
        controller.setTrailLabelText(getResourceString("trail_label"));
        controller.setXMpLabel(getResourceString("main_x_mp_label"));
        controller.setYMpLabel(getResourceString("main_y_mp_label"));
        controller.getSpeedSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                mainCircleController.setOscillatorSpeeds(newValue.doubleValue()));
        controller.getTrailSlider().valueProperty().addListener((observable, oldValue, newValue) ->
                mainCircleController.getTrailController().setTrailLength(newValue.intValue()));
    }

    private void attachSceneListeners(Scene scene) {
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
                mainCircleController.setScreenWidth(offsetWidth(newValue.intValue(), WIDTH_OFFSET)));

        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                mainCircleController.setScreenHeight(newValue.intValue()));
    }

    private String getResourceString(String key) {
        if (appStrings == null) {
            appStrings = ResourceBundle.getBundle(APP_STRINGS);
        }

        return appStrings.getString(key);
    }

    private static int offsetWidth(int width, double coefficient) {
        return (int) (width * coefficient);
    }

    private void setMainOscillators() {
        mainCircleController.setXOscillator(new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START, false,
                true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getXMultiplaierSlider().getValue() * Math.cos(Math.toRadians(value));
            }
        });

        mainCircleController.setYOscillator(new Oscillator(new Oscillator.Range(0, 360), Oscillator.Start.START, false,
                true, fxController.getSpeedSlider().getValue()) {
            @Override
            protected double step(double value) {
                return fxController.getYMultiplierSlider().getValue() * Math.sin(Math.toRadians(value));
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
