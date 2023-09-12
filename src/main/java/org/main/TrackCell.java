package org.main;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;

public class TrackCell {
    private int PREF_WIDTH = 903;
    private int PREF_HEIGHT = 30;
    private StackPane bg;
    private GridPane background;
    private HBox infoBox = new HBox();
    private Track track;

    public TrackCell(Track track) throws IOException {
        this.track = track;
        prepareCell();
    }
    private void prepareCell() throws IOException {
        bg = new StackPane();
        bg.setPrefWidth(PREF_WIDTH);
        bg.setPrefHeight(PREF_HEIGHT);
        bg.getStyleClass().add("trackCell");
        bg.setAlignment(Pos.CENTER_LEFT);
        background = new FXMLLoader(Main.class.getResource("fxml/TrackCell.fxml")).load();
        Label index = new Label(track.getTrackIndex() + "");
        index.setMinWidth(30);
        index.setAlignment(Pos.CENTER_RIGHT);
        Label title = new Label(track.getTrackName());
        title.setPadding(new Insets(0,0,0,20));
        title.setAlignment(Pos.CENTER_LEFT);
        Label artist = new Label(DataBase.getDataBase().getArtistName((int)track.getArtistID()));
        artist.getStyleClass().add("listArtistInfo");
        index.getStyleClass().add("indexTrackCell");
        title.getStyleClass().add("titleTrackCell");
        if(track.getFeatures() != null) {
            for(Integer feat : track.getFeatures()) {
                artist.setText(artist.getText() + Default.dot + DataBase.getDataBase().getArtistName(feat));
            }
        }
        Label duration = new Label(getDurationLook((int)track.getTrackDuration()));
        duration.getStyleClass().add("titleTrackCell");
        duration.setPadding(new Insets(0,14,0,0));
        duration.setOpacity(0.6);
        background.addColumn(0, index);
        background.addColumn(1, title);
        background.addColumn(2, artist);
        background.addColumn(3, duration);
        background.getColumnConstraints().get(3).setHalignment(HPos.RIGHT);
        bg.getChildren().add(background);
    }
    public StackPane getCell() {
        return bg;
    }
    private String getDurationLook(int duration) {
        int minutes = duration/60;
        int seconds = duration - (minutes*60);
        if(seconds < 10) {
            return minutes + ":0" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }
}
