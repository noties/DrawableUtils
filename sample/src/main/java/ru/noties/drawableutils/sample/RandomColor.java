package ru.noties.drawableutils.sample;

import java.util.Random;

/**
 * Created by Dimitry Ivanov on 08.09.2015.
 */
public class RandomColor {

    private final Random mRandom;
    private final int[] mColors;

    private int mPrev = 0;

    public RandomColor(int[] colors) {
        this.mRandom = new Random();
        this.mColors = colors;
    }

    public int next() {
        int out = mPrev;
        while (mPrev == out) {
            out = mColors[mRandom.nextInt(mColors.length)];
        }
        mPrev = out;
        return out;
    }
}
