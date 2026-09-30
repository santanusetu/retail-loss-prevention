package com.sjsu.cmpe273.lparilogisticapp.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sjsu.cmpe273.lparilogisticapp.BuildConfig;
import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** One drop: customer, address, a map preview, and the start/end of the delivery. */
public class FragmentDetailsShipment extends Fragment {

    private static final String TAG = "DetailsShipment";
    private static final String ARG_DROP_NO = "dropNo";
    private static final String ARG_NAME = "customerName";
    private static final String ARG_ADDRESS = "customerAddress";
    private static final String ARG_PHONE = "customerPhone";
    private static final String ARG_RISK = "riskLevel";

    private final ExecutorService background = Executors.newSingleThreadExecutor();
    private final Handler main = new Handler(Looper.getMainLooper());

    private ImageView mapView;
    private String address;

    public static FragmentDetailsShipment newInstance(TripDetail trip) {
        Bundle args = new Bundle();
        args.putString(ARG_DROP_NO, trip.getDropNo());
        args.putString(ARG_NAME, trip.getCustomerName());
        args.putString(ARG_ADDRESS, trip.getCustAddress());
        args.putString(ARG_PHONE, trip.getPhnNo());
        args.putString(ARG_RISK, trip.getRiskLevel());
        FragmentDetailsShipment fragment = new FragmentDetailsShipment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().setTitle(R.string.shipment_details);
        Context themed = new ContextThemeWrapper(requireActivity(), R.style.AppTheme_LightMap);
        View root = inflater.cloneInContext(themed).inflate(R.layout.fragment_details_shipment, container, false);
        root.setBackgroundColor(Color.WHITE);

        Bundle args = requireArguments();
        final String dropNo = args.getString(ARG_DROP_NO);
        address = args.getString(ARG_ADDRESS, "");

        ((TextView) root.findViewById(R.id.title)).setText(args.getString(ARG_NAME));
        ((TextView) root.findViewById(R.id.tvCtAddress)).setText(address);
        ((TextView) root.findViewById(R.id.tvCtPhoneNo)).setText(args.getString(ARG_PHONE));
        ((TextView) root.findViewById(R.id.tvPriorityLevel))
                .setText(getString(R.string.priority_level, capitalise(args.getString(ARG_RISK, ""))));
        mapView = root.findViewById(R.id.ivMapSnapShot);

        final Button start = root.findViewById(R.id.btDeliveryStart);
        final Button end = root.findViewById(R.id.btDeliveryEnd);
        start.setOnClickListener(v -> {
            openDirections();
            start.setVisibility(View.GONE);
            end.setVisibility(View.VISIBLE);
        });
        end.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentItemDelivery.newInstance(dropNo))
                .addToBackStack(null)
                .commit());

        loadMapPreview();
        return root;
    }

    /** Directions to the drop in Google Maps or the browser. Maps finds the driver's position itself, so this app needs no location permission. */
    private void openDirections() {
        try {
            String destination = URLEncoder.encode(address, "UTF-8");
            Uri uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + destination);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException | java.io.UnsupportedEncodingException e) {
            Toast.makeText(requireContext(), R.string.no_app_for_action, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Geocodes the address and loads a static map, off the UI thread.
     * Skipped when no Maps API key is configured; the header image stays in place instead.
     */
    private void loadMapPreview() {
        if (BuildConfig.MAPS_API_KEY.isEmpty() || address.isEmpty()) {
            return;
        }
        final Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        background.execute(() -> {
            try {
                List<Address> results = geocoder.getFromLocationName(address, 1);
                if (results == null || results.isEmpty()) {
                    return;
                }
                String url = "https://maps.googleapis.com/maps/api/staticmap?size=700x500&maptype=roadmap"
                        + "&markers=color:red%7C" + results.get(0).getLatitude() + "," + results.get(0).getLongitude()
                        + "&key=" + BuildConfig.MAPS_API_KEY;
                try (InputStream in = new URL(url).openStream()) {
                    final Bitmap map = BitmapFactory.decodeStream(in);
                    main.post(() -> {
                        if (isAdded() && map != null) {
                            mapView.setImageBitmap(map);
                        }
                    });
                }
            } catch (Exception e) {
                Log.w(TAG, "Map preview unavailable", e);
            }
        });
    }

    private static String capitalise(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        return value.substring(0, 1).toUpperCase(Locale.US) + value.substring(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        background.shutdownNow();
    }
}
