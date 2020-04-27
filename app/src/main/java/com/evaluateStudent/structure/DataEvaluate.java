package com.evaluateStudent.structure;

public class DataEvaluate {

    private int id;
    private String content;
    private int weight;

    public DataEvaluate() {

    }

    DataEvaluate(int id, String content, int weight) {
        this.id = id;
        this.content = content;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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
}
