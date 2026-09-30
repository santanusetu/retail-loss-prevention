package com.sjsu.cmpe273.lparilogisticapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.cmpe273.lparilogisticapp.retrofit.RetrofitApi;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SignUpActivity extends Activity implements Callback<String> {

    public static String SIGNUP_API = "http://running1-env.us-west-2.elasticbeanstalk.com/test/signup/"; //v2/571972dd2500000321856cbb

    private static final String TAG = "SignUpActivity";

    EditText nameText, emailText, passwordText, confirmPasswordText, addressText, stateText, zipCodeText, contactPhoneText;
    Button signUpButton;
    TextView loginLink;

    String nameSt, emailSt, passwordSt, confirmPasswordSt, addressSt, stateSt, zipSt, contactPhoneSt;


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


                nameSt = nameText.getText().toString();
                emailSt = emailText.getText().toString();
                passwordSt = passwordText.getText().toString();

                //TODO: NEW BLOCK
                confirmPasswordSt = contactPhoneText.getText().toString();
                addressSt = addressText.getText().toString();
                stateSt = stateText.getText().toString();
                zipSt = zipCodeText.getText().toString();
                contactPhoneSt = contactPhoneText.getText().toString();


                // TODO: Implement your own SignUp logic here -- POST it to server
                if (addressSt.isEmpty() || addressSt.length() < 0) {
                    addressSt = "Apt #312, 56 S 2nd St, San Jose";
                }


                //TODO: Retrofit call
                //santanu -- adding new login check
                signUpRetrofitCheck();


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

        //TODO: NEW BLOCK
        String confirmPasswordSt = contactPhoneText.getText().toString();
        String addressSt = addressText.getText().toString();
        String stateSt = stateText.getText().toString();
        String zipcodeSt = zipCodeText.getText().toString();
        String contactPhoneSt = contactPhoneText.getText().toString();

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

        if (password.equalsIgnoreCase(confirmPasswordSt)) {
            confirmPasswordText.setError("Passwords do not match");
            valid = false;
        } else {
            confirmPasswordText.setError(null);
        }


        if (addressSt.isEmpty() || addressSt.length() < 0) {
            addressText.setError("Address needs to be specified");
            valid = false;
        } else {
            addressText.setError(null);
        }

        if (zipcodeSt.isEmpty() || zipcodeSt.length() < 0) {
            zipCodeText.setError("Enter proper ZipCode ");
            valid = false;
        } else {
            zipCodeText.setError(null);
        }

        if (stateSt.isEmpty() || stateSt.length() < 0) {
            stateText.setError("Enter State");
            valid = false;
        } else {
            stateText.setError(null);
        }

        if (contactPhoneSt.isEmpty() || contactPhoneSt.length() < 0) {
            contactPhoneText.setError("Enter Contact Details");
            valid = false;
        } else {
            contactPhoneText.setError(null);
        }
        return valid;
    }


    private void setUpUI() {
        nameText = (EditText) findViewById(R.id.etInputNameSignUp);
        emailText = (EditText) findViewById(R.id.etInputEmailSignUp);
        passwordText = (EditText) findViewById(R.id.etInputPasswordSignUp);


        confirmPasswordText = (EditText) findViewById(R.id.etConfirmInputPasswordSignUp);
        addressText = (EditText) findViewById(R.id.etAddress);
        stateText = (EditText) findViewById(R.id.etState);
        zipCodeText = (EditText) findViewById(R.id.etZipCOde);
        contactPhoneText = (EditText) findViewById(R.id.etContact);


        signUpButton = (Button) findViewById(R.id.btnSignUp);
        loginLink = (TextView) findViewById(R.id.tvLinkSignup);
    }


    private void signUpRetrofitCheck() {

        //http://running1-env.us-west-2.elasticbeanstalk.com/test/signup/
        // alex/alex1/alex%20parrish/23/First%20Street/San%20Jose/CA/95113/alex.p@gmail.com/9898121245

        String[] addressPart = addressSt.split(",");
        String aptNo = addressPart[0];
        String street = addressPart[1];
        String city = addressPart[2];

        String[] emailNamePart = emailSt.split("@");
        String userName = emailNamePart[0];


        userName = userName.trim();
        passwordSt = passwordSt.trim();
        nameSt = nameSt.trim();
        aptNo = aptNo.trim();
        street = street.trim();
        city = city.trim();
        stateSt = stateSt.trim();
        zipSt = zipSt.trim();
        emailSt = emailSt.trim();
        contactPhoneSt = contactPhoneSt.trim();

        SIGNUP_API = SIGNUP_API + userName + "/" + passwordSt + "/" + nameSt + "/" + aptNo + "/" + street + "/" + city + "/" + stateSt + "/" + zipSt + "/" + emailSt + "/" + contactPhoneSt;


        SIGNUP_API = SIGNUP_API.replace(" ", "");

        System.out.println("@@@@@@@ SIGNUP_API " + SIGNUP_API);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SIGNUP_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        RetrofitApi retApi = retrofit.create(RetrofitApi.class);

        Call<String> call = retApi.loadSignUpData(SIGNUP_API);
        //retApi.loadLoginData("santanu.setu@gmail.com");

        call.enqueue(this);
    }


    @Override
    public void onResponse(Response<String> response, Retrofit retrofit) {

        System.out.println("@@@@ response 1 " + response.body().toString());

    }

    @Override
    public void onFailure(Throwable t) {
        //Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        System.out.println("******* Sign Up error " + t.getLocalizedMessage());
    }


}


