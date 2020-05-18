package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;
import com.evaluateStudent.customLayout.ActionLayout;
import com.evaluateStudent.structure.Action;
import com.evaluateStudent.structure.Criterion;
import com.evaluateStudent.structure.Standard;

import static com.evaluateStudent.structure.ConnectToData.listStandard;

public class StandardDetailFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private static int standardIndex;

    private SearchView search;

    public static StandardDetailFragment createInstance(int id) {
        StandardDetailFragment standardDetailFragment = new StandardDetailFragment();

        standardIndex = id;
        return standardDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standard_detail_fragment, null);
        Standard standard = listStandard.get(standardIndex);
        ((TextView) view.findViewById(R.id.standard_content)).setText(standard.getContent());
        search = view.findViewById(R.id.search_item_layout);
        search.setOnQueryTextListener(this);

        LinearLayout listCriterion = view.findViewById(R.id.list_criterion);
        for (Criterion criterion : standard.getListCriteria()) {
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
        content.setTag(listAction);

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
            TextView content = v.findViewById(R.id.criterion_content);
            LinearLayout listAction = (LinearLayout) content.getTag();

            if(listAction.getVisibility() == View.GONE) {
                listAction.setVisibility(View.VISIBLE);
            } else {
                listAction.setVisibility(View.GONE);
            }
        }
        if(v.getId() == R.id.search_item_layout) {
            search.requestFocus();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
