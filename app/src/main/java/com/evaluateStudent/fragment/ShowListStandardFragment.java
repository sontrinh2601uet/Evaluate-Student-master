package com.evaluateStudent.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evaluateStudent.R;
import com.evaluateStudent.structure.ConnectToData;
import com.evaluateStudent.structure.RecyclerViewAdapter;

import static com.evaluateStudent.EvaluateStudentActivity.listStandard;

public class ShowListStandardFragment extends Fragment {

    public static ShowListStandardFragment createInstance() {
        return new ShowListStandardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_standard, null);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        int typeUser = pref.getInt("type", -1);

        listStandard = ConnectToData.getListStandard(typeUser);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), listStandard, getActivity().getSupportFragmentManager());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}
