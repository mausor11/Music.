package org.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.main.Default;

public class AddTrackSectionController {
    @FXML
    StackPane trackImporter;
    @FXML
    public void initialize() {
        Default.trackTrackView = trackImporter;
    }
}
