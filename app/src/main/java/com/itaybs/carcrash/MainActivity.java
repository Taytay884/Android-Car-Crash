package com.itaybs.carcrash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.itaybs.carcrash.Enums.GameMode;
import com.itaybs.carcrash.Managers.ScoreManager;
import com.itaybs.carcrash.Managers.LocationManager;
import com.itaybs.carcrash.Models.ScoreEntry;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private RadioGroup radioGroup;
    private MaterialRadioButton radioSensor, radioButtons, radioBoth;
    private MaterialButton buttonPlay, buttonLeaderboard;
    private MaterialTextView previousScore;
    private ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = new LocationManager(this);
        scoreManager = new ScoreManager(this);

        Intent intent = getIntent();
        Integer score = (Integer) intent.getSerializableExtra("SCORE");

        GameMode gameMode = (GameMode) intent.getSerializableExtra("GAME_MODE");
        if (gameMode == null) {
            gameMode = GameMode.BOTH;
        }

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
            ScoreEntry scoreEntry = new ScoreEntry(new Date(), score);
            requestLocationAndSaveScore(scoreEntry);
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

            Intent intent1 = new Intent(MainActivity.this, GameActivity.class);
            intent1.putExtra("GAME_MODE", gameMode1);
            startActivity(intent1);
        });

        buttonLeaderboard.setOnClickListener(v -> {
            Intent intent12 = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent12);
        });
    }

    private void requestLocationAndSaveScore(ScoreEntry scoreEntry) {
        locationManager.requestLocation(new LocationManager.LocationCallback() {
            @Override
            public void onLocationResult(double latitude, double longitude) {
                scoreEntry.setLatitude(latitude);
                scoreEntry.setLongitude(longitude);
                scoreManager.saveScoreEntry(scoreEntry);
            }

            @Override
            public void onLocationError(String errorMessage) {
                // Handle location error here if needed
                scoreManager.saveScoreEntry(scoreEntry); // Save entry even if location isn't available
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationManager.onRequestPermissionsResult(requestCode, grantResults);
    }
}
