package com.evaluateStudent.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;
import com.evaluateStudent.ScanQRcodeActivity;

public class PrepareForScanningFragment extends Fragment implements View.OnClickListener {


    public static PrepareForScanningFragment createInstance(int typeUser) {
        PrepareForScanningFragment prepare = new PrepareForScanningFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", typeUser);
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

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                removeAutoLogin();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.right_out)
                        .replace(R.id.frameContainer, LoginFragment.createInstance())
                        .commit();
                break;
            case R.id.btn_scan:
                startActivity(new Intent(getActivity(), ScanQRcodeActivity.class));
                break;
        }

    }

    private void removeAutoLogin() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("type");

        editor.commit();
    }
}
