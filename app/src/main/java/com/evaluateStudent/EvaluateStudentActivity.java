package com.evaluateStudent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.ShowListStandardFragment;


public class EvaluateStudentActivity extends AppCompatActivity {

    private static final int CONTENT_VIEW_ID = 10101010;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);
        FrameLayout frame = new FrameLayout(this);
        displayInfoStudent(getIntent());

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
