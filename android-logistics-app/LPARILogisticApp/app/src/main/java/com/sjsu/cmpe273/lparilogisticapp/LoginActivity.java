package com.sjsu.cmpe273.lparilogisticapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.sjsu.cmpe273.lparilogisticapp.pojo.Login;
import com.sjsu.cmpe273.lparilogisticapp.pojo.LoginData;
import com.sjsu.cmpe273.lparilogisticapp.retrofit.RetrofitApi;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends Activity {

    public static String API = "http://www.mocky.io"; //v2/571972dd2500000321856cbb
    private static final String PREFERENCES = "login_pref";
    SharedPreferences userSharedPref;
    boolean userPresentFlag = false;

    ProgressDialog  progressDialog;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText, passwordText;
    Button loginButton;
    TextView signUpLink;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpUI();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "Login Button Pressed");
                if (!validate()) {
                    onLoginFailed();
                    return;
                }

                loginButton.setEnabled(false);
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authentication in Progress...");
                progressDialog.show();

                email = emailText.getText().toString();
                password = passwordText.getText().toString();

                //TODO: Implement own Login logic -- email and password is present
                System.out.println(" Email " + email + " password " + password);


                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO:On complete call either onLoginSuccess or onLoginFailed
                        //if boolean Success

                        //santanu --- commenting for time being
                         onLoginSuccess();

                        // else boolean notSuccess
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }




    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        HomeActivity.isLoggedIn = true;
        finish();
    }


    private boolean validate() {
        boolean valid = true;
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter valid Email address");
            valid = false;
        }else{
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }



    private void setUpUI() {
        emailText = (EditText)findViewById(R.id.etInputEmail);
        passwordText = (EditText)findViewById(R.id.etInputPassword);
        loginButton = (Button)findViewById(R.id.btnLogin);
        signUpLink = (TextView)findViewById(R.id.tvLinkSignup);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SIGNUP){
            if(resultCode == RESULT_OK){
                // TODO: Implement successful signup logic here -- We can have logic like authenticate via email - then login
                // By default in the end we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }



}
