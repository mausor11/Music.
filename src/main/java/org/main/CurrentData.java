package org.main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CurrentData {
    private BooleanProperty isNewTrackCover = new SimpleBooleanProperty(false);
    private BooleanProperty isPlay = new SimpleBooleanProperty(false);
    private BooleanProperty isPause = new SimpleBooleanProperty(false);
    private long actualTrackID = -1;
    private long actualPauseTrackID = -1;
    private TrackCell actualTrackCell = null;
    private long prevTrackID = -1;
    private Track actualTrack = null;
    private static CurrentData instance;
    public static CurrentData getDataInfo() {
        if(instance == null) {
            instance = new CurrentData();
        }
        return instance;
    }
    public BooleanProperty isNewTrackCover() {
        return isNewTrackCover;
    }
    public void setIsNewTracKCover(boolean is) {
        isNewTrackCover.set(is);
    }
    public BooleanProperty isPlay() {
        return isPlay;
    }
    public void setIsPlay(boolean is) {
        isPlay.set(is);
    }
    public BooleanProperty isPause() {
        return isPause;
    }
    public void setIsPause(boolean is) {
        isPause.set(is);
    }
    public long actualTrackID() {
        return this.actualTrackID;
    }
    public void setActualTrackID(long id) {
        this.actualTrackID = id;
    }
    public long actualPauseTrackID() {return this.actualPauseTrackID;}
    public void setActualPauseTrackID(long id) {
        this.actualPauseTrackID = id;
    }
    public long prevTracKID() {return this.prevTrackID;}
    public void setPrevTrackID(long id) {
        this.prevTrackID = id;
    }
    public Track actualTrack() {return this.actualTrack;}
    public void setActualTrack(Track track) {this.actualTrack = track;}
    public TrackCell actualTrackCell() {return this.actualTrackCell;}
    public void setActualTrackCell(TrackCell trackCell) {this.actualTrackCell = trackCell;}

}
