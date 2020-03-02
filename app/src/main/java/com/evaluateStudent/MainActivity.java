package com.evaluateStudent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evaluateStudent.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, LoginFragment.createInstance()).commit();
        }

    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, LoginFragment.createInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        replaceLoginFragment();
        super.onBackPressed();
    }
}
