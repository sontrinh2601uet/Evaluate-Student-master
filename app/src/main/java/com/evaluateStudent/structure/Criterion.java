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

    protected void setPoint() {
        double sum = 0, count = 0;
        for (Action action : listAction) {
            sum += action.getPoint();
            count += (action.getPoint() == 0) ? 0 : 1;
        }

        if(count == 0) count++;
        this.point = sum / count;
    }
}
