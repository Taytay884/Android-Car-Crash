package com.itaybs.carcrash.Managers;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.itaybs.carcrash.Utilities.GridLayoutHelper;

public class CarManager {
    private int currentLane;
    private int currentRow;
    private final int colLastIndex;
    private final int rowLastIndex;
    private final ImageView car;

    public CarManager(int colLastIndex, int rowLastIndex, ImageView car) {
        this.currentLane = colLastIndex / 2; // Start car in the middle lane
        this.currentRow = rowLastIndex; // Start car at the bottom row
        this.colLastIndex = colLastIndex;
        this.rowLastIndex = rowLastIndex;
        this.car = car;
    }

    public void moveCarLeft() {
        if (currentLane > 0) {
            currentLane--;
        }
    }

    public void moveCarRight() {
        if (currentLane < colLastIndex) {
            currentLane++;
        }
    }

    public void updateCarPosition(GridLayout gridLayout, Context context) {
        gridLayout.removeView(car);
        GridLayout.LayoutParams params = GridLayoutHelper.createLayoutParams(
                context.getResources(),
                colLastIndex + 1,
                rowLastIndex + 1,
                currentLane,
                currentRow
        );
        gridLayout.addView(car, params);
    }

    public void resetCarPosition() {
        currentLane = colLastIndex / 2;
        currentRow = rowLastIndex;
    }

    public int getCurrentLane() {
        return currentLane;
    }

    public int getCurrentRow() {
        return currentRow;
    }
}