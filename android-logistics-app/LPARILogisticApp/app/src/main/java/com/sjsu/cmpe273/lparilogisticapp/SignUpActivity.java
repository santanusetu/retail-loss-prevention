package com.sjsu.cmpe273.lparilogisticapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sjsu.cmpe273.lparilogisticapp.data.ApiClient;
import com.sjsu.cmpe273.lparilogisticapp.pojo.SignUpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Driver registration. The 2016 version put the password in a plain-HTTP URL and logged it,
 * read "confirm password" from the phone field, and rejected matching passwords.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText nameText, emailText, passwordText, confirmPasswordText,
            addressText, stateText, zipCodeText, contactPhoneText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = findViewById(R.id.etInputNameSignUp);
        emailText = findViewById(R.id.etInputEmailSignUp);
        passwordText = findViewById(R.id.etInputPasswordSignUp);
        confirmPasswordText = findViewById(R.id.etConfirmInputPasswordSignUp);
        addressText = findViewById(R.id.etAddress);
        stateText = findViewById(R.id.etState);
        zipCodeText = findViewById(R.id.etZipCOde);
        contactPhoneText = findViewById(R.id.etContact);
        signUpButton = findViewById(R.id.btnSignUp);
        TextView loginLink = findViewById(R.id.tvLinkSignup);

        signUpButton.setOnClickListener(v -> {
            if (validate()) {
                signUp();
            }
        });
        loginLink.setOnClickListener(v -> finish());
    }

    private void signUp() {
        signUpButton.setEnabled(false);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage(getString(R.string.creating_account));
        progress.show();

        SignUpRequest request = new SignUpRequest(
                text(nameText), text(emailText), passwordText.getText().toString(),
                text(addressText), text(stateText), text(zipCodeText), text(contactPhoneText));

        ApiClient.get(this).signUp(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                progress.dismiss();
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    onSignUpFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                progress.dismiss();
                onSignUpFailed();
            }
        });
    }

    private void onSignUpFailed() {
        Toast.makeText(this, R.string.signup_failed, Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String password = passwordText.getText().toString();

        valid &= check(nameText, text(nameText).length() >= 3, "At least 3 characters");
        valid &= check(emailText, Patterns.EMAIL_ADDRESS.matcher(text(emailText)).matches(), "Enter a valid email address");
        valid &= check(passwordText, password.length() >= 8, "At least 8 characters");
        valid &= check(confirmPasswordText, password.equals(confirmPasswordText.getText().toString()), "Passwords do not match");
        valid &= check(addressText, !text(addressText).isEmpty(), "Address needs to be specified");
        valid &= check(stateText, !text(stateText).isEmpty(), "Enter state");
        valid &= check(zipCodeText, text(zipCodeText).matches("\\d{5}(-\\d{4})?"), "Enter a valid ZIP code");
        valid &= check(contactPhoneText, Patterns.PHONE.matcher(text(contactPhoneText)).matches(), "Enter a valid phone number");
        return valid;
    }

    private static boolean check(EditText field, boolean ok, String error) {
        field.setError(ok ? null : error);
        return ok;
    }

    private static String text(EditText field) {
        return field.getText().toString().trim();
    }
}
