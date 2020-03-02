package com.evaluateStudent.data;

public class DataEvaluate {

    private int id;
    private String content;

    DataEvaluate(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
