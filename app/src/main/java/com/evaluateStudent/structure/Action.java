package com.evaluateStudent.structure;

public class Action extends DataEvaluate {

    private int criteriaId;
    private int rateQuality = 0;

    public Action() {
        super();

    }

    public Action(int iconId, String content, int weight) {
        super(iconId, content, weight);
    }

    public int getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(int criteriaId) {
        this.criteriaId = criteriaId;
    }

    public int getRateQuality() {
        return rateQuality;
    }

    public void setRateQuality(int rateQuality) {
        this.rateQuality = rateQuality;
    }

    public void resetCriteriaId() {
        this.criteriaId = 0;
    }
}
