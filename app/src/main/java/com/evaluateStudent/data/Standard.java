package com.evaluateStudent.data;

import java.util.ArrayList;

public class Standard extends DataEvaluate {

    private int imageViewId;
    private ArrayList<Criteria> listCriteria;

    public Standard(int id, String content) {
        super(id, content);
        listCriteria = new ArrayList<>();
    }

    public Standard(int id, String content, int imageId) {
        super(id, content);
        imageViewId = imageId;
        listCriteria = new ArrayList<>();
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public ArrayList<Criteria> getListCriteria() {
        return listCriteria;
    }

    public void setListCriteria(ArrayList<Criteria> listCriteria) {
        this.listCriteria = listCriteria;

        for (Criteria criterion : listCriteria) {
            criterion.setStandardId(this.getId());
        }
    }
}
