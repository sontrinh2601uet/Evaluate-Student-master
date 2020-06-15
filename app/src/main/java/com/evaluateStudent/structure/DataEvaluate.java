package com.evaluateStudent.structure;

public abstract class DataEvaluate {

    private int id;
    private String content;
    protected double weight;
    protected double point;

    public DataEvaluate() {

    }

    DataEvaluate(int id, String content, int weight) {
        this.id = id;
        this.content = content;
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public double getPoint() {
        return point;
    }

    protected abstract void setPoint();
}
