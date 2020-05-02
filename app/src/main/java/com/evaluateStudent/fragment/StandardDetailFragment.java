package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;
import com.evaluateStudent.customLayout.ActionLayout;
import com.evaluateStudent.structure.Action;
import com.evaluateStudent.structure.Criterion;

import java.util.HashMap;

import static com.evaluateStudent.structure.ConnectToData.listStandard;

public class StandardDetailFragment extends Fragment implements View.OnClickListener {

    private boolean isCollapse = false;
    private static int standardIndex;

    private HashMap<String, LinearLayout> mapCriterionView;


    public static StandardDetailFragment createInstance(int id) {
        StandardDetailFragment standardDetailFragment = new StandardDetailFragment();

        standardIndex = id;
        return standardDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standard_detail_fragment, null);
        ((TextView) view.findViewById(R.id.standard_content)).setText(listStandard.get(standardIndex).getContent());
        mapCriterionView = new HashMap<>();

        LinearLayout listCriterion = view.findViewById(R.id.list_criterion);
        for (Criterion criterion : listStandard.get(standardIndex).getListCriteria()) {
            listCriterion.addView(createCriterionView(criterion));
        }

        return view;
    }

    private View createCriterionView(Criterion criterion) {
        LinearLayout criterionView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.criterion_view, null);
        TextView content = criterionView.findViewById(R.id.criterion_content);
        LinearLayout listAction = criterionView.findViewById(R.id.list_action);

        content.setText(criterion.getContent());
        content.setOnClickListener(this);

        mapCriterionView.put(criterion.getContent(), listAction);

        for (Action action : criterion.getListAction()) {
            listAction.addView(createActionView(action));
        }

        criterion.setListActionView(listAction);
        return criterionView;
    }

    private View createActionView(Action action) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.action_view, null);
        ActionLayout actionView = new ActionLayout(getContext(), linearLayout, action);

        return actionView.getView();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.criterion_content) {
            showListAction(v);
        }
    }

    private void showListAction(View v) {
        TextView content = v.findViewById(R.id.criterion_content);
        LinearLayout listAction = mapCriterionView.get(content.getText().toString());
        isCollapse = !isCollapse;

        listAction.setVisibility(isCollapse ? View.GONE : View.VISIBLE);
    }
}
