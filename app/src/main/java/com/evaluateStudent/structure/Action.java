package com.evaluateStudent.structure;

public class Action extends DataEvaluate {

    private int rateQuality = 0;
    private boolean positive = true;

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
        setPoint();
    }

    public void resetRateQuality() {
        this.rateQuality = 0;
    }

    @Override
    protected void setPoint() {
        this.point = this.weight * rateQuality;
    }
}
