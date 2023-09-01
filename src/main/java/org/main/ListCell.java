package org.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class ListCell {
    private final double PREF_WIDTH = 250;
    private final double PREF_HEIGHT = 78;
    private final StackPane cellBox;
    private final ImageView coverImg;
    private ImageView backgroundImg;
    private Rectangle coverCell;
    private final ListView<StackPane> listView;
    private boolean isFocused = false;
    public ListCell(ListView<StackPane> listView, Image coverArt, String title, String artist, ArrayList<String> features) {
        this.listView = listView;
        cellBox = new StackPane();
        cellBox.setAlignment(Pos.CENTER_LEFT);
        cellBox.setPrefWidth(PREF_WIDTH);
        cellBox.setPrefHeight(PREF_HEIGHT);
        coverImg = setCoverImg(coverArt);
        VBox contentBox = setAlbumInfo(title, artist, features);

        coverImg.setMouseTransparent(true);
        contentBox.setMouseTransparent(true);

        StackPane.setMargin(coverImg, new Insets(0,0,0,14));
        StackPane.setMargin(contentBox, new Insets(0,0,0,83));

        cellBox.getChildren().addAll(setBackground(coverArt), coverImg, contentBox);

        listView.widthProperty().addListener((observable, oldValue, newValue) -> cellBox.setPrefWidth(newValue.doubleValue() - 20.0));

    }
    public StackPane getCell() {
        return cellBox;
    }
    private ImageView setCoverImg(Image coverArt) {
        ImageView cover = new ImageView(coverArt);
        cover.setFitWidth(60);
        cover.setFitHeight(60);
        Rectangle clipShape = new Rectangle();
        clipShape.setWidth(60);
        clipShape.setHeight(60);
        clipShape.setArcWidth(16);
        clipShape.setArcHeight(16);
        cover.setClip(clipShape);
        return cover;
    }
    private VBox setAlbumInfo(String title, String artist, ArrayList<String> features) {
        VBox contentbox = new VBox();
        contentbox.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        Label artistLabel = new Label(artist);

        titleLabel.getStyleClass().add("listTitleInfo");
        artistLabel.getStyleClass().add("listArtistInfo");

        if(features != null) {
            for(String feat : features) {
                artistLabel.setText(artistLabel.getText() + Default.dot + feat);
            }
        }

        contentbox.getChildren().addAll(titleLabel, artistLabel);

        return contentbox;

    }
    private Rectangle2D newViewport(Image backgroundArt, ImageView background) {
        double VIEWPORT_WIDTH = backgroundArt.getWidth();
        double VIEWPORT_HEIGHT = (VIEWPORT_WIDTH / background.getFitWidth()) * background.getFitHeight();
        double VIEWPORT_X = 0;
        double VIEWPORT_Y = (backgroundArt.getHeight() / 2) - (VIEWPORT_HEIGHT / 2);

        return new Rectangle2D(VIEWPORT_X, VIEWPORT_Y, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }
    private StackPane setBackground(Image backgroundArt) {
        backgroundImg = new ImageView(backgroundArt);
        backgroundImg.setFitHeight(PREF_HEIGHT);
        backgroundImg.setFitWidth(PREF_WIDTH);
        backgroundImg.setEffect(new GaussianBlur(3.0));
        backgroundImg.setOpacity(0);
        backgroundImg.setViewport(newViewport(backgroundArt, backgroundImg));

        Rectangle clipShape = new Rectangle();
        clipShape.setWidth(PREF_WIDTH);
        clipShape.setHeight(PREF_HEIGHT);
        clipShape.setArcHeight(16);
        clipShape.setArcWidth(16);
        backgroundImg.setClip(clipShape);

        coverCell = new Rectangle();
        coverCell.setFill(Default.BROWN_4);
        coverCell.setWidth(PREF_WIDTH);
        coverCell.setHeight(PREF_HEIGHT);
        coverCell.setOpacity(0);
        coverCell.setArcHeight(16);
        coverCell.setArcWidth(16);

        StackPane bg = new StackPane(backgroundImg);
        bg.getChildren().add(coverCell);


        listView.widthProperty().addListener((observable, oldValue, newValue) -> {
            backgroundImg.setFitWidth(newValue.doubleValue() - 20.0);
            clipShape.setWidth(newValue.doubleValue() - 20.0);
            coverCell.setWidth(newValue.doubleValue() - 20.0);
            backgroundImg.setViewport(newViewport(backgroundArt, backgroundImg));
        });

            coverCell.setOnMouseEntered(event -> {
                if(!isFocused) {
                    GaussianBlur gaussianBlur = new GaussianBlur(0.0);
                    backgroundImg.setEffect(gaussianBlur);
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                            new KeyFrame(Duration.ZERO, new KeyValue(backgroundImg.opacityProperty(), backgroundImg.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(coverCell.opacityProperty(), coverCell.getOpacity())),
                            new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleXProperty(), coverImg.getScaleX())),
                            new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleYProperty(), coverImg.getScaleY())),

                            new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                            new KeyFrame(Duration.millis(200), new KeyValue(backgroundImg.opacityProperty(), 0.3)),
                            new KeyFrame(Duration.millis(200), new KeyValue(coverCell.opacityProperty(), 0.2)),
                            new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleXProperty(), 1.1)),
                            new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleYProperty(), 1.1))
                    );
                    timeline.play();
                    backgroundImg.setOpacity(0.7);


                    coverCell.setOnMouseExited(event1 -> {
                        Timeline timeline1 = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                                new KeyFrame(Duration.ZERO, new KeyValue(backgroundImg.opacityProperty(), backgroundImg.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(coverCell.opacityProperty(), coverCell.getOpacity())),
                                new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleXProperty(), coverImg.getScaleX())),
                                new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleYProperty(), coverImg.getScaleY())),

                                new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 0.0)),
                                new KeyFrame(Duration.millis(200), new KeyValue(backgroundImg.opacityProperty(), 0.0)),
                                new KeyFrame(Duration.millis(200), new KeyValue(coverCell.opacityProperty(), 0)),
                                new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleXProperty(), 1)),
                                new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleYProperty(), 1))

                        );
                        if (!isFocused) {
                            timeline1.play();
                        }
                    });
                }
            });
        return bg;
    }
    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
        setCellFocused(isFocused);
    }
    private void setCellFocused(boolean isFocused) {
        if(isFocused) {
            GaussianBlur gaussianBlur = new GaussianBlur(0.0);
            backgroundImg.setEffect(gaussianBlur);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                    new KeyFrame(Duration.ZERO, new KeyValue(backgroundImg.opacityProperty(), backgroundImg.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverCell.opacityProperty(), coverCell.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleXProperty(), coverImg.getScaleX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleYProperty(), coverImg.getScaleY())),

                    new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 7.7)),
                    new KeyFrame(Duration.millis(200), new KeyValue(backgroundImg.opacityProperty(), 0.4)),
                    new KeyFrame(Duration.millis(200), new KeyValue(coverCell.opacityProperty(), 0.1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleXProperty(), 1.1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleYProperty(), 1.1))
            );
            timeline.play();
        } else {
            GaussianBlur gaussianBlur = new GaussianBlur(0.0);
            backgroundImg.setEffect(gaussianBlur);
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(gaussianBlur.radiusProperty(), gaussianBlur.getRadius())),
                    new KeyFrame(Duration.ZERO, new KeyValue(backgroundImg.opacityProperty(), backgroundImg.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverCell.opacityProperty(), coverCell.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleXProperty(), coverImg.getScaleX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(coverImg.scaleYProperty(), coverImg.getScaleY())),

                    new KeyFrame(Duration.millis(200), new KeyValue(gaussianBlur.radiusProperty(), 0.0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(backgroundImg.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(coverCell.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleXProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(coverImg.scaleYProperty(), 1))

            );
            timeline1.play();
            this.isFocused = false;
        }
    }
}
