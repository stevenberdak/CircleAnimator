package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class FxController implements Initializable {

    @FXML
    private Label speedLabel, satelliteSpeedLabel, satelliteDistanceLabel, speedMultiplierLabel, trailLabel,
            mainXMultiplierLabel,
            mainYMultiplierLabel;

    @FXML
    private Slider speedSlider, satelliteSpeedSlider, satelliteDistanceSlider, trailSlider, mainXMultiplierSlider,
            mainYMultiplierSlider, speedMultiplierSlider;

    public FxController() {
    }

    public Slider getSpeedSlider() {
        return speedSlider;
    }

    public Slider getSatelliteSpeedSlider() {
        return satelliteSpeedSlider;
    }

    public Slider getSatelliteDistanceSlider() {
        return satelliteDistanceSlider;
    }

    public Slider getSpeedMultiplierSlider() {
        return speedMultiplierSlider;
    }

    public Slider getTrailSlider() {
        return trailSlider;
    }

    public Slider getXMultiplaierSlider() {
        return mainXMultiplierSlider;
    }

    public Slider getYMultiplierSlider() {
        return mainYMultiplierSlider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        speedLabel.setText(resources.getString("speed_label"));
        satelliteSpeedLabel.setText(resources.getString("satellite_speed_label"));
        satelliteDistanceLabel.setText(resources.getString("satellite_distance_label"));
        trailLabel.setText(resources.getString("trail_label"));
        mainXMultiplierLabel.setText(resources.getString("main_x_mp_label"));
        mainYMultiplierLabel.setText(resources.getString("main_y_mp_label"));
        speedMultiplierLabel.setText(resources.getString("speed_multiplier_label"));
    }
}
