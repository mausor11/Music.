package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class TrackCellImporter {
    private int PREF_WIDTH = 903;
    private int PREF_HEIGHT = 30;
    private final double animationTime = 150;
    private double backgroundOpacity = 0.5;
    private double indexOpacity = 0.6;
    private Track track;
    private int indexNum;
    private StackPane cell;
    private StackPane background;
    private GridPane info;
    private Label index;
    private Label title;
    private Label artist;
    private Label album;
    private Label genre;
    private String coverURL;
    private ArrayList<String> features = new ArrayList<>();
    private boolean isChosen;
    private StackPane indexPane;
    private boolean isFeatNull = false;
    public TrackCellImporter(Track track, int index) throws IOException {
        this.track = track;
        this.indexNum = index;
        isChosen = false;
        prepareCellForImporter();
    }
    public StackPane getCell() { return cell;}
    public void setIndex(int i) {
        index.setText(i + " ");
    }

    public boolean isChosen() {
        return isChosen;
    }
    private String formatFeatures() {
        if(isFeatNull) {
            return features.get(0);
        }
            StringBuilder stringBuilder = new StringBuilder();
            for(String feat: features) {
                stringBuilder.append(feat);
                stringBuilder.append("; ");
        }
        return stringBuilder.toString();

    }
    public String[] getData() {

        String[] data = {title.getText(), artist.getText(), album.getText(), genre.getText(), formatFeatures()};
        return data;
    }
    public long getDuration() {
        return track.getTrackDuration();
    }
    public String getCover() {
        return coverURL;
    }
    public ArrayList<String> getFeatures() {
        return features;
    }
    public String getInfo() {
        return "\n" +
                title.getText() + "\n"
                + artist.getText() + "\n"
                + album.getText() + "\n"
                + genre.getText() + "\n"
                + formatFeatures() + "\n"
                + coverURL;
    }
    private void prepareCellForImporter() throws IOException {
        cell = new StackPane();
        cell.setPrefWidth(PREF_WIDTH);
        cell.setPrefHeight(PREF_HEIGHT);
        cell.setAlignment(Pos.CENTER_LEFT);

        background = new StackPane();
        background.setPrefWidth(PREF_WIDTH);
        background.setPrefHeight(PREF_HEIGHT);
        background.getStyleClass().add("trackCellImporter");

        info = new FXMLLoader(Main.class.getResource("fxml/TrackCellImporter.fxml")).load();

        index = new Label(indexNum + " ");
        index.setMinWidth(30);
        index.setAlignment(Pos.CENTER_RIGHT);
        index.getStyleClass().add("indexTrackCell");


        indexPane = new StackPane();
        indexPane.getChildren().addAll(index);

        title = new Label(track.getTrackName());
        title.setPadding(new Insets(0,0,0,20));
        title.setAlignment(Pos.CENTER_LEFT);
        title.getStyleClass().add("titleTrackCell");
        if(track.getArtistID() != 0) {
            artist = new Label(DataBase.getDataBase().getArtistName((int)track.getArtistID()));
        } else {
            artist = new Label("artist");
        }
        if(track.getAlbumID() != 0) {
            album = new Label(DataBase.getDataBase().getAlbumName((int) track.getAlbumID()));
        } else {
            album = new Label("album");
        }
        if(track.getGenreID() != 0) {
            genre = new Label("genre");
        } else {
            genre = new Label("genre");
        }
        if(track.getFeatures() != null) {
            for(Integer feat : track.getFeatures()) {
                features.add(DataBase.getDataBase().getArtistName(feat));
            }
            isFeatNull = false;
        } else {
            features.add("features");
            isFeatNull = true;
        }

        artist.getStyleClass().add("listArtistInfo");
        album.getStyleClass().add("listArtistInfo");
        genre.getStyleClass().add("listArtistInfo");

        Label duration = new Label(getDurationLook((int)track.getTrackDuration()));
        duration.getStyleClass().add("titleTrackCell");
        duration.setPadding(new Insets(0,14,0,0));

        duration.setOpacity(0.6);
        background.setOpacity(backgroundOpacity);
        index.setOpacity(indexOpacity);

        info.addColumn(0, indexPane);
        info.addColumn(1, title);
        info.addColumn(2, artist);
        info.addColumn(3, album);
        info.addColumn(4, genre);
        info.addColumn(5, duration);
        info.getColumnConstraints().get(5).setHalignment(HPos.RIGHT);
        cell.getChildren().add(background);
        cell.getChildren().add(info);
        addListener();
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

                    new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), backgroundOpacity + 0.2))
            );
            if(!isChosen) {
                enterAnimation.play();
            }

            cell.setOnMouseExited(exitEvent -> {
                Timeline exitAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(background.opacityProperty(), background.getOpacity())),

                        new KeyFrame(Duration.millis(animationTime), new KeyValue(background.opacityProperty(), 0.5))
                );
                if(!isChosen) {
                    exitAnimation.play();
                }
            });
        });
        cell.setOnMouseClicked(clickEvent -> {
            if(!isChosen) {
                Default.chosenTracks.set(Default.chosenTracks.get() + 1);
                Default.chosenCells.add(this);
                isChosen = true;
                background.setOpacity(background.getOpacity() + 0.2);
            } else {
                isChosen = false;
                Default.chosenTracks.set(Default.chosenTracks.get() - 1);
                Default.chosenCells.remove(this);
                background.setOpacity(background.getOpacity() - 0.2);
            }

        });
    }
    public void setTitle(String t) {
        title.setText(t);
    }
    public void setArtist(String a) {
        artist.setText(a);
    }
    public void setAlbum(String A) {
        album.setText(A);
    }
    public void setGenre(String g) {
        genre.setText(g);
    }
    public void setCover(String c) {
        coverURL = c;
    }
    public void setFeatures(String f) {
            features = new ArrayList<>();
            String[] elems = f.split("; ");
            for(String elem : elems) {
                features.add(elem);
            }
            isFeatNull = false;
    }
    public void setChosen() {
        if(!isChosen) {
            Default.chosenTracks.set(Default.chosenTracks.get() + 1);
            Default.chosenCells.add(this);
            isChosen = true;
            background.setOpacity(background.getOpacity() + 0.2);
        }
    }
    public void setNotChosen() {
        if(isChosen) {
            isChosen = false;
            Default.chosenTracks.set(Default.chosenTracks.get() - 1);
            Default.chosenCells.remove(this);
            background.setOpacity(background.getOpacity() - 0.2);
        }
    }

}
