package com.sjsu.cmpe273.lparilogisticapp.data;

import android.content.Context;

import com.sjsu.cmpe273.lparilogisticapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Single, shared Retrofit client. In demo mode requests are answered from bundled sample data. */
public final class ApiClient {

    private static RetrofitApi api;

    private ApiClient() {
    }

    public static synchronized RetrofitApi get(Context context) {
        if (api == null) {
            OkHttpClient.Builder http = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS);
            if (BuildConfig.DEMO_MODE) {
                http.addInterceptor(new DemoDataInterceptor(context.getApplicationContext()));
            }
            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitApi.class);
        }
        return api;
    }
}
