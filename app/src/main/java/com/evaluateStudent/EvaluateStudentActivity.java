package com.evaluateStudent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.ShowListStandardFragment;
import com.evaluateStudent.structure.ConnectToData;
import com.evaluateStudent.structure.Standard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EvaluateStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private String studentId;

    LinearLayout saveView, backView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        ConnectToData.clearEvaluate();
        displayInfoStudent(getIntent());

        saveView = findViewById(R.id.save_layout);
        backView = findViewById(R.id.back_layout);
        saveView.setOnClickListener(this);
        backView.setOnClickListener(this);

    }

    private void displayInfoStudent(Intent intent) {
        String studentId = intent.getStringExtra("studentId");
        JSONObject studentData = ConnectToData.getStudentInfo(studentId, this);

        if (studentData == null) {
            backToScan();
        } else if (showInfo(studentData)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, ShowListStandardFragment.createInstance(), "list").commit();
        } else {
            backToScan();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, ShowListStandardFragment.createInstance(), "list").commit();
    }

    private boolean showInfo(JSONObject studentData) {
        try {
            ((TextView) findViewById(R.id.id_student)).setText(studentData.getString("studentId"));
            ((TextView) findViewById(R.id.name_student)).setText(studentData.getString("name"));
            ((TextView) findViewById(R.id.dob_student)).setText(studentData.getString("bod"));
            ((TextView) findViewById(R.id.class_student)).setText(studentData.getString("class"));

            studentId = studentData.getString("studentId");
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

    private void commitBackToScan() {
        final Activity activity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.return_and_save_evaluate_title);
        builder.setMessage(R.string.return_and_save_evaluate);
        builder.setPositiveButton(R.string.return_and_save_evaluate_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ConnectToData.saveEvaluate(studentId, activity);
                        backToScanActivity();
                        dialogInterface.dismiss();
                    }
                });
        builder.setNegativeButton(R.string.return_and_save_evaluate_no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        backToScanActivity();
                    }
                });
        builder.setNeutralButton(R.string.return_and_save_evaluate_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void backToScanActivity() {
        startActivity(new Intent(this, ScanQRcodeActivity.class));
        finish();
    }

    public void changeStatus() {
        backView.setVisibility(View.VISIBLE);
        saveView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (saveView.getVisibility() == View.VISIBLE) {
            commitBackToScan();
        }
        if (backView.getVisibility() == View.VISIBLE) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        ConnectToData.saveEvaluate(studentId, this);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getTag().toString()) {
            case "save":
                ConnectToData.saveEvaluate(studentId, this);
                Toast.makeText(this, "Đã lưu đánh giá!", Toast.LENGTH_SHORT).show();
                break;
            case "back":
                backView.setVisibility(View.INVISIBLE);
                saveView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
