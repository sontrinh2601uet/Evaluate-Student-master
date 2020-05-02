package com.evaluateStudent.structure;

import android.widget.LinearLayout;

import com.evaluateStudent.R;

public class Action extends DataEvaluate {

    private int rateQuality = 0;

    public Action() {
        super();
    }

    public Action(int iconId, String content, int weight) {
        super(iconId, content, weight);
    }

    public int getRateQuality() {
        return rateQuality;
    }

    public void setRateQuality(int rateQuality) {
        this.rateQuality = rateQuality;
    }

    public void resetRateQuality() {
        this.rateQuality = 0;
    }
}
