package com.itaybs.carcrash.Managers;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;

import com.itaybs.carcrash.R;

import java.util.Arrays;
import java.util.Random;

public class ObstaclesManager {
    private final int[][] obstacles;
    private final int columnWidth;
    private final int rowHeight;
    private final int rowLastIndex;
    private final int colLastIndex;
    private final Random rand;

    public ObstaclesManager(int[][] obstacles, int columnWidth, int rowHeight, int rowLastIndex, int colLastIndex) {
        this.obstacles = obstacles;
        this.columnWidth = columnWidth;
        this.rowHeight = rowHeight;
        this.rowLastIndex = rowLastIndex;
        this.colLastIndex = colLastIndex;
        this.rand = new Random();
    }

    public void updateObstaclesMatrix() {
        int cols = colLastIndex + 1;

        // Move existing obstacles down
        for (int i = rowLastIndex; i > 0; i--) {
            for (int j = 0; j < cols; j++) {
                obstacles[i][j] = obstacles[i - 1][j];
            }
        }

        // Clear the top row
        for (int i = 0; i < cols; i++) {
            obstacles[0][i] = 0;
        }

        // Place a new obstacle
        int obstacleColumn = rand.nextInt(cols);
        obstacles[0][obstacleColumn] = 1;

        // Randomize the placement of a coin
        if (rand.nextFloat() < 0.2) { // 20% chance to place a coin
            int coinColumn;
            do {
                coinColumn = rand.nextInt(cols);
            } while (coinColumn == obstacleColumn); // Ensure coin doesn't overlap with obstacle
            obstacles[0][coinColumn] = 2;
        }
    }

    public void initializeObstacles(GridLayout gridLayout, Context context) {
        gridLayout.removeAllViews();

        for (int row = 0; row < obstacles.length; row++) {
            for (int col = 0; col < obstacles[row].length; col++) {
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = context.getResources().getDisplayMetrics().widthPixels / columnWidth;
                params.height = context.getResources().getDisplayMetrics().heightPixels / rowHeight;
                params.columnSpec = GridLayout.spec(col);
                params.rowSpec = GridLayout.spec(row);

                if (obstacles[row][col] == 1) {
                    ImageView obstacle = new ImageView(context);
                    obstacle.setImageResource(R.drawable.obstacle); // Set obstacle image
                    gridLayout.addView(obstacle, params);
                } else if (obstacles[row][col] == 2) {
                    ImageView obstacle = new ImageView(context);
                    obstacle.setImageResource(R.drawable.coin); // Set obstacle image
                    gridLayout.addView(obstacle, params);
                } else {
                    View emptyView = new View(context);
                    gridLayout.addView(emptyView, params);
                }
            }
        }
    }

    public boolean checkCollision(int row, int col) {
        return obstacles[row][col] == 1;
    }

    public boolean checkCoinCollision(int row, int col) {
        return obstacles[row][col] == 2;
    }

    public void resetObstacle(int row, int col) {
        obstacles[row][col] = 0;
    }

    public void reset() {
        for (int i = 0; i <= rowLastIndex; i++) {
            Arrays.fill(obstacles[i], 0);
        }
    }
}