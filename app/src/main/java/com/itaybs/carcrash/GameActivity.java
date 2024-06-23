package com.itaybs.carcrash;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private int heartsLeft = 3;
    private LinearLayoutCompat heartsLayout;
    private static final long DELAY = 1000L;
    Random rand = new Random();

    private boolean timerOn = false;
    private Timer timer;

    private int[][] obstacles = {
            {0, 0, 0},   // Row 0
            {0, 0, 0},   // Row 1
            {0, 0, 0},   // Row 2
            {0, 0, 0},   // Row 3
            {0, 0, 0},   // Row 4
            {0, 0, 0},   // Row 5
            {0, 0, 0}    // Row 6
    };

    private GridLayout gridLayout;
    private ImageView car;
    private int currentLane = 1; // Start car in the middle lane (0-indexed)
    private int currentRow = 6;  // Start car at the bottom row (0-indexed)

    private void updateObstaclesMatrix() {
        for(int i = 6; i > 0; i--) {
            obstacles[i][0] = obstacles[i-1][0];
            obstacles[i][1] = obstacles[i-1][1];
            obstacles[i][2] = obstacles[i-1][2];
        }
        obstacles[0][0] = 0;
        obstacles[0][1] = 0;
        obstacles[0][2] = 0;
        int obstacleColumn = rand.nextInt(3);
        obstacles[0][obstacleColumn] = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridLayout = findViewById(R.id.gridLayout);
        heartsLayout = findViewById(R.id.heartsLayout);

        // Initialize the hearts
        updateHearts();

        // Initialize the obstacles
        initializeObstacles();

        // Initialize the car image view
        car = new ImageView(this);
        car.setImageResource(R.drawable.car); // Set car image

        // Add car to initial position
        updateCarPosition();

        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarLeft();
            }
        });

        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCarRight();
            }
        });

        startGameTimer();
    }


    private void updateHearts() {
        heartsLayout.removeAllViews();

        for (int i = 0; i < heartsLeft; i++) {
            AppCompatImageView heart = new AppCompatImageView(this);
            heart.setImageResource(R.drawable.heart); // Set heart image

            // Create LayoutParams and set the required properties
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                    (int) (40 * getResources().getDisplayMetrics().density), // Width
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, // Height
                    0
            );

            // Set left margin (change the value to your desired margin)
            int marginInPx = (int) (4 * getResources().getDisplayMetrics().density); // Example: 8dp
            params.setMargins(marginInPx, 0, 0, 0);

            heart.setLayoutParams(params);
            heartsLayout.addView(heart);
        }
    }

    private void initializeObstacles() {
        gridLayout.removeAllViews();

        for (int row = 0; row < obstacles.length; row++) {
            for (int col = 0; col < obstacles[row].length; col++) {
                if (obstacles[row][col] == 1) {
                    ImageView obstacle = new ImageView(this);
                    obstacle.setImageResource(R.drawable.obstacle); // Set obstacle image
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = getResources().getDisplayMetrics().widthPixels / 3;
                    params.height = getResources().getDisplayMetrics().heightPixels / 8;
                    params.columnSpec = GridLayout.spec(col);
                    params.rowSpec = GridLayout.spec(row);
                    gridLayout.addView(obstacle, params);
                } else {
                    View emptyView = new View(this);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = getResources().getDisplayMetrics().widthPixels / 3;
                    params.height = getResources().getDisplayMetrics().heightPixels / 8;
                    params.columnSpec = GridLayout.spec(col);
                    params.rowSpec = GridLayout.spec(row);
                    gridLayout.addView(emptyView, params);
                }
            }
        }
    }

    private void moveCarLeft() {
        if (currentLane > 0) {
            currentLane--;
            updateCarPosition();
        }
    }

    private void moveCarRight() {
        if (currentLane < 2) { // 3 columns, 0-indexed (0 to 2)
            currentLane++;
            updateCarPosition();
        }
    }

    private void updateCarPosition() {
        // Remove car from its current position
        gridLayout.removeView(car);

        // Add car to new position
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 3;
        params.height = getResources().getDisplayMetrics().heightPixels / 8;
        params.columnSpec = GridLayout.spec(currentLane);
        params.rowSpec = GridLayout.spec(currentRow);
        gridLayout.addView(car, params);

        // Check for collision with obstacles
        checkCollision();
    }

    private void checkCollision() {
        if (obstacles[currentRow][currentLane] == 1) {
            heartsLeft--;

            if (heartsLeft == 0) {
                heartsLeft = 3;
                toastAndVibrate("Game Over!");
            } else {
                toastAndVibrate("Collision Detected!");
            }

            updateHearts();

            // Reset car position
            currentLane = 1; // Reset to middle lane
            currentRow = 6;  // Reset to bottom row

            // Reset obstacles
            for (int[] obstacle : obstacles) {
                Arrays.fill(obstacle, 0);
            }
        }
    }

    private void startGameTimer() {
        if (!timerOn) {
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        updateObstaclesMatrix();
                        initializeObstacles();
                        updateCarPosition();
                    });
                }
            },0L,DELAY);
        }
    }

    private void stopGameTimer() {
        timerOn = false;
        timer.cancel();
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
