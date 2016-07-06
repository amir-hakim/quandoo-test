package com.test.quandoo.view.base;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

/**
 * Base Activity is used as the base for all activities in the app
 * It's used to hold all the common UI components in the activities like menu, header, fab ..etc
 * <p/>
 * Also, it will be used to log analytics, pass the key for it and it will do so.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Get window demensions (Width and Height)
     * @return array with size=2, first element is width, second element is height
     */
    public int[] getWindowDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int windowDimens[] = new int[2];
        windowDimens[0] = size.x;
        windowDimens[1] = size.y;
        return windowDimens;
    }
}
