package com.itaybs.carcrash.Managers;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.itaybs.carcrash.Enums.GameMode;
import com.itaybs.carcrash.GameActivity;
import com.itaybs.carcrash.Interfaces.GameOverCallback;
import com.itaybs.carcrash.Interfaces.MoveCallback;
import com.itaybs.carcrash.R;
import com.itaybs.carcrash.Utilities.MoveDetector;
import com.itaybs.carcrash.Utilities.SoundPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private final Context context;
    private final SoundPlayer soundPlayer;
    private final SoundPlayer soundEffectsPlayer;
    private MoveDetector moveDetector;
    private final GridLayout gridLayout;
    private final LinearLayoutCompat heartsLayout;
    private final ScoreManager scoreManager;
    private final CarManager carManager;
    private final ObstaclesManager obstaclesManager;
    private final GameOverCallback gameOverCallback;

    private int heartsLeft = 3;
    private static long delay = 1000L;
    private static long updatedDelay = 1000L;
    private Timer timer;
    private TimerTask currentTask;
    private boolean timerOn = false;

    public GameManager(Context context, GridLayout gridLayout, LinearLayoutCompat heartsLayout, ScoreManager scoreManager,
                       CarManager carManager, ObstaclesManager obstaclesManager, GameOverCallback gameOverCallback, GameMode gameMode) {
        this.context = context;
        this.gridLayout = gridLayout;
        this.heartsLayout = heartsLayout;
        this.scoreManager = scoreManager;
        this.carManager = carManager;
        this.obstaclesManager = obstaclesManager;
        this.soundPlayer = new SoundPlayer(context);
        this.soundEffectsPlayer = new SoundPlayer(context);
        this.moveDetector = new MoveDetector(context, new MoveCallback() {
            @Override
            public void moveX(float x) {
                if (gameMode != GameMode.BUTTONS) {
                    moveCar(x > 0);
                }
            }

            @Override
            public void moveZ(float z) {
                if (z > 0) {
                    if (updatedDelay > 600) {
                        updatedDelay = updatedDelay - 100;
                    }
                } else if (z < 0) {
                    if (updatedDelay < 1400) {
                        updatedDelay = updatedDelay + 100;
                    }
                }
            }
        });
        this.gameOverCallback = gameOverCallback;
    }

    public void initializeGame() {
        updateHearts();
        obstaclesManager.initializeObstacles(gridLayout, context);
        carManager.updateCarPosition(gridLayout, context);
        scoreManager.resetScore();
        startGameTimer();
    }

    public void updateHearts() {
        heartsLayout.removeAllViews();

        for (int i = 0; i < heartsLeft; i++) {
            ImageView heart = new ImageView(context);
            heart.setImageResource(R.drawable.heart); // Set heart image

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (40 * context.getResources().getDisplayMetrics().density), // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Height
                    0
            );

            int marginInPx = (int) (4 * context.getResources().getDisplayMetrics().density);
            params.setMargins(marginInPx, 0, 0, 0);

            heart.setLayoutParams(params);
            heartsLayout.addView(heart);
        }
    }

    public void moveCar(boolean isRight) {
        if (isRight) {
            carManager.moveCarRight();
        } else {
            carManager.moveCarLeft();
        }
        carManager.updateCarPosition(gridLayout, context);
        checkCollision();
    }

    public void resumeGame() {
        soundPlayer.playSound(R.raw.background, true);
        moveDetector.start();
        startGameTimer();
    }

    public void pauseGame() {
        soundPlayer.pauseSound();
        moveDetector.stop();
        stopGameTimer();
    }

    public void startGameTimer() {
        if (!timerOn) {
            timerOn = true;
            timer = new Timer();
            scheduleNewTask();
        }
    }

    public void stopGameTimer() {
        timerOn = false;
        if (currentTask != null) {
            currentTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    private void scheduleNewTask() {
        if (currentTask != null) {
            currentTask.cancel();
        }
        currentTask = new TimerTask() {
            @Override
            public void run() {
                ((GameActivity) context).runOnUiThread(() -> {
                    obstaclesManager.updateObstaclesMatrix();
                    obstaclesManager.initializeObstacles(gridLayout, context);
                    carManager.updateCarPosition(gridLayout, context);
                    checkCollision();
                    scoreManager.addScore(1); // Increment score as an example
                    if (updatedDelay != delay) {
                        delay = updatedDelay;
                        updateGameTimer();
                    }
                });
            }
        };
        timer.schedule(currentTask, delay, delay);
    }

    private void updateGameTimer() {
        if (timerOn) {
            scheduleNewTask();
        }
    }

    public void checkCollision() {
        if (obstaclesManager.checkCollision(carManager.getCurrentRow(), carManager.getCurrentLane())) {
            soundEffectsPlayer.playSound(R.raw.car_crash, false);
            heartsLeft--;

            if (heartsLeft == 0) {
                toastAndVibrate("Game Over!");
                this.gameOverCallback.gameOver();
            } else {
                toastAndVibrate("Collision Detected!");
            }

            updateHearts();
            carManager.resetCarPosition();
            obstaclesManager.reset();
        }
    }

    private void toastAndVibrate(String text) {
        toast(text);
        vibrate();
    }

    private void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
