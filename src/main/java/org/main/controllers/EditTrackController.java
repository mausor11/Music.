package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.main.Default;
import org.main.Main;
import org.main.StageHolder;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;

public class EditTrackController {
    @FXML
    StackPane imagePlace;
    @FXML
    ImageView coverImg;
    private boolean isCover = false;
    private File file;
    @FXML
    public void initialize() throws URISyntaxException {
        coverImg.setClip(Default.clipShape(100, 100, 10, 10));
        coverImg.setImage(new Image(Main.class.getResource("icons/Plus.png").toURI().toString()));
    }
    public void cancelTileOnMouseClicked() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(Default.trackEditor.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(Default.trackEditor.opacityProperty(), 0.0))
        );
        timeline.play();
        timeline.setOnFinished(event -> {
            Default.trackTrackView.getChildren().remove(1);
            file = null;
            isCover = false;
            try {
                coverImg.setImage(new Image(Main.class.getResource("icons/Plus.png").toURI().toString()));
                coverImg.setOpacity(0.0);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void imagePlaceOnMouseEntered() {
        if(!isCover) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.opacityProperty(), coverImg.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.opacityProperty(), 1.0))
            );
            timeline.play();
        }

    }

    public void imagePlaceOnMouseExited() {
        if(!isCover) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.opacityProperty(), coverImg.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.opacityProperty(), 0.0))
            );
            timeline.play();
        }

    }
    public void imagePlaceOnMouseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose cover image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(StageHolder.getPrimaryStage());
        if(file != null) {
            String url = file.getAbsolutePath().toString();
            coverImg.setImage(new Image(url));
            coverImg.setOpacity(1.0);
            isCover = true;
        }
    }
}
