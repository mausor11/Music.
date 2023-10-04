package org.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.main.Default;

public class AddTrackSectionController {
    @FXML
    StackPane trackImporter;
    @FXML
    Label trackTile;
    @FXML
    Label playlistTile;
    @FXML
    Label albumTile;
    @FXML
    public void initialize() {
        Default.trackTrackView = trackImporter;
    }
}
