package org.main;

import java.util.ArrayList;

public class Track implements Comparable<Track> {
    private long trackID;
    private long userID;
    private long artistID;
    private long genreID;
    private String coverLink;
    private String trackName;
    private long albumID;
    private boolean isFavourite;
    private long playlistID;
    private int trackIndex;
    private ArrayList<Integer> features;

    public long getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(long trackDuration) {
        this.trackDuration = trackDuration;
    }

    private long trackDuration;

    public long getTrackID() {
        return trackID;
    }
    public void setTrackID(long trackID) {
        this.trackID = trackID;
    }
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public long getArtistID() {
        return artistID;
    }
    public void setArtistID(long artistID) {
        this.artistID = artistID;
    }
    public long getGenreID() {
        return genreID;
    }
    public void setGenreID(long genreID) {
        this.genreID = genreID;
    }
    public String getCoverLink() {
        return coverLink;
    }
    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }
    public String getTrackName() {
        return trackName;
    }
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    public long getAlbumID() {
        return albumID;
    }
    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }
    public boolean isFavourite() {
        return isFavourite;
    }
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
    public long getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(long playlistID) {
        this.playlistID = playlistID;
    }
    public ArrayList<Integer> getFeatures() {
        return features;
    }
    public void setFeatures(ArrayList<Integer> features) {
        this.features = features;
    }
    public int getTrackIndex() {
        return trackIndex;
    }
    public Track(int trackIndex, long trackID, long trackDuration, long userID, long artistID, long genreID, String coverLink, String trackName, long albumID, boolean isFavourite, long playlistID, ArrayList<Integer> features) {
        this.trackIndex = trackIndex;
        this.trackID = trackID;
        this.userID = userID;
        this.artistID = artistID;
        this.genreID = genreID;
        this.coverLink = coverLink;
        this.trackName = trackName;
        this.albumID = albumID;
        this.isFavourite = isFavourite;
        this.playlistID = playlistID;
        this.features = features;
        this.trackDuration = trackDuration;
    }
    public Track(String name, long trackDuration) {
        this.trackName = name;
        this.trackDuration = trackDuration;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackID=" + trackID +
                "trackDuration=" + trackDuration +
                ", userID=" + userID +
                ", artistID=" + artistID +
                ", genreID=" + genreID +
                ", coverLink='" + coverLink + '\'' +
                ", trackName='" + trackName + '\'' +
                ", albumID=" + albumID +
                ", isFavourite=" + isFavourite +
                ", playlistID=" + playlistID +
                ", features=" + features +
                '}' + "\n";
    }

    @Override
    public int compareTo(Track o) {
        return trackIndex - o.getTrackIndex();
    }
}
