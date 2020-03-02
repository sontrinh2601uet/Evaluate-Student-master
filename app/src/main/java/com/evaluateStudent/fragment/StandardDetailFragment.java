package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;

public class StandardDetailFragment extends Fragment {

    public static StandardDetailFragment createInstance(Bundle bundle) {
        StandardDetailFragment standardDetailFragment = new StandardDetailFragment();
        standardDetailFragment.setArguments(bundle);

        return standardDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standard_detail_fragment, null);


        return view;
    }
}
