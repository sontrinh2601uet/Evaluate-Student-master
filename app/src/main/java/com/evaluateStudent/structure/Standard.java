package com.evaluateStudent.structure;

import com.evaluateStudent.R;

import java.util.ArrayList;

public class Standard extends DataEvaluate {

    private int imageViewId;
    private int type;
    private ArrayList<Criterion> listCriteria;

    public Standard() {
        super();
        imageViewId = R.drawable.qrcode;
    }

    public Standard(int id, String content, int imageId, int type, int weight) {
        super(id, content, weight);
        imageViewId = imageId;
        this.type = type;
        listCriteria = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public ArrayList<Criterion> getListCriteria() {
        return listCriteria;
    }

    public void setListCriteria(ArrayList<Criterion> listCriteria) {
        this.listCriteria = listCriteria;
    }

    public void addCriteria(Criterion criteria) {
        this.listCriteria.add(criteria);
    }
}
