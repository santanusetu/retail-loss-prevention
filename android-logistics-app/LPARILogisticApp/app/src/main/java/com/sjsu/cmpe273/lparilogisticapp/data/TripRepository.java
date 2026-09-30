package com.sjsu.cmpe273.lparilogisticapp.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Loads the driver's trips once and keeps them in memory, so every screen shares one source of truth. */
public final class TripRepository {

    public interface TripsCallback {
        void onTrips(List<TripDetail> trips);

        void onError(String message);
    }

    private static final TripRepository INSTANCE = new TripRepository();

    private List<TripDetail> trips;

    private TripRepository() {
    }

    public static TripRepository getInstance() {
        return INSTANCE;
    }

    public void getTrips(Context context, final TripsCallback callback) {
        if (trips != null) {
            callback.onTrips(Collections.unmodifiableList(trips));
            return;
        }
        ApiClient.get(context).getTrips().enqueue(new Callback<TripList>() {
            @Override
            public void onResponse(@NonNull Call<TripList> call, @NonNull Response<TripList> response) {
                TripList body = response.body();
                if (!response.isSuccessful() || body == null) {
                    callback.onError("Could not load trips (" + response.code() + ")");
                    return;
                }
                trips = new ArrayList<>(body.getTripDetails());
                callback.onTrips(Collections.unmodifiableList(trips));
            }

            @Override
            public void onFailure(@NonNull Call<TripList> call, @NonNull Throwable t) {
                callback.onError("Could not load trips: " + t.getMessage());
            }
        });
    }

    /** Marks a drop as delivered after the customer has signed. */
    public void markDelivered(String dropNo) {
        if (trips == null || dropNo == null) {
            return;
        }
        for (TripDetail trip : trips) {
            if (dropNo.equals(trip.getDropNo())) {
                trip.setCompletionStatus(TripDetail.STATUS_DELIVERED);
            }
        }
    }
}
