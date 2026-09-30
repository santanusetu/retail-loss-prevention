package com.sjsu.cmpe273.lparilogisticapp.retrofit;

import com.sjsu.cmpe273.lparilogisticapp.pojo.Login;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;


public interface RetrofitApi {
    @GET
    Call<String> loadSignUpData(@Url String url);

    @GET("v2/5719a2bf120000f9041b719e")
    Call<Login> loadLoginData(@Query("user_name") String email);

    @GET("v2/5719e08a120000130b1b7200")
    Call<TripList> loadTripListData(@Query("trip_list") String tripList);

    @GET("v2/5719e08a120000130b1b7200")
    Call<TripList> loadTripListDataLowCaution(@Query("trip_list") String tripList);

    @GET("v2/5731a74c100000572017f919")
    Call<TripList> loadTripListDataMediumAlert(@Query("trip_list") String tripList);

    @GET("v2/5731a824100000572017f91e")
    Call<TripList> loadTripListDataHighAlert(@Query("trip_list") String tripList);

    @GET("v2/5731a824100000572017f91e")
    Call<TripList> loadTripListDataCompleted(@Query("trip_list") String tripList);

}



