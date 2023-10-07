package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import org.main.Default;
import org.main.StageHolder;
import org.main.Track;
import org.main.TrackCell;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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
    private ArrayList<Rectangle> underlines;
    private boolean isFirst = true;
    public static IntegerProperty actMode = new SimpleIntegerProperty(-1);
    public static IntegerProperty prevMode = new SimpleIntegerProperty(-1);
    @FXML
    public void initialize() {
        Default.trackTrackView = trackImporter;
        setUp();
        setMode();
        setOnClick();
    }
    private void setUp() {
        underlines = new ArrayList<>();
        underlines.add(trackUnderline);
        underlines.add(playlistUnderline);
        underlines.add(albumUnderline);
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

    public void addFolder() throws IOException {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Choose folder");
        File file = fileChooser.showDialog(StageHolder.getPrimaryStage());
        if(file != null) {
            String url = file.getAbsolutePath();
            getAllFiles(url);
        }

    }
    private void getAllFiles(String folderURL) throws IOException {
        File directory = new File(folderURL);
        File[] files = directory.listFiles(mp3Files());
        for(File file : files) {
            Media song = new Media(file.toURI().toString());
            TrackCell trackCell = new TrackCell(new Track(file.getName().replace(".mp3", ""), (int)song.getDuration().toSeconds()), 0);
            trackListView.getItems().add(trackCell.getCell());
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
}
