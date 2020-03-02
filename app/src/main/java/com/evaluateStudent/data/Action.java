package com.evaluateStudent.data;

public class Action extends DataEvaluate {

    private int criteriaId;
    private int rateQuality;

    public Action(int iconId, String content) {
        super(iconId, content);
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
}
