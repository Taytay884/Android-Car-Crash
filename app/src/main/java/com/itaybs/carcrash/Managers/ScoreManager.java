package com.itaybs.carcrash.Managers;

import com.google.android.material.textview.MaterialTextView;

public class ScoreManager {
    private int score;
    private MaterialTextView scoreTextView;

    public ScoreManager(MaterialTextView scoreTextView) {
        this.scoreTextView = scoreTextView;
        this.score = 0;
        updateScoreTextView();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
        updateScoreTextView();
    }

    public void resetScore() {
        this.score = 0;
        updateScoreTextView();
    }

    private void updateScoreTextView() {
        String scoreTxt = "000";
        if (score < 10) scoreTxt = "00" + score;
        else if (score < 100) scoreTxt = "0" + score;
        else scoreTxt = "" + score;
        scoreTextView.setText(scoreTxt);
    }
}