package com.itaybs.carcrash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.itaybs.carcrash.Enums.GameMode;
import com.itaybs.carcrash.Managers.ScoreManager;
import com.itaybs.carcrash.Utilities.ScoreEntry;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private MaterialRadioButton radioSensor, radioButtons, radioBoth;
    private MaterialButton buttonPlay, buttonLeaderboard;
    private MaterialTextView previousScore;
    private ScoreManager scoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        scoreManager = new ScoreManager(this);

        Intent intent = getIntent();
        Integer score = (Integer) intent.getSerializableExtra("SCORE");

        GameMode gameMode = (GameMode) intent.getSerializableExtra("GAME_MODE");
        if (gameMode == null) {
            gameMode = GameMode.BOTH;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        radioSensor = findViewById(R.id.radioSensor);
        radioButtons = findViewById(R.id.radioButtons);
        radioBoth = findViewById(R.id.radioBoth);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);
        previousScore = findViewById(R.id.previousScoreText);

        previousScore.setVisibility(View.GONE);
        radioBoth.setChecked(gameMode == GameMode.BOTH);
        radioButtons.setChecked(gameMode == GameMode.BUTTONS);
        radioSensor.setChecked(gameMode == GameMode.SENSOR);

        if (score != null) {
            previousScore.setVisibility(View.VISIBLE);
            String scoreMessage = getString(R.string.previous_score, score);
            previousScore.setText(scoreMessage);
            scoreManager.saveScoreEntry(new ScoreEntry(new Date(), score));
        }

        buttonPlay.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            GameMode gameMode1 = GameMode.BOTH;
            if (selectedId == radioSensor.getId()) {
                gameMode1 = GameMode.SENSOR;
            } else if (selectedId == radioButtons.getId()) {
                gameMode1 = GameMode.BUTTONS;
            } else if (selectedId == radioBoth.getId()) {
                gameMode1 = GameMode.BOTH;
            }

            Intent intent12 = new Intent(MainActivity.this, GameActivity.class);
            intent12.putExtra("GAME_MODE", gameMode1);
            startActivity(intent12);
        });

        buttonLeaderboard.setOnClickListener(v -> {
             Intent intent1 = new Intent(MainActivity.this, LeaderboardActivity.class);
             startActivity(intent1);
        });
    }
}