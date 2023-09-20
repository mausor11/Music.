package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private final double animationTime = 150;
    private StackPane cell;
    private GridPane info;
    private StackPane background;
    private StackPane indexPane;
    private Track track;
    private Label title;
    private boolean isPlay = false;
    private boolean isPause = false;
    private boolean isOff = true;
    private Label index;
    private ImageView playIcon;
    private boolean isFirst = false;
    public enum Mode {OFF, PAUSE, PLAY}
    private double backgroundOpacity = 0;
    private double playIconOpacity = 0;
    private double indexOpacity = 0;
    private IntegerProperty actualModeIndex = new SimpleIntegerProperty(0);
    private int indexNum = -1;
    Mode actualMode = Mode.OFF;

    public TrackCell(Track track, int index) throws IOException {
        this.track = track;
        this.indexNum = index;
        prepareCell();
    }
    public Mode getMode() {
        return actualMode;
    }
    public void prepareCell() throws IOException {
        cell = new StackPane();
        cell.setPrefWidth(PREF_WIDTH);
        cell.setPrefHeight(PREF_HEIGHT);
        cell.setAlignment(Pos.CENTER_LEFT);

        background = new StackPane();
        background.setPrefWidth(PREF_WIDTH);
        background.setPrefHeight(PREF_HEIGHT);
        background.getStyleClass().add("trackCell");
        if(CurrentData.getDataInfo().actualTrackID() == track.getTrackID()) {                                           // when track is played or is paused
            if(CurrentData.getDataInfo().actualPauseTrackID() == track.getTrackID()) {                                  //when track is paused
                setMode(Mode.PAUSE);

            } else {                                                                                                    //when track is played
                setMode(Mode.PLAY);

            }
        } else {                                                                                                        //when track is off
            setMode(Mode.OFF);

        }
        info = new FXMLLoader(Main.class.getResource("fxml/TrackCell.fxml")).load();

        index = new Label(indexNum + " ");
        index.setMinWidth(30);
        index.setAlignment(Pos.CENTER_RIGHT);
        index.getStyleClass().add("indexTrackCell");

        if(actualMode != Mode.OFF) {
            if(actualMode == Mode.PAUSE) {
                playIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayIcon.png"))));
            } else {
                playIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayFocusGif.gif"))));
            }
        } else {
            playIcon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayIcon.png"))));
        }
        playIcon.setFitHeight(10);
        playIcon.setFitWidth(10);
        StackPane.setAlignment(playIcon, Pos.CENTER_RIGHT);

        indexPane = new StackPane();
        indexPane.getChildren().addAll(index, playIcon);

        title = new Label(track.getTrackName());
        title.setPadding(new Insets(0,0,0,20));
        title.setAlignment(Pos.CENTER_LEFT);

        Label artist = new Label(DataBase.getDataBase().getArtistName((int)track.getArtistID()));
        artist.getStyleClass().add("listArtistInfo");


        if(actualMode == Mode.PLAY) {
                title.getStyleClass().add("titleTrackCellFocused");
        } else {
            title.getStyleClass().add("titleTrackCell");
        }

        if(track.getFeatures() != null) {
            for(Integer feat : track.getFeatures()) {
                artist.setText(artist.getText() + Default.dot + DataBase.getDataBase().getArtistName(feat));
            }
        }
        Label duration = new Label(getDurationLook((int)track.getTrackDuration()));
        duration.getStyleClass().add("titleTrackCell");
        duration.setPadding(new Insets(0,14,0,0));

        duration.setOpacity(0.6);
        background.setOpacity(backgroundOpacity);
        playIcon.setOpacity(playIconOpacity);
        index.setOpacity(indexOpacity);

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
            if(actualMode == Mode.OFF) {                                                                                //animation for off trackCell
                Timeline enterAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(index.opacityProperty(), index.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.2)),
                        new KeyFrame(Duration.millis(animationTime), new KeyValue(playIcon.opacityProperty(), 0.6)),
                        new KeyFrame(Duration.millis(animationTime), new KeyValue(index.opacityProperty(), 0))
                );
                enterAnimation.play();
            }
            if(actualMode == Mode.PLAY){
                Timeline setPlayAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime/2), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.1)),
                        new KeyFrame(Duration.millis(animationTime/2), new KeyValue(playIcon.opacityProperty(), 0))
                );
                setPlayAnimation.play();
                setPlayAnimation.setOnFinished(changeIcon -> {
                        playIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PauseIcon.png"))));
                    Timeline setPlayAnimation2 = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),

                            new KeyFrame(Duration.millis(animationTime/2), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.2)),
                            new KeyFrame(Duration.millis(animationTime/2), new KeyValue(playIcon.opacityProperty(), 0.6))
                    );
                    setPlayAnimation2.play();
                });
            }
            if(actualMode == Mode.PAUSE) {
                Timeline setPauseAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.2))
                );
                setPauseAnimation.play();
            }
        });

        cell.setOnMouseExited(exitEvent -> {
            if(actualMode == Mode.OFF) {
                Timeline exitAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(index.opacityProperty(), index.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), 0.4)),
                        new KeyFrame(Duration.millis(animationTime), new KeyValue(playIcon.opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(animationTime), new KeyValue(index.opacityProperty(), 0.6))
                );
                exitAnimation.play();
            }
            if(actualMode == Mode.PAUSE) {
                Timeline setPauseAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), backgroundOpacity))
                );
                setPauseAnimation.play();
            }
            if(actualMode == Mode.PLAY) {
                if(!isFirst) {
                    Timeline setPlayAnimation = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),

                            new KeyFrame(Duration.millis(animationTime/2), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.1)),
                            new KeyFrame(Duration.millis(animationTime/2), new KeyValue(playIcon.opacityProperty(), 0))
                    );
                    setPlayAnimation.play();
                    setPlayAnimation.setOnFinished(changeIcon -> {
                        playIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayFocusGif.gif"))));
                        Timeline setPlayAnimation2 = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(playIcon.opacityProperty(), playIcon.getOpacity())),

                                new KeyFrame(Duration.millis(animationTime/2), new KeyValue(background.opacityProperty(), backgroundOpacity)),
                                new KeyFrame(Duration.millis(animationTime/2), new KeyValue(playIcon.opacityProperty(), 0.6))
                        );
                        setPlayAnimation2.play();
                    });
                } else {
                    isFirst = false;
                }
            }
        });
    }
    private void setMode(Mode mode) {
        switch(mode) {
            case OFF -> {isOff = true; isPause = false; isPlay = false; actualMode = Mode.OFF; backgroundOpacity = 0.4; playIconOpacity = 0; indexOpacity = 0.6;}
            case PAUSE -> {isOff = false; isPause = true; isPlay = false; actualMode = Mode.PAUSE; backgroundOpacity = 0.8; playIconOpacity = 0.6; indexOpacity = 0;}
            case PLAY -> {isOff = false; isPause = false; isPlay = true; actualMode = Mode.PLAY; backgroundOpacity = 0.8; playIconOpacity = 0.6; indexOpacity = 0;}
        }
    }
    public void setPlay() {
        setMode(Mode.PLAY);
        playIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PauseIcon.png"))));
        title.getStyleClass().clear();
        title.getStyleClass().add("titleTrackCellFocused");
        background.setOpacity(backgroundOpacity + 0.2);
        playIcon.setOpacity(playIconOpacity);
        index.setOpacity(indexOpacity);
    }
    public void setPause() {
        setMode(Mode.PAUSE);
        playIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayIcon.png"))));
        title.getStyleClass().clear();
        title.getStyleClass().add("titleTrackCell");
        background.setOpacity(backgroundOpacity + 0.2);
        playIcon.setOpacity(playIconOpacity);
        index.setOpacity(indexOpacity);
    }
    public void setOff() {
        setMode(Mode.OFF);
        playIcon.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/PlayIcon.png"))));
        title.getStyleClass().clear();
        title.getStyleClass().add("titleTrackCell");
        background.setOpacity(backgroundOpacity);
        playIcon.setOpacity(playIconOpacity);
        index.setOpacity(indexOpacity);
    }



    private String getInfo() {
        StringBuilder stringBuilder = new StringBuilder(isOff + " " + isPause + " " + isPlay + " " + backgroundOpacity + " " + playIconOpacity + " " + indexOpacity + " " + actualMode);
        return stringBuilder.toString();
    }


}
