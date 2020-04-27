package com.evaluateStudent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.ShowListStandardFragment;
import com.evaluateStudent.structure.Action;
import com.evaluateStudent.structure.ConnectToData;
import com.evaluateStudent.structure.Criterion;
import com.evaluateStudent.structure.Standard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EvaluateStudentActivity extends AppCompatActivity {

    public static ArrayList<Standard> listStandard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        clearEvaluate();
        displayInfoStudent(getIntent());

    }

    private void clearEvaluate() {
        if(listStandard != null) {
            for (Standard standard : listStandard) {
                for (Criterion criterion : standard.getListCriteria()) {
                    for (Action action : criterion.getListAction()) {
                        action.resetCriteriaId();
                    }
                }
            }
        }
    }

    private void displayInfoStudent(Intent intent) {
        String studentId = intent.getStringExtra("studentId");
        JSONObject studentData = ConnectToData.getStudentInfo(studentId, this);

        if(studentData == null) {
            backToScan();
        } else if (showInfo(studentData)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, ShowListStandardFragment.createInstance(), "list").commit();
        } else {
            backToScan();
        }

        // remove
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, ShowListStandardFragment.createInstance(), "list").commit();
    }

    private boolean showInfo(JSONObject studentData) {
        try {
            ((TextView) findViewById(R.id.id_student)).setText(studentData.getString("studentId"));
            ((TextView) findViewById(R.id.name_student)).setText(studentData.getString("name"));
            ((TextView) findViewById(R.id.dob_student)).setText(studentData.getString("bod"));
            ((TextView) findViewById(R.id.class_student)).setText(studentData.getString("class"));

            String dob = (studentData.getString("gender").equals("1")) ? "Nam" : "Nữ";
            ((TextView) findViewById(R.id.gender_student)).setText(dob);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void backToScan() {
        String mess = getString(R.string.cannot_found_student_info,
                getIntent().getStringExtra("studentId"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(mess);
        builder.setPositiveButton(R.string.cannot_found_student_info_rescan,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        backToScanActivity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean commitBackToScan() {
        final boolean[] commit = new boolean[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.return_and_save_evaluate_title);
        builder.setMessage(R.string.return_and_save_evaluate);
        builder.setPositiveButton(R.string.return_and_save_evaluate_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        commit[0] = true;
                    }
                });
        builder.setNegativeButton(R.string.return_and_save_evaluate_no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        commit[0] = false;
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

        return commit[0];
    }

    private void saveEvaluate() {
        if(commitBackToScan()) {
            ConnectToData.saveEvaluate(listStandard);
        }
    }

    private void backToScanActivity() {
        startActivity(new Intent(this, ScanQRcodeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        saveEvaluate();
        super.onDestroy();
    }


}
