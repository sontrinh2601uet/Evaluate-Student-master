package com.evaluateStudent.structure;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class Criterion extends DataEvaluate {

    private ArrayList<Action> listAction;

    private LinearLayout listActionView;

    public Criterion() {
        super();
    }

    public Criterion(int id, String content, int weight) {
        super(id, content, weight);
        listAction = new ArrayList<>();
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

    public LinearLayout getListActionView() {
        return listActionView;
    }

    public void setListActionView(LinearLayout listActionView) {
        this.listActionView = listActionView;
    }
}
