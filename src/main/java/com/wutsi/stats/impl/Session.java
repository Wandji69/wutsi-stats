package com.wutsi.stats.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Session {
    private String hitId;
    private String productId;
    private List<Track> tracks = new ArrayList<>();

    public Session(String hitId, String productId) {
        this.hitId = hitId;
        this.productId = productId;
    }

    public boolean hasTrackWithEvent(String event) {
        return tracks.stream()
                .filter(it -> event.equals(it.getEvent()))
                .findFirst().isPresent();
    }

    public boolean hasTrackWithEvent(String event, String value) {
        return tracks.stream()
                .filter(it -> event.equals(it.getEvent()) && value.equals(it.getValue()))
                .findFirst().isPresent();
    }

    public long getDurationInSecond(){
        List<Track> tmp = tracks.stream()
                .filter(it -> includeInDuration(it))
                .sorted((t1, t2) -> (int) (t1.getTimeToLong() - t2.getTimeToLong()))
                .collect(Collectors.toList());
        if (tmp.size() <= 1) {
            return 0L;
        } else {
            return (tmp.get(tmp.size() - 1).getTimeToLong() - tmp.get(0).getTimeToLong()) / 1000;
        }
    }

    private boolean includeInDuration(Track track) {
        String event = track.getEvent();
        return "readstart".equals(event) || "scroll".equals(event)  || "g_one_tap_show".equals(event);
    }

    public void add(Track track) {
        tracks.add(track);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getHitId() {
        return hitId;
    }

    public void setHitId(String hitId) {
        this.hitId = hitId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
