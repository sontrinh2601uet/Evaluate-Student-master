package com.evaluateStudent.structure;

public class Action extends DataEvaluate {

    private int rateQuality = 0;

    public Action() {
        super();
    }

    public Action(int id, String content, int weight) {
        super(id, content, weight);
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

    @Override
    protected void setPoint() {}
}
