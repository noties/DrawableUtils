package ru.noties.drawableutils;

import android.util.StateSet;

/**
 * Created by Dimitry Ivanov on 06.09.2015.
 */
public enum SelectorState {

    CHECKED     (true, new int[] { android.R.attr.state_checked }),
    SELECTED    (true, new int[] { android.R.attr.state_selected }),
    PRESSED     (true, new int[] { android.R.attr.state_pressed }),
    FOCUSED     (true, new int[] { android.R.attr.state_focused }),
    ACTIVATED   (true, new int[] { android.R.attr.state_activated }),
    WILD_CARD   (false, StateSet.WILD_CARD);

    private final boolean mIsHighLight;
    private final int[] mStates;

    SelectorState(boolean isHighLight, int[] states) {
        this.mIsHighLight = isHighLight;
        this.mStates = states;
    }

    public int[] getStates() {
        return mStates;
    }

    public boolean isHighLight() {
        return mIsHighLight;
    }
}
