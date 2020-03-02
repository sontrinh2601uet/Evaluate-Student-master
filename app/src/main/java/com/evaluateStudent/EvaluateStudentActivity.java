package com.evaluateStudent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.ShowListStandardFragment;


public class EvaluateStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        displayInfoStudent(getIntent());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, ShowListStandardFragment.createInstance()).commit();
    }

    private String getIdStudent() {
        return getIntent().getStringExtra("studentId");
    }

    private void displayInfoStudent(Intent intent) {
        // TODO: Modify qr data to info student data
    }

    class Student {

    }
}
