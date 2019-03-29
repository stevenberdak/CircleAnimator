package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class FxController {

    @FXML
    private Label aboutLabel;

    @FXML
    private Label speedLabel, trailLabel, mainXMultiplierLabel, mainYMultiplierLabel;

    @FXML
    private Slider speedSlider, trailSlider, mainXMultiplierSlider, mainYMultiplierSlider;

    public FxController() {
    }

    public void setAboutLabelText(String labelText) {
        aboutLabel.setText(labelText);
    }

    public void setSpeedLabelText(String labelText) {
        speedLabel.setText(labelText);
    }

    public void setTrailLabelText(String labelText) {
        trailLabel.setText(labelText);
    }

    public void setXMpLabel(String labelText) {
        mainXMultiplierLabel.setText(labelText);
    }

    public void setYMpLabel(String labelText) {
        mainYMultiplierLabel.setText(labelText);
    }

    public Slider getSpeedSlider() {
        return speedSlider;
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
}
