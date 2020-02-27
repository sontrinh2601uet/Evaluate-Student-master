package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;

public class ShowListStandardFragment extends Fragment {

    public static ShowListStandardFragment createInstance() {
        return new ShowListStandardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_standard, null);

        return view;
    }
}
