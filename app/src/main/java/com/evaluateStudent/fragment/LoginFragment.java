package com.evaluateStudent.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.evaluateStudent.R;
import com.evaluateStudent.structure.ConnectToData;

public class LoginFragment extends Fragment implements OnClickListener {

    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private View view;
    private EditText emailId, password;
    private Button loginButton;
    private CheckBox show_hide_password;
    private LinearLayout loginLayout;

    public static LoginFragment createInstance() {
        LoginFragment login = new LoginFragment();
        return login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        // Create connect to Data
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailId = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        getSavedLogin();
        // Setting text selector over textviews
        @SuppressLint("ResourceType")
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            show_hide_password.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    show_hide_password.setText(R.string.hide_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    show_hide_password.setText(R.string.show_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginBtn) {
            int type = checkValidation();
            if (type != -1) {
                Fragment prepare = PrepareForScanningFragment.createInstance(type);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, prepare)
                        .commit();
            }
        }

    }


    // Check Validation before login
    private int checkValidation() {
        // Get email id and password
        String getEmailId = emailId.getText().toString();
        String getPassword = password.getText().toString();
        int typeUser = ConnectToData.getAuthenticationAccess(getContext(), getEmailId, getPassword);

        if (typeUser != -1) {
            saveLogin(getEmailId, getPassword, typeUser);
            return typeUser;
        } else {
            Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
            return -1;
        }
    }

    private void getSavedLogin() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);

        emailId.setText(pref.getString("email", null));
        password.setText(pref.getString("pass", null));
    }

    private void saveLogin(String email, String passWord, int typeUSer) {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("email", email);
        editor.putString("pass", passWord);
        editor.putInt("type", typeUSer);

        editor.commit();
    }


}
