package com.evaluateStudent.structure;

import java.util.ArrayList;

public class Criterion extends DataEvaluate{

    private int standardId;
    private ArrayList<Action> listAction;

    public Criterion() {
        super();
    }

    public Criterion(int id, String content, int weight) {
        super(id, content, weight);
        listAction = new ArrayList<>();
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public ArrayList<Action> getListAction() {
        return listAction;
    }

    public void setListAction(ArrayList<Action> listAction) {
        this.listAction = listAction;
    }

    public void setListAction(Action action) {
        this.listAction.add(action);
    }
}
