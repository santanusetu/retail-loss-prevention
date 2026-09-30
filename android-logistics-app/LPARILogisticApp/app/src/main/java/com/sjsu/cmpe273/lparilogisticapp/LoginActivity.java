package com.sjsu.cmpe273.lparilogisticapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sjsu.cmpe273.lparilogisticapp.data.ApiClient;
import com.sjsu.cmpe273.lparilogisticapp.pojo.Credentials;
import com.sjsu.cmpe273.lparilogisticapp.pojo.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Driver login. The 2016 version skipped the check entirely (a timer always "succeeded")
 * and printed the password to the log.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    private EditText emailText, passwordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.etInputEmail);
        passwordText = findViewById(R.id.etInputPassword);
        loginButton = findViewById(R.id.btnLogin);
        TextView signUpLink = findViewById(R.id.tvLinkSignup);

        loginButton.setOnClickListener(v -> {
            if (validate()) {
                login();
            }
        });
        signUpLink.setOnClickListener(v ->
                startActivityForResult(new Intent(this, SignUpActivity.class), REQUEST_SIGNUP));
    }

    private void login() {
        loginButton.setEnabled(false);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage(getString(R.string.authenticating));
        progress.show();

        Credentials credentials = new Credentials(
                emailText.getText().toString().trim(), passwordText.getText().toString());
        ApiClient.get(this).login(credentials).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    HomeActivity.isLoggedIn = true;
                    finish();
                } else {
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
                progress.dismiss();
                onLoginFailed();
            }
        });
    }

    private void onLoginFailed() {
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString().trim()).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }
        if (passwordText.getText().toString().isEmpty()) {
            passwordText.setError("Enter your password");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP && resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.account_created, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        // Login is required; leave the app rather than reveal the home screen
        moveTaskToBack(true);
    }
}
