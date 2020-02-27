package com.evaluateStudent.data;

public class Action extends DataEvaluate{

    private String criteriaName;
    private int rateQuality;

    public Action(String name, int iconId) {
        super(name, iconId);
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public int getRateQuality() {
        return rateQuality;
    }

    public void setRateQuality(int rateQuality) {
        this.rateQuality = rateQuality;
    }
}
