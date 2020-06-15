package com.evaluateStudent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.EvaluateHistoryFragment;
import com.evaluateStudent.fragment.ShowListStandardFragment;
import com.evaluateStudent.structure.ConnectToData;

import org.json.JSONException;
import org.json.JSONObject;


public class EvaluateStudentActivity extends AppCompatActivity implements View.OnClickListener {

    Button saveButton, backButton, historyButton;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        displayInfoStudent(getIntent());

        saveButton = findViewById(R.id.save);
        backButton = findViewById(R.id.back);
        historyButton = findViewById(R.id.history);

        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
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
    }

    private boolean showInfo(JSONObject studentData) {
        try {
            ((TextView) findViewById(R.id.id_student)).setText(studentData.getString("studentId"));
            ((TextView) findViewById(R.id.name_student)).setText(studentData.getString("name"));
            ((TextView) findViewById(R.id.dob_student)).setText(studentData.getString("dob"));
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
        builder.setCancelable(false);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.return_and_save_evaluate_title);
        builder.setMessage(R.string.return_and_save_evaluate);
        builder.setPositiveButton(R.string.return_and_save_evaluate_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveEvaluate();
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
        backButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (backButton.isEnabled()) {
            getSupportFragmentManager().popBackStack();
        } else {
            commitBackToScan();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveEvaluate();
                break;
            case R.id.back:
                backButton.setEnabled(false);
                getSupportFragmentManager().popBackStack();
                break;
            case R.id.history:
                backButton.setEnabled(true);
                String studentId = getIntent().getStringExtra("studentId");

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, EvaluateHistoryFragment.createInstance(studentId), "detail")
                        .addToBackStack("list")
                        .commit();
                break;
        }
    }

    private void saveEvaluate() {
        ConnectToData.saveEvaluate(studentId, this.getSharedPreferences("MyPref", 0));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.after_save_evaluate_title);
        builder.setMessage(R.string.after_save_evaluate_mess);
        builder.setPositiveButton(R.string.after_save_evaluate_continue,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        backButton.callOnClick();
                        dialogInterface.dismiss();
                    }
                });
        builder.setNegativeButton(R.string.after_save_evaluate_back,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        backToScanActivity();
                    }
                });
        builder.show();
    }
}
