package org.main.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.main.*;

import java.io.IOException;
import java.util.ArrayList;

public class TracklistSectionController {
    @FXML
    ImageView tracklistBackground;
    @FXML
    ImageView coverImg;
    @FXML
    Label titleLabel;
    @FXML
    Label infoLabel;
    @FXML
    StackPane tracklistSection;
    @FXML
    ScrollPane sectionPane;
    @FXML
    ListView<StackPane> trackView;
    @FXML
    GridPane mainGrid;
    private ScrollPane section;
    private ImageView background;
    private Image cover;
    private ArrayList<TrackCell> trackCells = new ArrayList<>();
    private TrackCell prevTrackCell = null;
    public static BooleanProperty isChange = new SimpleBooleanProperty(false);
    public void initialize() {
        isChange.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                if(Default.albumID.get() != 0) {
                    try {
                        if(Default.Type == 0) {
                            initializeSection(DataBase.getDataBase().getAlbumType(Default.albumID.get()), DataBase.getDataBase().getAlbumName(Default.albumID.get()), DataBase.getDataBase().getAlbumArtistName(Default.albumID.get()), DataBase.getDataBase().getAlbumFeaturesName(Default.albumID.get()), DataBase.getDataBase().getAlbumCover(Default.albumID.get()), DataBase.getDataBase().getAlbumTracklist(Default.albumID.get()));
                            Default.Type = -1;
                        } else if (Default.Type == 1) {
                            initializeSection("Playlist", DataBase.getDataBase().getPlaylistName(Default.albumID.get()), DataBase.getDataBase().getAlbumUserName(Default.albumID.get()), null, DataBase.getDataBase().getPlaylistCoverURL(Default.albumID.get()), DataBase.getDataBase().getPlaylistTracklist(Default.albumID.get()));
                            Default.Type = -1;
                        }
                        isChange.set(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });
        StageHolder.getPrimaryStage().widthProperty().addListener(((observableValue, number, t1) -> {
            resizeBackground(t1.doubleValue() - 317, StageHolder.getPrimaryStage().getHeight() - 240);
        }));
        StageHolder.getPrimaryStage().heightProperty().addListener(((observableValue1, number1, t11) -> {
            resizeBackground(StageHolder.getPrimaryStage().getWidth() - 317, t11.doubleValue() - 240);
        }));

    }
    private void initializeSection(String type, String title, String artist, ArrayList<String> features, String imgURL, ArrayList<Track> tracklist) throws IOException {
        titleLabel.setText(title);
        if(type != null) {
            infoLabel.setText(type);
        } else {
            infoLabel.setText("");
        }
        if(artist != null) {
            infoLabel.setText(infoLabel.getText() + Default.dot + artist);
        } else {
            infoLabel.setText(infoLabel.getText() + "");
        }
        if(features != null) {
            for(String feat : features) {
                infoLabel.setText(infoLabel.getText() + Default.dot + feat);
            }
        }else {
            infoLabel.setText(infoLabel.getText() + "");
        }
        prepareCover(imgURL);
        section = sectionPane;
        prepareBackground(imgURL);
        prepareTracklist(tracklist);
    }
    private void prepareCover(String imgURL) {
        cover = new Image(imgURL);
        coverImg.setImage(cover);
        coverImg.setClip(Default.clipShape(200, 200, 16, 16));
        coverImg.setViewport(Default.setViewportSquare(cover, coverImg));
    }
    private void prepareBackground(String imgURL) {
        cover = new Image(imgURL);
        background = new ImageView(cover);
        background.setFitHeight(StageHolder.getPrimaryStage().getHeight() - 240);
        background.setFitWidth(StageHolder.getPrimaryStage().getWidth() - 317);
        background.setEffect(new GaussianBlur(25));
        background.setClip(Default.clipShape(StageHolder.getPrimaryStage().getWidth() - 317, StageHolder.getPrimaryStage().getHeight() - 240, 16, 16));
        background.setViewport(Default.setViewportSquare(cover, background));
        background.setOpacity(0.5);
        tracklistSection.getChildren().clear();
        tracklistSection.getChildren().add(background);
        tracklistSection.getChildren().add(section);
    }
    private void resizeBackground(double width, double height) {
        tracklistBackground.setFitWidth(width);
        tracklistBackground.setFitHeight(height);
        background.setFitWidth(width);
        background.setFitHeight(height);
        background.setClip(Default.clipShape(width, height, 16, 16));
        background.setViewport(Default.setViewportSquare(cover, background));
    }
    private void prepareTracklist(ArrayList<Track> tracklist) throws IOException {
        if(!trackView.getItems().isEmpty()) {
            trackView.getItems().clear();
            trackCells.clear();
        }
        int index = 1;
        for(Track track : tracklist) {
            TrackCell trackCell = new TrackCell(track, index);
            if(trackCell.getTrack().getTrackID() == CurrentData.getDataInfo().actualTrackID()) {
                prevTrackCell = trackCell;
            }
            trackView.getItems().add(trackCell.getCell());
            trackCells.add(trackCell);
            index++;
        }


        mainGrid.getRowConstraints().get(1).setMinHeight(30*tracklist.size() + 3*(tracklist.size()+1));

        trackView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                switch(trackCells.get(trackView.getSelectionModel().getSelectedIndex()).getMode()) {
                    case PLAY -> {
                        if(prevTrackCell == trackCells.get(trackView.getSelectionModel().getSelectedIndex())) {
                            trackCells.get(trackView.getSelectionModel().getSelectedIndex()).setPause();
                            CurrentData.getDataInfo().setActualTrackCell(trackCells.get(trackView.getSelectionModel().getSelectedIndex()));
                            CurrentData.getDataInfo().setActualPauseTrackID(trackCells.get(trackView.getSelectionModel().getSelectedIndex()).getTrack().getTrackID());
                            CurrentData.getDataInfo().isPlay().set(false);
                        }
                    }
                    case PAUSE -> {
                        if(prevTrackCell == trackCells.get(trackView.getSelectionModel().getSelectedIndex())) {
                            trackCells.get(trackView.getSelectionModel().getSelectedIndex()).setPlay();
                            CurrentData.getDataInfo().setActualTrackCell(trackCells.get(trackView.getSelectionModel().getSelectedIndex()));
                            CurrentData.getDataInfo().setActualPauseTrackID(-1);
                            CurrentData.getDataInfo().isPlay().set(true);
                        }
                    }
                    case OFF -> {
                        if(prevTrackCell != null) {
                            prevTrackCell.setOff();
                        }
                        trackCells.get(trackView.getSelectionModel().getSelectedIndex()).setPlay();
                        CurrentData.getDataInfo().setActualTrackCell(trackCells.get(trackView.getSelectionModel().getSelectedIndex()));
                        CurrentData.getDataInfo().setActualTrackID(trackCells.get(trackView.getSelectionModel().getSelectedIndex()).getTrack().getTrackID());
                        CurrentData.getDataInfo().setActualPauseTrackID(-1);
                        CurrentData.getDataInfo().isPlay().set(true);
                    }

                }
                CurrentData.getDataInfo().setActualTrack(trackCells.get(trackView.getSelectionModel().getSelectedIndex()).getTrack());
                if(prevTrackCell == trackCells.get(trackView.getSelectionModel().getSelectedIndex())) {
                        CurrentData.getDataInfo().setIsNewTracKCover(false);
                } else {
                        CurrentData.getDataInfo().setIsNewTracKCover(true);
                }
                prevTrackCell = trackCells.get(trackView.getSelectionModel().getSelectedIndex());

            }

        });
    }

}
