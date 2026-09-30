package com.sjsu.lpari;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity{

    private static final String TAG = "SignUpActivity";

    EditText nameText, emailText, passwordText;
    Button signUpButton;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setUpUI();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Inside Sign Up");

                if (!validate()) {
                    onSignUpFailed();
                    return;
                }

                signUpButton.setEnabled(false);


                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();


                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                // TODO: Implement your own SignUp logic here -- POST it to server


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignUpSuccess or onSignUpFailed
                                // depending on success - if 200 OK
                                onSignUpSuccess();
                                // if error
                                // onSignUpFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });

    }


    public void onSignUpSuccess() {
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }


    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signUpButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("Atleast 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
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
        nameText = (EditText)findViewById(R.id.etInputNameSignUp);
        emailText = (EditText)findViewById(R.id.etInputEmailSignUp);
        passwordText = (EditText)findViewById(R.id.etInputPasswordSignUp);
        signUpButton = (Button)findViewById(R.id.btnSignUp);
        loginLink = (TextView)findViewById(R.id.tvLinkSignup);
    }
}


