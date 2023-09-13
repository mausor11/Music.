package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class TrackCell {
    private int PREF_WIDTH = 903;
    private int PREF_HEIGHT = 30;
    private double animationTime = 150;
    private StackPane cell;
    private GridPane info;
    private StackPane background;
    private Track track;
    private boolean isFocus = false;
    private Label index;
    private ImageView playIcon;

    public TrackCell(Track track) throws IOException {
        this.track = track;
        prepareCell();
    }
    private void prepareCell() throws IOException {
        cell = new StackPane();
        cell.setPrefWidth(PREF_WIDTH);
        cell.setPrefHeight(PREF_HEIGHT);
        cell.setAlignment(Pos.CENTER_LEFT);

        background = new StackPane();
        background.setPrefWidth(PREF_WIDTH);
        background.setPrefHeight(PREF_HEIGHT);
        background.getStyleClass().add("trackCell");
        background.setOpacity(0.4);
        info = new FXMLLoader(Main.class.getResource("fxml/TrackCell.fxml")).load();

        index = new Label(track.getTrackIndex() + " ");
        index.setMinWidth(30);
        index.setAlignment(Pos.CENTER_RIGHT);


        playIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayIcon.png"))));
        playIcon.setOpacity(0);
        playIcon.setFitHeight(10);
        playIcon.setFitWidth(10);
        StackPane.setAlignment(playIcon, Pos.CENTER_RIGHT);

        StackPane indexPane = new StackPane();
        indexPane.getChildren().addAll(index, playIcon);

        Label title = new Label(track.getTrackName());
        title.setPadding(new Insets(0,0,0,20));
        title.setAlignment(Pos.CENTER_LEFT);
        Label artist = new Label(DataBase.getDataBase().getArtistName((int)track.getArtistID()));
        artist.getStyleClass().add("listArtistInfo");
        index.getStyleClass().add("indexTrackCell");
        index.setOpacity(0.6);

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
        info.addColumn(0, indexPane);
        info.addColumn(1, title);
        info.addColumn(2, artist);
        info.addColumn(3, duration);
        info.getColumnConstraints().get(3).setHalignment(HPos.RIGHT);
        cell.getChildren().add(background);
        cell.getChildren().add(info);
        addListener();
    }
    public StackPane getCell() {
        return cell;
    }
    public Track getTrack() {
        return track;
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
    private void addListener() {
        cell.setOnMouseEntered(enterEvent -> {
            Timeline enterAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(index.opacityProperty(), index.getOpacity())),

                    new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), 0.8)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(playIcon.opacityProperty(), 0.6)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(index.opacityProperty(), 0))
            );
            enterAnimation.play();
        });
        cell.setOnMouseExited(exitEvent -> {
            Timeline exitAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(index.opacityProperty(), index.getOpacity())),

                    new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), 0.4)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(playIcon.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(animationTime), new KeyValue(index.opacityProperty(), 0.6))
            );
            exitAnimation.play();
        });

    }

}
