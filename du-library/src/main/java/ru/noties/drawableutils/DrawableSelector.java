package ru.noties.drawableutils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

/**
 * Created by Dimitry Ivanov on 06.09.2015.
 */
public class DrawableSelector {

    private static final int NO_DURATION = -1;
    private static final int STATES_LENGTH = SelectorState.values().length;

    public static DrawableSelector.Builder simple(
            @NonNull Context context,
            @ColorRes int normalColorRes,
            @ColorRes int highLightColorRes) {
        final SelectorState[] states = SelectorState.values();
        final Builder builder = builder(context);
        for (SelectorState state: states) {
            builder.addState(state, state.isHighLight() ? highLightColorRes : normalColorRes);
        }
        return builder;
    }

    public static DrawableSelector.Builder builder(@NonNull Context context) {
        return new Builder(context);
    }

    private final Context mContext;
    private final int[] mItems;
    private final int mEnterFadeDuration;
    private final int mExitFadeDuration;

    private DrawableSelector(Builder builder) {
        this.mContext = builder.mContext;
        this.mItems = builder.mItems;
        this.mEnterFadeDuration = builder.enterFadeDuration;
        this.mExitFadeDuration = builder.exitFadeDuration;
    }

    public Drawable build(@DrawableRes int drawableRes) {

        final int length = mItems.length;

        // reuse drawables with the same color
        final SparseArray<Drawable> cache = new SparseArray<Drawable>(length);

        final StateListDrawable stateListDrawable = new StateListDrawable();
        final SelectorState[] states = SelectorState.values();

        SelectorState state;
        Drawable drawable;
        @ColorRes int color;

        for (int i = 0; i < length; i++) {

            color = mItems[i];

            // 0 is not a valid resource identifier
            if (color == 0) {
                continue;
            }

            state       = states[i];
            drawable    = cache.get(color);

            if (drawable == null) {
                // we don't call mutate() here, because DrawableUtils mutate Drawable before setting colorFilter
                drawable = ContextCompat.getDrawable(mContext, drawableRes);
                drawable = DrawableUtils.setColor(drawable, ContextCompat.getColor(mContext, color));
                drawable = DrawableWrapper.wrap(drawable);
                DrawableUtils.intrinsicBounds(drawable);
                cache.put(color, drawable);
            }

            stateListDrawable.addState(state.getStates(), drawable);
        }

        if (mEnterFadeDuration != NO_DURATION) {
            stateListDrawable.setEnterFadeDuration(mEnterFadeDuration);
        }

        if (mExitFadeDuration != NO_DURATION) {
            stateListDrawable.setExitFadeDuration(mExitFadeDuration);
        }

        return stateListDrawable;
    }

    public static class Builder {

        private final Context mContext;
        private final int[] mItems;

        private int enterFadeDuration   = NO_DURATION;
        private int exitFadeDuration    = NO_DURATION;

        public Builder(@NonNull Context context) {
            mContext = context;
            mItems = new int[STATES_LENGTH];
        }

        public Builder addState(@NonNull SelectorState state, @ColorRes int colorRes) {
            mItems[state.ordinal()] = colorRes;
            return this;
        }

        public Builder setEnterFadeDuration(int enterFadeDuration) {
            this.enterFadeDuration = enterFadeDuration;
            return this;
        }

        public Builder setExitFadeDuration(int exitFadeDuration) {
            this.exitFadeDuration = exitFadeDuration;
            return this;
        }

        public DrawableSelector build() {
            return new DrawableSelector(this);
        }
    }
}
