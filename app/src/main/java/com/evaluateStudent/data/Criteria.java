package com.evaluateStudent.data;

import java.util.ArrayList;

public class Criteria extends DataEvaluate{

    private int standardId;
    private ArrayList<Action> listAction;

    public Criteria(int id, String content) {
        super(id, content);
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

        for (Action action : listAction) {
            action.setCriteriaId(this.getId());
        }
    }
}
