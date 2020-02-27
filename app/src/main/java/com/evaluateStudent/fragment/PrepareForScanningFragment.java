package com.evaluateStudent.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.EvaluateStudentActivity;
import com.evaluateStudent.R;
import com.evaluateStudent.ScanQRcodeActivity;

public class PrepareForScanningFragment extends Fragment implements View.OnClickListener {


    public static PrepareForScanningFragment createInstance(String emailId) {
        PrepareForScanningFragment prepare = new PrepareForScanningFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", emailId);
        prepare.setArguments(bundle);

        return prepare;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prepare_scan, container, false);
        Button logOut = view.findViewById(R.id.btn_log_out);
        Button scan = view.findViewById(R.id.btn_scan);

        logOut.setOnClickListener(this);
        scan.setOnClickListener(this);

        getDataFromUser();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                removeAutoLogin();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainer, LoginFragment.createInstance())
                        .commit();
                break;
            case R.id.btn_scan:
                startActivity(new Intent(getActivity(), EvaluateStudentActivity.class));
                break;
        }

    }

    private void removeAutoLogin() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("pass");

        editor.commit();
    }

    private void getDataFromUser() {
        // TODO: get list standard user can evaluate
    }
}
