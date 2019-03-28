package app;

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
    private static final int INITIAL_WIDTH = 600;
    private static final int INITIAL_HEIGHT = 400;

    private ResourceBundle appStrings;
    private Logger logger;
    private DotController dotController;

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

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        dotController = new DotController(new Circle(5), INITIAL_WIDTH, INITIAL_HEIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(dotController.getPane(), root);

        // Add layout to scene.
        Scene scene = new Scene(stackPane, INITIAL_WIDTH, INITIAL_HEIGHT);
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
                dotController.setScreenWidth(newValue.intValue()));
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                dotController.setScreenHeight(newValue.intValue()));

        // Create oscillators.
        Oscillator xOscillator = new Oscillator(new Oscillator.Range(0, 180), Oscillator.Start.START, false,
                .0001) {
            @Override
            protected double step(double value) {
                return 100 * (Math.sin(value));
            }
        };

        Oscillator yOscillator = new Oscillator(new Oscillator.Range(0, 180), Oscillator.Start.CENTER, false,
                .0001) {
            @Override
            protected double step(double value) {
                return 100 * (Math.sin(value));
            }
        };

        // Create timeline.
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
                t -> dotController.setPosition(xOscillator.doStep(), yOscillator.doStep())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Set up window.
        primaryStage.setTitle(appStrings.getString("app_name"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
