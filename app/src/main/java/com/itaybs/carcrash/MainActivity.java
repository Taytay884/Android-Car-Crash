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

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private MaterialRadioButton radioSensor, radioButtons, radioBoth;
    private MaterialButton buttonPlay, buttonLeaderboard;
    private MaterialTextView previousScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        }

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                GameMode gameMode = GameMode.BOTH;
                if (selectedId == radioSensor.getId()) {
                    gameMode = GameMode.SENSOR;
                } else if (selectedId == radioButtons.getId()) {
                    gameMode = GameMode.BUTTONS;
                } else if (selectedId == radioBoth.getId()) {
                    gameMode = GameMode.BOTH;
                }

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("GAME_MODE", gameMode);
                startActivity(intent);
            }
        });

        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LeaderboardActivity
                // Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                // startActivity(intent);
            }
        });
    }
}