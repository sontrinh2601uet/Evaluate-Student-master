package com.evaluateStudent.data;

import java.util.ArrayList;

public class Standard extends DataEvaluate{

    private ArrayList<Criteria> listCriteria;

    public Standard(String name, int iconId) {
        super(name, iconId);

        listCriteria = new ArrayList<>();
    }
}
