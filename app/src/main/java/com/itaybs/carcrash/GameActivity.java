package com.itaybs.carcrash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.itaybs.carcrash.Enums.GameMode;
import com.itaybs.carcrash.Managers.CarManager;
import com.itaybs.carcrash.Managers.GameManager;
import com.itaybs.carcrash.Managers.ObstaclesManager;
import com.itaybs.carcrash.Managers.GameScoreManager;


public class GameActivity extends AppCompatActivity {
    private GameManager gameManager;
    private GameScoreManager gameScoreManager;
    private final int OBSTACLES_ROWS = 9;
    private final int OBSTACLES_COLUMNS = 5;
    private final int[][] obstacles = new int[OBSTACLES_ROWS][OBSTACLES_COLUMNS];
    private final int columnWidth = obstacles[0].length;
    private final int colLastIndex = obstacles[0].length - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        GameMode gameMode = (GameMode) intent.getSerializableExtra("GAME_MODE");

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        LinearLayoutCompat heartsLayout = findViewById(R.id.heartsLayout);
        MaterialTextView scoreTextView = findViewById(R.id.score);

        ImageView car = new ImageView(this);
        car.setImageResource(R.drawable.car); // Set car image

        int rowLastIndex = OBSTACLES_ROWS - 1;
        int rowHeight = OBSTACLES_ROWS + 1;
        CarManager carManager = new CarManager(colLastIndex, rowLastIndex, car);
        ObstaclesManager obstaclesManager = new ObstaclesManager(obstacles, columnWidth, rowHeight, rowLastIndex, colLastIndex);
        gameScoreManager = new GameScoreManager(scoreTextView);
        gameManager = new GameManager(this, gridLayout, heartsLayout, gameScoreManager, carManager, obstaclesManager, () -> {
            Intent switchToIntent = new Intent(GameActivity.this, MainActivity.class);
            switchToIntent.putExtra("SCORE", gameScoreManager.getScore());
            switchToIntent.putExtra("GAME_MODE", gameMode);
            switchToIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(switchToIntent);
            finish();
        }, gameMode);

        gameManager.initializeGame();
        MaterialButton btnRight = findViewById(R.id.btnRight);
        MaterialButton btnLeft = findViewById(R.id.btnLeft);

        if (gameMode == GameMode.SENSOR) {
            btnRight.setVisibility(MaterialButton.GONE);
            btnLeft.setVisibility(MaterialButton.GONE);
        } else {
            btnRight.setOnClickListener(v -> gameManager.moveCar(true));
            btnLeft.setOnClickListener(v -> gameManager.moveCar(false));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameManager.resumeGame();
    }
}
