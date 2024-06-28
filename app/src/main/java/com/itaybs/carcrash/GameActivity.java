package com.itaybs.carcrash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.textview.MaterialTextView;
import com.itaybs.carcrash.Managers.CarManager;
import com.itaybs.carcrash.Managers.GameManager;
import com.itaybs.carcrash.Managers.ObstaclesManager;
import com.itaybs.carcrash.Managers.ScoreManager;

public class GameActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private LinearLayoutCompat heartsLayout;
    private MaterialTextView scoreTextView;
    private GameManager gameManager;
    private CarManager carManager;
    private ObstaclesManager obstaclesManager;
    private ScoreManager scoreManager;
    private ImageView car;

    private int[][] obstacles = new int[9][5];
    private final int columnWidth = obstacles[0].length;
    private final int rowHeight = obstacles.length + 1;
    private final int rowLastIndex = obstacles.length - 1;
    private final int colLastIndex = obstacles[0].length - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridLayout = findViewById(R.id.gridLayout);
        heartsLayout = findViewById(R.id.heartsLayout);
        scoreTextView = findViewById(R.id.score);

        car = new ImageView(this);
        car.setImageResource(R.drawable.car); // Set car image

        carManager = new CarManager(colLastIndex, rowLastIndex, car);
        obstaclesManager = new ObstaclesManager(obstacles, columnWidth, rowHeight, rowLastIndex, colLastIndex);
        scoreManager = new ScoreManager(scoreTextView);
        gameManager = new GameManager(this, gridLayout, heartsLayout, scoreManager, carManager, obstaclesManager);

        gameManager.initializeGame();

        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(v -> gameManager.moveCar(true));

        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(v -> gameManager.moveCar(false));
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
