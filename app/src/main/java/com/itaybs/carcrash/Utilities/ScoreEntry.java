package com.itaybs.carcrash.Utilities;

import java.util.Date;

public class ScoreEntry {
    private Date date;
    private int score;

    // Constructor
    public ScoreEntry(Date date, int score) {
        this.date = date;
        this.score = score;
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
}