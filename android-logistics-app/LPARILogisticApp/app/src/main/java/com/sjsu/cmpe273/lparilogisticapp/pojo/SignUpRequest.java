package com.sjsu.cmpe273.lparilogisticapp.pojo;

/** Sign-up request body. Sent in the request body, never in the URL. */
public class SignUpRequest {

    private final String name;
    private final String email;
    private final String password;
    private final String address;
    private final String state;
    private final String zipCode;
    private final String phone;

    public SignUpRequest(String name, String email, String password, String address,
                         String state, String zipCode, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
    }
}
