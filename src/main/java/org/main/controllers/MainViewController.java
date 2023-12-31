package org.main.controllers;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.main.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainViewController {
    @FXML
    StackPane mainPane;
    @FXML
    VBox containerBox;
    @FXML
    StackPane mainPlayerInfo;
    @FXML
    StackPane librarySpace;
    @FXML
    StackPane mainSpace;
    @FXML
    Label titleLabel;
    @FXML
    Label artistLabel;
    @FXML
    GridPane menu;
    @FXML
    StackPane homeTile;
    @FXML
    StackPane playlistTile;
    @FXML
    StackPane albumTile;
    @FXML
    StackPane favouriteTile;
    @FXML
    GridPane searchTile;
    @FXML
    StackPane libraryTile;
    @FXML
    StackPane importerTile;
    @FXML
    Rectangle homeCover;
    @FXML
    Rectangle playlistCover;
    @FXML
    Rectangle albumCover;
    @FXML
    Rectangle favouriteCover;
    @FXML
    Rectangle libraryCover;
    @FXML
    Rectangle importerCover;
    @FXML
    ImageView playButton;
    @FXML
    ImageView playButtonEffect;
    @FXML
    ImageView undoButton;
    @FXML
    ImageView undoButtonEffect;
    @FXML
    ImageView nextButton;
    @FXML
    ImageView nextButtonEffect;
    @FXML
    ImageView shuffleButtonEffect;
    @FXML
    ImageView shuffleButton;
    @FXML
    ImageView repeatButtonEffect;
    @FXML
    ImageView repeatButton;
    @FXML
    ImageView maxButtonEffect;
    @FXML
    ImageView maxButton;
    @FXML
    Slider volumeBar;
    @FXML
    Rectangle volume;
    @FXML
    Rectangle volumeEffect;
    @FXML
    ImageView backgroundImage;
    @FXML
    ImageView coverIcon;
    @FXML
    HBox artistBox;
    @FXML
    HBox contentBox;
/**<p>0 - Home Tile</p>
 * <p>1 - Playlists Tile</p>
 * <p>2 - Albums Tile</p>
 * <p>3 - Favourite Tile</p>
 * <p>4 - Settings Tile</p>
 * <p>5 - Library Tile</p>
 * <p>6 - Importer Tile</p>**/
    private int actualTile = 0;
    private int prevTile = 0;
    private final Rectangle searchCover = new Rectangle();
    private final ArrayList<Rectangle> tileCover = new ArrayList<>();
    private boolean isSearchOn = false;
    private boolean isShuffle = false;
    Default.StatusRepeat repeatStatus = Default.StatusRepeat.NONE;
    private int playPause = 0;
    public static BooleanProperty isImporter = new SimpleBooleanProperty(false);
    @FXML
    public Label featLabel;

    @FXML
    public void initialize() throws IOException {
        Default.mainPane = mainPane;
        Default.containerBox = containerBox;
        Default.mainSpace = mainSpace;
        librarySpace.getChildren().add(new FXMLLoader(Main.class.getResource("fxml/LibrarySection.fxml")).load());
        mainSpace.getChildren().add(Default.homeView);
        prepareCovers();
        setUpVolumeBar();
        setNewCoverArt();
        setPlay();
        setUpFocusedListener();
        setImporter();
    }

    private void setNewCoverArt() {
        CurrentData.getDataInfo().isNewTrackCover().addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                if(CurrentData.getDataInfo().actualTrack() != null) {
                    Timeline changeCover1 = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(backgroundImage.opacityProperty(), backgroundImage.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(coverIcon.opacityProperty(), coverIcon.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(titleLabel.opacityProperty(), titleLabel.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(artistLabel.opacityProperty(), artistLabel.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(featLabel.opacityProperty(), featLabel.getOpacity())),

                            new KeyFrame(Duration.millis(200), new KeyValue(backgroundImage.opacityProperty(), 0)),
                            new KeyFrame(Duration.millis(200), new KeyValue(coverIcon.opacityProperty(), 0)),
                            new KeyFrame(Duration.millis(200), new KeyValue(titleLabel.opacityProperty(), 0)),
                            new KeyFrame(Duration.millis(200), new KeyValue(artistLabel.opacityProperty(), 0)),
                            new KeyFrame(Duration.millis(200), new KeyValue(featLabel.opacityProperty(), 0))
                    );
                    changeCover1.play();
                    changeCover1.setOnFinished(e -> {
                        newCoverImage(new Image(CurrentData.getDataInfo().actualTrack().getCoverLink()), CurrentData.getDataInfo().actualTrack().getTrackName(), DataBase.getDataBase().getArtistName((int)CurrentData.getDataInfo().actualTrack().getArtistID()), DataBase.getDataBase().getTrackFeatNames((int)CurrentData.getDataInfo().actualTrackID()));
                        CurrentData.getDataInfo().setIsNewTracKCover(false);
                        Timeline changeCover2 = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(backgroundImage.opacityProperty(), backgroundImage.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(coverIcon.opacityProperty(), coverIcon.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(titleLabel.opacityProperty(), titleLabel.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(artistLabel.opacityProperty(), artistLabel.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(featLabel.opacityProperty(), featLabel.getOpacity())),

                                new KeyFrame(Duration.millis(100), new KeyValue(backgroundImage.opacityProperty(), 0.7)),
                                new KeyFrame(Duration.millis(160), new KeyValue(coverIcon.opacityProperty(), 1)),
                                new KeyFrame(Duration.millis(180), new KeyValue(titleLabel.opacityProperty(), 1)),
                                new KeyFrame(Duration.millis(200), new KeyValue(artistLabel.opacityProperty(), 1)),
                                new KeyFrame(Duration.millis(220), new KeyValue(featLabel.opacityProperty(), 1))
                        );
                        changeCover2.play();
                    });

                }

            }
        }));
    }
    private void setUpFocusedListener() {
        Default.tileFocused.addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                setLibraryTile();
            }
        });
    }

    private void setUpVolumeBar() {
        volumeBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume.setWidth((newValue.doubleValue()/100) * 160);
            volumeEffect.setWidth((newValue.doubleValue()/100) * 160);
            volumeEffect.setEffect(new GaussianBlur((newValue.doubleValue()/100)*15.0));
        });

    }
    private void prepareCovers() {
        tileCover.add(homeCover);
        tileCover.add(playlistCover);
        tileCover.add(albumCover);
        tileCover.add(favouriteCover);
        tileCover.add(searchCover);
        tileCover.add(libraryCover);
        tileCover.add(importerCover);
    }
    public void setLibraryTile() {
        if(actualTile != 5) {
            actualTile = 5;
            makeTileAnimation();
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            HomeSectionController.isBack.set(true);
            mainSpace.getChildren().add(Default.tracklistView);
            prevTile = actualTile;
        }
    }
    public void setHomeTile() {
        if(actualTile != 0) {
            actualTile = 0;
            makeTileAnimation();
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            HomeSectionController.isBack.set(true);
            mainSpace.getChildren().add(Default.homeView);
            prevTile = actualTile;
            Default.libraryFocused.set(false);
            Default.tileFocused.set(true);
        }
    }
    private void setImporter() {
        isImporter.addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                actualTile = 6;
                makeTileAnimation();
                isImporter.set(false);
                Default.libraryFocused.set(false);
                Default.tileFocused.set(true);
            }
        }));
    }
    public void setPlaylistsTile() {
        if(actualTile != 1) {
            actualTile = 1;
            makeTileAnimation();
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            PlaylistSectionController.isBack.set(true);
            mainSpace.getChildren().add(Default.playlistView);
            prevTile = actualTile;
            Default.libraryFocused.set(false);
            Default.tileFocused.set(true);
        }

    }
    public void setAlbumsTile() {
        if(actualTile != 2) {
            actualTile = 2;
            makeTileAnimation();
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            AlbumSectionController.isBack.set(true);
            mainSpace.getChildren().add(Default.albumsView);
            prevTile = actualTile;
            Default.libraryFocused.set(false);
            Default.tileFocused.set(true);
        }
    }
    public void setFavouriteTile() {
        if(actualTile != 3) {
            actualTile = 3;
            makeTileAnimation();
            if(!mainSpace.getChildren().isEmpty()) {
                mainSpace.getChildren().clear();
            }
            AlbumSectionController.isBack.set(true);
            Default.albumID.set(-1);
            TracklistSectionController.isChange.set(true);
            mainSpace.getChildren().add(Default.tracklistView);

            prevTile = actualTile;
            Default.libraryFocused.set(false);
            Default.tileFocused.set(true);
        }
    }
    public void setSearchTile() {
        if(!isSearchOn) {
            double change = menu.getColumnConstraints().get(7).getMaxWidth() - menu.getColumnConstraints().get(7).getPrefWidth();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(7).prefWidthProperty(), menu.getColumnConstraints().get(7).getPrefWidth())),
                    new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(6).prefWidthProperty(), menu.getColumnConstraints().get(6).getPrefWidth())),
                    new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(7).prefWidthProperty(), 200)),
                    new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(6).prefWidthProperty(), menu.getColumnConstraints().get(6).getPrefWidth() - change))
            );
            timeline.play();
            searchTile.getStyleClass().remove("tileMenu");
            searchTile.getStyleClass().add("searchTileActive");
            isSearchOn = true;
            actualTile = 4;
            makeTileAnimation();
            prevTile = actualTile;
            Default.libraryFocused.set(false);
            Default.tileFocused.set(true);
        }

    }

    private void makeTileAnimation() {
            if(prevTile < actualTile) {
                StackPane.setAlignment(tileCover.get(prevTile), Pos.CENTER_RIGHT);
                StackPane.setAlignment(tileCover.get(actualTile), Pos.CENTER_LEFT);
            } else {
                StackPane.setAlignment(tileCover.get(prevTile), Pos.CENTER_LEFT);
                StackPane.setAlignment(tileCover.get(actualTile), Pos.CENTER_RIGHT);
            }
            Timeline timeline = new Timeline();
            if(prevTile != 4) {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(tileCover.get(prevTile).widthProperty(), tileCover.get(prevTile).getWidth(), Interpolator.EASE_IN)),
                        new KeyFrame(Duration.millis(100), new KeyValue(tileCover.get(prevTile).widthProperty(), 0, Interpolator.EASE_IN)));
            } else {
                searchTile.getStyleClass().remove("searchTileActive");
                searchTile.getStyleClass().add("tileMenu");
                isSearchOn = false;
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(7).prefWidthProperty(), menu.getColumnConstraints().get(7).getPrefWidth())),
                        new KeyFrame(Duration.ZERO, new KeyValue(menu.getColumnConstraints().get(6).prefWidthProperty(), menu.getColumnConstraints().get(6).getPrefWidth())),
                        new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(7).prefWidthProperty(), 100)),
                        new KeyFrame(Duration.millis(100), new KeyValue(menu.getColumnConstraints().get(6).prefWidthProperty(), menu.getColumnConstraints().get(6).getPrefWidth() +100))
                );
            }
            timeline.play();
            timeline.setOnFinished(event ->  {
                Timeline timeline1 = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(tileCover.get(actualTile).widthProperty(), 0, Interpolator.EASE_IN)),
                        new KeyFrame(Duration.millis(100), new KeyValue(tileCover.get(actualTile).widthProperty(), 130, Interpolator.EASE_IN))
                );
                timeline1.play();
                timeline1.setOnFinished(event2 -> {
                });
            });
    }
    public void playEffect() {
        Default.blurEffect(playButton, playButtonEffect);
    }
    public void setPlay() {
        CurrentData.getDataInfo().isPlay().addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                if(playPause == 0) {
                    playPause = setOnPlay("PauseIcon", playPause);
                }
            } else {
                if(playPause == 1) {
                    playPause = setOnPlay("PlayIcon", playPause);
                }
            }
        }));
        playButton.setOnMouseClicked(event -> {
            if(CurrentData.getDataInfo().actualTrackCell() != null) {
                if(playPause == 0) {
                    CurrentData.getDataInfo().isPlay().set(true);
                    CurrentData.getDataInfo().setActualPauseTrackID(-1);
                    CurrentData.getDataInfo().actualTrackCell().setPlay();
                } else {
                    CurrentData.getDataInfo().isPlay().set(false);
                    CurrentData.getDataInfo().setActualPauseTrackID(CurrentData.getDataInfo().actualTrackCell().getTrack().getTrackID());
                    CurrentData.getDataInfo().actualTrackCell().setPause();

                }
            }

        });
    }
    public void nextEffect() {
        Default.blurEffect(nextButton, nextButtonEffect);
    }
    public void undoEffect() {
        Default.blurEffect(undoButton, undoButtonEffect);
    }
    private int setOnPlay(String icon, int now) {
        playButton.setRotate(0);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), playButton);
        rotateTransition.setInterpolator(Interpolator.EASE_OUT);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        playButton.setEffect(gaussianBlur);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 20))
        );
        timeline1.play();
        timeline1.setOnFinished(e -> {
                    playButton.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/" + icon + ".png"))));
                    playButtonEffect.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/" + icon + ".png"))));
                    Timeline timeline2 = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 20)),
                            new KeyFrame(Duration.millis(75), new KeyValue(gaussianBlur.radiusProperty(), 0))
                    );
                    timeline2.play();
                }
        );
        if(now == 0) {
            return 1;
        } else {
            return 0;
        }
    }
    public void shuffleEffect() {
        if(!isShuffle) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(shuffleButtonEffect.opacityProperty(), shuffleButtonEffect.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(shuffleButtonEffect.opacityProperty(), 1))
            );
            timeline.play();
                shuffleButton.setOnMouseExited(event -> {
                    if(!isShuffle) {
                        Timeline timeline1 = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(shuffleButtonEffect.opacityProperty(), shuffleButtonEffect.getOpacity())),
                                new KeyFrame(Duration.millis(100), new KeyValue(shuffleButtonEffect.opacityProperty(), 0))
                        );
                        timeline1.play();
                    }
                });
        }
    }
    public void repeatEffect() {
        if(repeatStatus == Default.StatusRepeat.NONE) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(repeatButtonEffect.opacityProperty(), repeatButtonEffect.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(repeatButtonEffect.opacityProperty(), 1))
            );
            timeline.play();
            repeatButton.setOnMouseExited(event -> {
                if(repeatStatus == Default.StatusRepeat.NONE) {
                    Timeline timeline1 = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(repeatButtonEffect.opacityProperty(), repeatButtonEffect.getOpacity())),
                            new KeyFrame(Duration.millis(100), new KeyValue(repeatButtonEffect.opacityProperty(), 0))
                    );
                    timeline1.play();
                }
            });
        }
    }
    public void setShuffle() {
        if(!isShuffle) {
            makeFocused(shuffleButton, shuffleButtonEffect, "ShuffleFocusIcon");
            isShuffle = true;
        } else {
            makeUnfocused(shuffleButton, shuffleButtonEffect, "ShuffleIcon");
            isShuffle = false;

        }

    }
    public void setRepeat() {
        switch (repeatStatus) {
            case NONE -> {
                repeatStatus = Default.StatusRepeat.REPEAT;
                makeFocused(repeatButton, repeatButtonEffect, "RepeatFocusIcon");
            }
            case REPEAT -> {
                repeatStatus = Default.StatusRepeat.REPEAT_HEAVY;
                makeFocused(repeatButton, repeatButtonEffect, "RepeatFocusOneIcon");
            }
            case REPEAT_HEAVY -> {
                repeatStatus = Default.StatusRepeat.NONE;
                makeUnfocused(repeatButton, repeatButtonEffect, "RepeatIcon");
            }
        }
    }
    private void makeFocused(ImageView button, ImageView buttonEffect, String icon) {
        buttonEffect.setOpacity(1);
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        buttonEffect.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(40), new KeyValue(gaussianBlur.radiusProperty(), 7.7))
        );
        timeline.play();
        button.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/" + icon + ".png"))));
    }
    private void makeUnfocused(ImageView button, ImageView buttonEffect, String icon) {
        GaussianBlur gaussianBlur = new GaussianBlur(7.7);
        buttonEffect.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                new KeyFrame(Duration.millis(40), new KeyValue(gaussianBlur.radiusProperty(), 0))
        );
        timeline.play();
        timeline.setOnFinished(e -> {
            buttonEffect.setEffect(null);
            buttonEffect.setOpacity(0);
        });
        button.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/"+ icon + ".png"))));
    }
    public void setNext() {
        undoNextAnimation(nextButtonEffect);
    }
    public void setUndo() {
        undoNextAnimation(undoButtonEffect);
    }
    private void undoNextAnimation(ImageView buttonEffect) {
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        buttonEffect.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(150), new KeyValue(gaussianBlur.radiusProperty(), 9)),
                new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 7.7))
        );
        timeline.play();
    }
    public void maxEffect() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(maxButtonEffect.opacityProperty(), maxButtonEffect.getOpacity())),
                new KeyFrame(Duration.ZERO, new KeyValue(maxButton.scaleXProperty(), maxButton.getScaleX())),
                new KeyFrame(Duration.ZERO, new KeyValue(maxButton.scaleYProperty(), maxButton.getScaleY())),

                new KeyFrame(Duration.millis(100), new KeyValue(maxButtonEffect.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(100), new KeyValue(maxButton.scaleXProperty(), 1.1)),
                new KeyFrame(Duration.millis(100), new KeyValue(maxButton.scaleYProperty(), 1.1))
        );
        timeline.play();
        maxButton.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/MaxFocusIcon.png"))));
        maxButton.setOnMouseExited(event -> {
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(maxButtonEffect.opacityProperty(), maxButtonEffect.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(maxButton.scaleXProperty(), maxButton.getScaleX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(maxButton.scaleYProperty(), maxButton.getScaleY())),

                    new KeyFrame(Duration.millis(100), new KeyValue(maxButtonEffect.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(maxButton.scaleXProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(maxButton.scaleYProperty(), 1))
            );
            timeline1.play();
            maxButton.setImage(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icons/MaxIcon.png"))));
        });
    }
    public void volumeEffect() {
        double blurEffect = (volumeBar.getValue()/100) * 15.0;
        GaussianBlur gaussianBlur = new GaussianBlur(blurEffect);
        volumeEffect.setEffect(gaussianBlur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(volumeEffect.opacityProperty(), volumeEffect.getOpacity())),
                new KeyFrame(Duration.millis(100), new KeyValue(volumeEffect.opacityProperty(), 1))
        );
        timeline.play();
        volume.getStyleClass().remove("volumeBarBackground");
        volume.getStyleClass().add("volumeBarBackgroundHover");

        volumeBar.setOnMouseExited(event -> {
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(volumeEffect.opacityProperty(), volumeEffect.getOpacity())),
                    new KeyFrame(Duration.millis(100), new KeyValue(volumeEffect.opacityProperty(), 0))
            );
            timeline1.play();
            volume.getStyleClass().remove("volumeBarBackgroundHover");
            volume.getStyleClass().add("volumeBarBackground");
        });
    }
    private void createBackgroundCover(Image image) {
        backgroundImage.setImage(image);
        backgroundImage.setEffect(new GaussianBlur(20));
        backgroundImage.setOpacity(0.7);
        double VIEWPORT_WIDTH = image.getWidth();
        double VIEWPORT_HEIGHT = (VIEWPORT_WIDTH / backgroundImage.getFitWidth()) * backgroundImage.getFitHeight();
        double VIEWPORT_X = 0;
        double VIEWPORT_Y = (image.getHeight() / 2) - (VIEWPORT_HEIGHT / 2);

        Rectangle2D viewport = new Rectangle2D(VIEWPORT_X, VIEWPORT_Y, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        Rectangle clipShape = new Rectangle(0,0,backgroundImage.getFitWidth(), backgroundImage.getFitHeight());
        clipShape.setArcWidth(16);
        clipShape.setArcHeight(16);

        backgroundImage.setViewport(viewport);
        backgroundImage.setClip(clipShape);
    }
    private void createCoverIcon(Image image) {
        coverIcon.setImage(image);
        Rectangle clipShape = new Rectangle(0,0, coverIcon.getFitWidth(), coverIcon.getFitHeight());
        clipShape.setArcWidth(16);
        clipShape.setArcHeight(16);
        coverIcon.setClip(clipShape);
    }
    private void newCoverImage(Image image, String title, String artist, ArrayList<String> features) {
        createBackgroundCover(image);
        createCoverIcon(image);
        if(!titleLabel.getText().isEmpty()) {
            titleLabel.setText("");
        }
        if(!artistLabel.getText().isEmpty()) {
            artistLabel.setText("");
        }
        if(!featLabel.getText().isEmpty()) {
            featLabel.setText("");
        }
        titleLabel.setText(title);
        artistLabel.setText(artist);
        if(features != null) {
            for(String feat : features) {
                featLabel.setText(featLabel.getText() + Default.dot + feat);
            }
        }
    }
}
