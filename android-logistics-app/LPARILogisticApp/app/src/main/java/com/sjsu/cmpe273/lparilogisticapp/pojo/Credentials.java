package com.sjsu.cmpe273.lparilogisticapp.pojo;

/** Login request body. Sent in the request body, never in the URL. */
public class Credentials {

    private final String email;
    private final String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
