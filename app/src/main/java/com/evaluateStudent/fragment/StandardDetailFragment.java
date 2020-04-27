package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;
import com.evaluateStudent.structure.Standard;

public class StandardDetailFragment extends Fragment {

    private static Standard standard;

    public static StandardDetailFragment createInstance(Standard show) {
        StandardDetailFragment standardDetailFragment = new StandardDetailFragment();
        Bundle bundle = new Bundle();

        standard = show;
        return standardDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standard_detail_fragment, null);


        return view;
    }
}
