package com.sjsu.cmpe273.lparilogisticapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.util.GPSTracker;

import java.net.URL;
import java.util.List;
import java.util.Locale;


public class FragmentDetailsShipment extends Fragment {

    double srcLatitude;
    double srcLongitude;

    GPSTracker gps;

    Button startDelivery;
    Button endDelivery;

    static private Geocoder mGeocoder;
    String restoredCustomerAddress;

    static double lat;
    static double lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Shipment Details");

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_LightMap);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = localInflater.inflate(R.layout.fragment_details_shipment, container, false);
        rootView.setBackgroundColor(Color.WHITE);


        mGeocoder = new Geocoder(getActivity(), Locale.getDefault());

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView address = (TextView) rootView.findViewById(R.id.tvCtAddress);
        TextView phnNo = (TextView) rootView.findViewById(R.id.tvCtPhoneNo);

        SharedPreferences prefs = getActivity().getSharedPreferences("shipment_details_pref", Context.MODE_PRIVATE);
        String restoredCustomerName = prefs.getString("customerName", null);
        restoredCustomerAddress = prefs.getString("customerAddress", null);
        String restoredCustPhn = prefs.getString("custPhn", null);

        if (restoredCustomerName != null) {
          //  String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
           // int idName = prefs.getInt("idName", 0); //0 is the default value.

            System.out.println("@@@@@ shared pref info "+restoredCustomerName + "  "+restoredCustomerAddress+"  "+restoredCustPhn);

            title.setText("" + restoredCustomerName);
            address.setText("" + restoredCustomerAddress);
            phnNo.setText(""+restoredCustPhn);
        }



        startDelivery = (Button) rootView.findViewById(R.id.btDeliveryStart);
        endDelivery = (Button) rootView.findViewById(R.id.btDeliveryEnd);
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {
            srcLatitude = gps.getLatitude();
            srcLongitude = gps.getLongitude();
            Log.i("FragmentDetailsShipment", "@@@@ Location is Lat: " + srcLatitude + " Long: " + srcLongitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }



        convertAddress(restoredCustomerAddress);


        String URI = "https://maps.googleapis.com/maps/api/staticmap?size=700x700&maptype=roadmap" +
                "&markers=color:red%7C" + lat + "," + lng;
        System.out.println("@@@@@ URI " + URI);

       // final double destLat = 37.367904;
        //final double destLat = lat;
       // final double destLong = -121.920200;
        //final double destLong = lng;

        String uriNew = "https://maps.google.com/maps?saddr=" + srcLatitude
                + "," + srcLongitude
                + "&daddr="
                + lat + "," + lng;

        System.out.println("@@@@ new uri " + uriNew);


        new GetStaticMapImage((ImageView) rootView.findViewById(R.id.ivMapSnapShot)).execute(URI);


        startDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", srcLatitude, srcLongitude, destLat, destLong);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);*/

                //for testing
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", srcLatitude, srcLongitude, "Start Delivery", lat, lng, "Santanu Chakraborty");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

                startDelivery.setVisibility(View.GONE);
                endDelivery.setVisibility(View.VISIBLE);
            }
        });


        endDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDelivery.setVisibility(View.VISIBLE);
                endDelivery.setVisibility(View.GONE);


                Fragment fragment = new FragmentItemDelivery();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }


    public void convertAddress(String address) {
        if (address != null && !address.isEmpty()) {
            try {
                List<Address> addressList = mGeocoder.getFromLocationName(address, 1);
                if (addressList != null && addressList.size() > 0) {
                    lat = addressList.get(0).getLatitude();
                    lng = addressList.get(0).getLongitude();

                    System.out.println("@@@@ lat "+lat+" long "+lng);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        } // end if
    } // end convertAddress





    //class to get the image
    private class GetStaticMapImage extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public GetStaticMapImage(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap bmp = null;
            try {
                URL url = new URL(urls[0]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                Log.i("Error", "@@@@@ Error " + urls[0]);
                Log.i("Error", "@@@@@ Error msg " + e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            System.out.println("@@@@@ result " + result);
            image.setImageBitmap(result);
        }

    }




}






