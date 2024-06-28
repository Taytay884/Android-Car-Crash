package com.itaybs.carcrash.Utilities;

import android.widget.GridLayout;
import android.content.res.Resources;

public class GridLayoutHelper {

    public static GridLayout.LayoutParams createLayoutParams(Resources resources, int columnWidth, int rowHeight, int currentLane, int currentRow) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = resources.getDisplayMetrics().widthPixels / columnWidth;
        params.height = resources.getDisplayMetrics().heightPixels / rowHeight;
        params.columnSpec = GridLayout.spec(currentLane);
        params.rowSpec = GridLayout.spec(currentRow);
        return params;
    }

}
