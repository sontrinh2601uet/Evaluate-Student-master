package com.evaluateStudent.data;

import java.util.ArrayList;

public class Standard extends DataEvaluate{

    private ArrayList<Criteria> listCriteria;

    public Standard(String name, int iconId, String content) {
        super(name, iconId, content);

        listCriteria = new ArrayList<>();
    }

    public ArrayList<Criteria> getListCriteria() {
        return listCriteria;
    }

    public void setListCriteria(ArrayList<Criteria> listCriteria) {
        this.listCriteria = listCriteria;

        for (Criteria criterion : listCriteria) {
            criterion.setStandardName(this.getName());
        }
    }
}
