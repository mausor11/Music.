package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.main.Default;
import org.main.Main;
import org.main.StageHolder;

import java.io.File;
import java.net.URISyntaxException;

public class EditTrackController {
    @FXML
    StackPane imagePlace;
    @FXML
    ImageView coverImg;
    @FXML
    TextField titleText;
    @FXML
    TextField albumText;
    @FXML
    TextField artistText;
    @FXML
    TextField featText;
    @FXML
    TextField genreText;
    private boolean isCover = false;
    private File file;
    public static StringProperty TitleT = new SimpleStringProperty();
    public static StringProperty AlbumT = new SimpleStringProperty();
    public static StringProperty ArtistT = new SimpleStringProperty();
    public static StringProperty FeatT = new SimpleStringProperty();
    public static StringProperty GenreT = new SimpleStringProperty();
    @FXML
    public void initialize() throws URISyntaxException {
        coverImg.setClip(Default.clipShape(100, 100, 10, 10));
        coverImg.setImage(new Image(Main.class.getResource("icons/Plus.png").toURI().toString()));
        setListeners();
    }
    private void setListeners() {
        TitleT.addListener(((observableValue, s, t1) -> {
            titleText.setText(t1);
        }));
        AlbumT.addListener(((observableValue, s, t1) -> {
            albumText.setText(t1);
        }));
        ArtistT.addListener(((observableValue, s, t1) -> {
            artistText.setText(t1);
        }));
        TitleT.addListener(((observableValue, s, t1) -> {
            titleText.setText(t1);
        }));
        FeatT.addListener(((observableValue, s, t1) -> {
            featText.setText(t1);
        }));
        GenreT.addListener(((observableValue, s, t1) -> {
            genreText.setText(t1);
        }));

    }
    public void cancelTileOnMouseClicked() {
        Default.containerBox.setDisable(false);
        GaussianBlur gaussianBlur = new GaussianBlur(7.7);
        Default.containerBox.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(Default.trackEditor.opacityProperty(), 1.0)),
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(Default.trackEditor.opacityProperty(), 0.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(gaussianBlur.radiusProperty(), 0.0))
        );
        timeline.play();
        timeline.setOnFinished(event -> {
            Default.mainPane.getChildren().remove(1);
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

    public void saveTileOnMouseClicked() {
        Default.chosenCells.get(0).setTitle(titleText.getText());
        Default.chosenCells.get(0).setArtist(artistText.getText());
        Default.chosenCells.get(0).setAlbum(albumText.getText());
        Default.chosenCells.get(0).setGenre(genreText.getText());

        Default.containerBox.setDisable(false);
        GaussianBlur gaussianBlur = new GaussianBlur(7.7);
        Default.containerBox.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(Default.trackEditor.opacityProperty(), 1.0)),
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(Default.trackEditor.opacityProperty(), 0.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(gaussianBlur.radiusProperty(), 0.0))
        );
        timeline.play();
        timeline.setOnFinished(event -> {
            Default.mainPane.getChildren().remove(1);
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
}
