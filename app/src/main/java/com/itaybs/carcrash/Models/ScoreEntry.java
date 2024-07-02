package com.itaybs.carcrash.Models;

import java.util.Date;

public class ScoreEntry {
    private Date date;
    private int score;
    private double latitude;
    private double longitude;

    // Constructor
    public ScoreEntry(Date date, int score) {
        this.date = date;
        this.score = score;
        this.latitude = 0;
        this.longitude = 0;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLatitude() { return this.latitude; }
    public double getLongitude() { return this.longitude; }
}