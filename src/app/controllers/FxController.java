package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class FxController {

    @FXML
    private Label aboutLabel;

    @FXML
    private Label speedLabel, trailLabel, xMpLabel, yMpLabel;

    @FXML
    private Slider speedSlider, trailSlider, xMpSlider, yMpSlider;

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
        xMpLabel.setText(labelText);
    }

    public void setYMpLabel(String labelText) {
        yMpLabel.setText(labelText);
    }

    public Slider getSpeedSlider() {
        return speedSlider;
    }

    public Slider getTrailSlider() {
        return trailSlider;
    }

    public Slider getXMpSlider() {
        return xMpSlider;
    }

    public Slider getYMpSlider() {
        return yMpSlider;
    }
}
