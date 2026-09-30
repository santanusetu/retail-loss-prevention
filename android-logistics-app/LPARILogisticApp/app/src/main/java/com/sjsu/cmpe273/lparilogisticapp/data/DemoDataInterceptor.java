package com.sjsu.cmpe273.lparilogisticapp.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Answers API calls from JSON files in assets/demo so the app runs without a backend.
 * The original 2016 backend and mock endpoints no longer exist.
 */
class DemoDataInterceptor implements Interceptor {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final Context context;

    DemoDataInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().encodedPath();

        switch (path) {
            case "/trips":
                return respond(request, 200, readAsset("demo/trips.json"));
            case "/sessions":
                return respond(request, 200, readAsset("demo/login.json"));
            case "/users":
                return respond(request, 201, "");
            default:
                return respond(request, 404, "{\"error\":\"not found\"}");
        }
    }

    private Response respond(Request request, int code, String body) {
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(code < 300 ? "OK" : "Not Found")
                .body(ResponseBody.create(body, JSON))
                .build();
    }

    private String readAsset(String name) throws IOException {
        try (InputStream in = context.getAssets().open(name)) {
            byte[] buffer = new byte[in.available()];
            int read = in.read(buffer);
            return new String(buffer, 0, Math.max(read, 0), "UTF-8");
        }
    }
}
