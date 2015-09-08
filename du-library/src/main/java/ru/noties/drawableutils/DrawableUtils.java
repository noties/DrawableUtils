package ru.noties.drawableutils;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by Dimitry Ivanov on 06.09.2015.
 */
public class DrawableUtils {

    private DrawableUtils() {}

    public static Drawable setColor(@NonNull Drawable drawable, int color) {
        final Drawable out = drawable.mutate();
        out.setColorFilter(getColorFilter(color));
        return out;
    }

    public static void intrinsicBounds(Drawable... drawables) {

        if (drawables == null
                || drawables.length == 0) {
            return;
        }

        for (Drawable drawable: drawables) {
            drawable.setBounds(
                    0,
                    0,
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight()
            );
        }
    }

    public static ColorFilter getColorFilter(int color) {
        return new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }
}
