package com.evaluateStudent.data;

import java.util.ArrayList;

public class Criteria extends DataEvaluate{

    private String standardName;
    private ArrayList<Action> listAction;

    public Criteria(String name, int iconId) {
        super(name, iconId);

        listAction = new ArrayList<>();
    }
}
