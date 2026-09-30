package com.sjsu.cmpe273.lparilogisticapp.data;

import com.sjsu.cmpe273.lparilogisticapp.pojo.Credentials;
import com.sjsu.cmpe273.lparilogisticapp.pojo.LoginData;
import com.sjsu.cmpe273.lparilogisticapp.pojo.SignUpRequest;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/** REST contract for the logistics backend. */
public interface RetrofitApi {

    @POST("sessions")
    Call<LoginData> login(@Body Credentials credentials);

    @POST("users")
    Call<Void> signUp(@Body SignUpRequest request);

    /** Today's trips for the signed-in driver, every risk level and status. */
    @GET("trips")
    Call<TripList> getTrips();
}
