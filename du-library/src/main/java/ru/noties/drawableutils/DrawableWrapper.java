package ru.noties.drawableutils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * To be used with selector. DrawableContainer somehow sets its own ColorFilter
 * Created by Dimitry Ivanov on 06.09.2015.
 */
public class DrawableWrapper extends Drawable {

    public static Drawable wrap(Drawable toWrap) {
        return new DrawableWrapper(toWrap);
    }

    public static Drawable unwrap(Drawable toUnWrap) {
        if (toUnWrap instanceof DrawableWrapper) {
            return ((DrawableWrapper) toUnWrap).mDrawable;
        }

        // just return
        return toUnWrap;
    }

    private final Drawable mDrawable;

    private DrawableWrapper(Drawable drawable) {
        this.mDrawable = drawable;
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawable.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }

    @Override
    public void onBoundsChange(Rect newBounds) {
        mDrawable.setBounds(newBounds);
        super.onBoundsChange(newBounds);
    }

    @Override
    public void invalidateSelf() {
        mDrawable.invalidateSelf();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mDrawable.setBounds(left, top, right, bottom);
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setBounds(Rect bounds) {
        mDrawable.setBounds(bounds);
        super.setBounds(bounds);
    }

    @Override
    public void setDither(boolean dither) {
        mDrawable.setDither(dither);
        super.setDither(dither);
    }

    @Override
    public boolean isStateful() {
        return mDrawable.isStateful();
    }

    @Override
    public boolean setState(int[] state) {
        return mDrawable.setState(state);
    }

    @Override
    public int[] getState() {
        return mDrawable.getState();
    }

    @Override
    public boolean onLevelChange(int level) {
        return mDrawable.setLevel(level);
    }

    @Override
    public boolean onStateChange(int[] state) {
        return mDrawable.setState(state);
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }
}
