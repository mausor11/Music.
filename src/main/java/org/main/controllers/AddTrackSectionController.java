package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import org.main.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

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
    Rectangle trackUnderline;
    @FXML
    Rectangle playlistUnderline;
    @FXML
    Rectangle albumUnderline;
    @FXML
    ListView<StackPane> trackListView;
    @FXML
    Label addFolder;
    @FXML
    StackPane buttonArea;
    @FXML
    StackPane sortBy;
    @FXML
    GridPane addTracksInformation;
    @FXML
    GridPane editTrackPane;
    @FXML
    Label chosenText;
    @FXML
    StackPane editPane;
    private ArrayList<Rectangle> underlines;
    private boolean isFirst = true;
    private String actFileName = "Add folder";
    public static IntegerProperty actMode = new SimpleIntegerProperty(-1);
    public static IntegerProperty prevMode = new SimpleIntegerProperty(-1);
    @FXML
    public void initialize() {
        Default.trackTrackView = trackImporter;
        Default.editTrackPane = editTrackPane;
        Default.chosenTracks = new SimpleLongProperty(0);
        if(trackListView.getItems().isEmpty()) {
            addTracksInformation.setDisable(false);
            addTracksInformation.setOpacity(1);
        }

        setUp();
        setMode();
        setOnClick();
        setAddFolder();
        setEditTrackPane();
    }
    private void setUp() {
        underlines = new ArrayList<>();
        underlines.add(trackUnderline);
        underlines.add(playlistUnderline);
        underlines.add(albumUnderline);
    }
    private void setEditTrackPane() {
        Default.chosenTracks.addListener(((observableValue, number, t1) -> {
                if(t1.intValue() > 0) {
                    Default.editTrackPane.setOpacity(1);
                    chosenText.setText(t1.intValue() + " song chosen");
                    if(t1.intValue() > 1) {
                        editPane.setOpacity(0);
                        editPane.setDisable(true);
                    } else {
                        editPane.setOpacity(1);
                        editPane.setDisable(false);
                    }
                } else {
                    Default.editTrackPane.setOpacity(0);
                }
        }));
    }
    private void setAddFolder() {
        buttonArea.setOnMouseEntered(enterEvent -> {
            addFolder.setText("Add folder");

            buttonArea.setOnMouseExited(exitEvent -> {
                addFolder.setText(actFileName);
            });
        });
    }
    private void setMode() {
        actMode.addListener(((observableValue, number, t1) -> {
            if(isFirst) {
                switch(t1.intValue()) {
                    case 0 -> {
                        trackUnderline.setScaleX(1);
                        playlistUnderline.setScaleX(0);
                        albumUnderline.setScaleX(0);
                    }
                    case 1 -> {
                        trackUnderline.setScaleX(0);
                        playlistUnderline.setScaleX(1);
                        albumUnderline.setScaleX(0);
                    }
                    case 2 -> {
                        trackUnderline.setScaleX(0);
                        playlistUnderline.setScaleX(0);
                        albumUnderline.setScaleX(1);
                    }
                }
                isFirst = false;
            } else {
                underlineAnimation(underlines.get(prevMode.get()), underlines.get(actMode.get()));
            }

            prevMode.set(t1.intValue());

        }));
    }
    private void setOnClick() {
        trackTile.setOnMouseClicked(event -> {
            actMode.set(0);
        });
        playlistTile.setOnMouseClicked(event -> {
            actMode.set(1);
        });
        albumTile.setOnMouseClicked(event -> {
            actMode.set(2);
        });

    }




    private void underlineAnimation(Rectangle prevUnderline, Rectangle actUnderline) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(prevUnderline.scaleXProperty(), 1)),
                new KeyFrame(Duration.ZERO, new KeyValue(actUnderline.scaleXProperty(), 0)),

                new KeyFrame(Duration.millis(100), new KeyValue(prevUnderline.scaleXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(actUnderline.scaleXProperty(), 1))
        );
        timeline.play();
    }

    public void addFolder() {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Choose folder");
        File file = fileChooser.showDialog(StageHolder.getPrimaryStage());
        if(file != null) {
            String url = file.getAbsolutePath();
            addFolder.setText(file.getName());
            actFileName = file.getName();
            sortByAnimation();
            getAllFiles(url);
        }
        Default.chosenCells = new ArrayList<>();
        Default.chosenTracks.set(0);

    }
    private void getAllFiles(String folderURL) {
        if(!trackListView.getItems().isEmpty()) {
            trackListView.getItems().clear();
        }
        File directory = new File(folderURL);
        File[] files = directory.listFiles(mp3Files());
        int index= 1;
        for(File file : files) {
            System.out.println(file.getName());
            Media song = new Media(file.toURI().toString());
            addToTrackList(song, index);
            addTracksInformation.setDisable(true);
            addTracksInformation.setOpacity(0);
            index++;
        }
    }
    private FilenameFilter mp3Files() {
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if(lowercaseName.endsWith(".mp3")) {
                    return true;
                } else {
                    return false;

                }
            }
        };
        return filenameFilter;
    }
    private void addToTrackList(Media song, int index) {
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Track track = new Track((String) song.getMetadata().get("title"), (String) song.getMetadata().get("artist"), "Genre", (long) song.getDuration().toSeconds());
                try {
                    TrackCellImporter trackCell = new TrackCellImporter(track, index);
                    trackListView.getItems().add(trackCell.getCell());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void sortByAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sortBy.opacityProperty(), sortBy.getOpacity())),
                new KeyFrame(Duration.millis(150), new KeyValue(sortBy.opacityProperty(), 1))
        );
        timeline.play();
    }

    public void editOnMouseClicked() {
        EditTrackController.TitleT.set(null);
        EditTrackController.ArtistT.set(null);
        EditTrackController.AlbumT.set(null);
        EditTrackController.GenreT.set(null);
        Default.mainPane.getChildren().add(Default.trackEditor);
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        Default.containerBox.setEffect(gaussianBlur);
        Default.containerBox.setDisable(true);
        String[] data = Default.chosenCells.get(0).getData();
        EditTrackController.TitleT.set(data[0]);
        EditTrackController.ArtistT.set(data[1]);
        EditTrackController.AlbumT.set(data[2]);
        EditTrackController.GenreT.set(data[3]);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(Default.trackEditor.opacityProperty(), 0.0)),
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(Default.trackEditor.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(250), new KeyValue(gaussianBlur.radiusProperty(), 20.0))
        );
        timeline.play();
    }

    public void clearEverything() {
        Default.chosenTracks.set(0);
        for(TrackCellImporter cells : Default.chosenCells) {
            cells.setIsNotChosen();
        }
        Default.chosenCells = new ArrayList<>();
    }
}
