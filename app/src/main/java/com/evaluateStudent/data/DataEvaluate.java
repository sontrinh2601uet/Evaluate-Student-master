package com.evaluateStudent.data;

public class DataEvaluate {

    private int iconId;
    private String name;
    private String content;

    public DataEvaluate(String name, int iconId, String content) {
        this.name = name;
        this.iconId = iconId;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getContent() {
        return content;
    }
}
