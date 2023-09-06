package org.main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.main.Default;

import java.util.ArrayList;


public class PlaylistSectionController {
    @FXML
    Label showAllText1;
    @FXML
    Label showAllText1a;
    @FXML
    Label showAllText1Effect;
    @FXML
    StackPane showAllPane1;
    @FXML
    StackPane playlistSection;
    @FXML
    GridPane gridFirst;
    @FXML
    GridPane mainGrid;
    private ArrayList<Rectangle> covers;
    private double scaleX;
    private double scaleY;
    private BooleanProperty isShown = new SimpleBooleanProperty(false);
    private double lastValue = 1002;
    public void initialize() {
        makeTemplate(10);
        makeDefault();
        playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
            hiddenLookPlaylists(t2.doubleValue() - 50, gridFirst);
            lastValue = t2.doubleValue();
        });
        isShown.addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                if(!gridFirst.getChildren().isEmpty()) {
                    gridFirst.getChildren().clear();
                    gridFirst.getColumnConstraints().clear();
                    gridFirst.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(2).setMinHeight(520);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    shownLookPlaylists(t2.doubleValue() - 50, gridFirst);
                    lastValue = t2.doubleValue();
                });
            } else {
                if(!gridFirst.getChildren().isEmpty()) {
                    gridFirst.getChildren().clear();
                    gridFirst.getColumnConstraints().clear();
                    gridFirst.getRowConstraints().clear();
                }
                mainGrid.getRowConstraints().get(2).setMinHeight(250);
                playlistSection.widthProperty().addListener((observableValue1, number, t2) -> {
                    hiddenLookPlaylists(t2.doubleValue() - 50, gridFirst);
                    lastValue = t2.doubleValue();
                });
            }
        }));

        makeEffect(showAllPane1, showAllText1, showAllText1a, showAllText1Effect, isShown);

    }
    private void makeTemplate(int many) {
        covers = new ArrayList<>();
        for(int i = 0;i< many;i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(180);
            rectangle.setHeight(250);
            rectangle.setArcHeight(16);
            rectangle.setArcWidth(16);
            rectangle.setFill(Color.web("#7EAA92"));
            covers.add(rectangle);
        }
    }
    private void makeDefault() {
        for(int i=0;i<5 && i < covers.size();i++) {
            gridFirst.addColumn(i, covers.get(i));
        }
    }
    private void hiddenLookPlaylists(double W, GridPane gridPane) {
        if(!gridPane.getChildren().isEmpty()) {
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
        }
        double recW = 180;
        double b = 10;
        int columns = (int) ((W + b) / (recW + b));
        double freePx = (W - (columns * recW + (columns -1)* b));
        double k = freePx /columns;
        if(columns < covers.size()) {
            this.scaleX = (recW + k) / recW;
            this.scaleY = (recW + k) / recW;
        }
        if(columns == covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }

        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        gridPane.getRowConstraints().add(new RowConstraints());
        mainGrid.getRowConstraints().get(2).setMinHeight(250 * scaleY);
        for(int i = 0; i < columns && i < covers.size(); i++) {
            covers.get(i).setWidth(180*scaleX);
            covers.get(i).setHeight(250*scaleY);
            gridPane.add(covers.get(i), i, 0);
        }
    }
    private void shownLookPlaylists(double W, GridPane gridPane) {
        if(!gridPane.getChildren().isEmpty()) {
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
        }
        double recW = 180;
        double b = 10;
        int rows = 0;
        int columns = (int) ((W + b) / (recW + b));
        double freePx = (W - (columns * recW + (columns -1)* b));
        double k = freePx /columns;
        if(columns < covers.size()) {
            this.scaleX = (recW + k) / recW;
            this.scaleY = (recW + k) / recW;
        }
        if(columns == covers.size()) {
            this.scaleX = 1;
            this.scaleY = 1;
        }
        if(covers.size() % columns == 0) {
            rows = covers.size() / columns;
        } else {
            rows = covers.size() / columns + 1;
        }

        if(rows == 1) {
            mainGrid.getRowConstraints().get(2).setMinHeight(250 * scaleY);
        } else {
            mainGrid.getRowConstraints().get(2).setMinHeight(520 * scaleY);
        }
        for(int i=0;i<columns;i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        for(int y=0;y<rows;y++) {
            gridPane.getRowConstraints().add(new RowConstraints());
        }
        int columnCount = gridPane.getColumnCount();
        int r = 0;
        int c = 0;
        for(Rectangle cover : covers) {
            if(c == columnCount) {
                r++;
                c = 0;
            }
            cover.setWidth(180*scaleX);
            cover.setHeight(250*scaleY);
            gridPane.add(cover, c, r);
            c++;
        }

    }
    private void makeEffect(StackPane pane, Label text, Label textA, Label textEffect, BooleanProperty isShown) {
        pane.setOnMouseEntered(event1 -> {
            Timeline enterTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                    new KeyFrame(Duration.millis(100), new KeyValue(text.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textA.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textEffect.opacityProperty(), 1))
            );
            enterTimeline.play();
            pane.setOnMouseExited(event2 -> {
                Timeline exitTimeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                        new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                        new KeyFrame(Duration.millis(100), new KeyValue(text.opacityProperty(), 1)),
                        new KeyFrame(Duration.millis(100), new KeyValue(textA.opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(100), new KeyValue(textEffect.opacityProperty(), 0))
                );
                exitTimeline.play();
            });
        });
        pane.setOnMouseClicked(event3 -> {
            Timeline clickedTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), text.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textA.opacityProperty(), textA.getOpacity())),
                    new KeyFrame(Duration.ZERO, new KeyValue(textEffect.opacityProperty(), textEffect.getOpacity())),

                    new KeyFrame(Duration.millis(20), new KeyValue(text.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(20), new KeyValue(textA.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(20), new KeyValue(textEffect.opacityProperty(), 0)),

                    new KeyFrame(Duration.millis(200), new KeyValue(text.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(textA.opacityProperty(), 1)),
                    new KeyFrame(Duration.millis(200), new KeyValue(textEffect.opacityProperty(), 1))
            );
            clickedTimeline.play();
            switchMode(isShown);
        });
    }
    private void switchMode(BooleanProperty isShown) {
        if(isShown.get()) {
            this.isShown.set(false);
            hiddenLookPlaylists(lastValue - 50, gridFirst);

        } else {
            this.isShown.set(true);
            shownLookPlaylists(lastValue - 50, gridFirst);
        }
    }
}
