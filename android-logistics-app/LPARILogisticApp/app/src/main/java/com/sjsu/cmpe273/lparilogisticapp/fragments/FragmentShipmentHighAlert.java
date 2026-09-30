package com.sjsu.cmpe273.lparilogisticapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sjsu.cmpe273.lparilogisticapp.LoginActivity;
import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.adapters.CustomDeliveryAdapterHigh;
import com.sjsu.cmpe273.lparilogisticapp.adapters.CustomDeliveryAdapterMediumAlert;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripList;
import com.sjsu.cmpe273.lparilogisticapp.retrofit.RetrofitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FragmentShipmentHighAlert extends Fragment {

    ListView listview;
    ArrayList<String> tripDropNo, tripDeliveryTime, tripCustName, tripCustAddress, tripCustPhnNo, tripCustComplitionStatus;
    //http://www.mocky.io/v2/5719e08a120000130b1b7200 //OLD one
    //http://www.mocky.io/v2/5731a824100000572017f91e
    static CustomDeliveryAdapterHigh adapter;
    public static final String DETAILS_PREFERENCES = "shipment_details_pref";
    SharedPreferences userShipmentSharedPref;
    static List<TripDetail> tripDetails;

    static int entryCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View rootView = localInflater.inflate(R.layout.fragment_shipment_medium, container, false);
        listview = (ListView) rootView.findViewById(R.id.lvListViewMedium);


        tripDropNo = new ArrayList<>();
        tripDeliveryTime = new ArrayList<>();
        tripCustName = new ArrayList<>();
        tripCustAddress = new ArrayList<>();
        tripCustPhnNo = new ArrayList<>();
        tripCustComplitionStatus = new ArrayList<>();

        Retrofit retrofitShipment = new Retrofit.Builder()
                .baseUrl(LoginActivity.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retShipmentApi = retrofitShipment.create(RetrofitApi.class);

        Call<TripList> call = retShipmentApi.loadTripListDataHighAlert("tripList");


        System.out.println("@@@@ response ISFIRSTTIME " + entryCount);

        if (entryCount == 0) {
            call.enqueue(new Callback<TripList>() {
                @Override
                public void onResponse(Response<TripList> response, Retrofit retrofit) {
                    tripDetails = response.body().getTripDetails();

                    for (int i = 0; i < tripDetails.size(); i++) {

                        if (tripDetails.get(i).getCompletionStatus().equalsIgnoreCase("No")) {

                            System.out.println("@@@@ response CALL " + tripDetails.get(i).getCustomerName());
                            tripDropNo.add(tripDetails.get(i).getDropNo());
                            tripDeliveryTime.add(tripDetails.get(i).getDeliveryTime());
                            tripCustName.add(tripDetails.get(i).getCustomerName());
                            tripCustAddress.add(tripDetails.get(i).getCustAddress());
                            tripCustPhnNo.add(tripDetails.get(i).getPhnNo());
                            tripCustComplitionStatus.add(tripDetails.get(i).getCompletionStatus());
                        }
                    }



                    adapter = new CustomDeliveryAdapterHigh(getActivity(), tripDropNo, tripDeliveryTime, tripCustName, tripCustAddress, tripCustPhnNo, tripCustComplitionStatus);
                    listview.setAdapter(adapter);

                    entryCount++;
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("@@@@ error " + t.getLocalizedMessage());
                }
            });
        }//end of if boolean


        // second time onwards
        if (entryCount > 0) {
            for (int i = 0; i < tripDetails.size(); i++) {

                if (tripDetails.get(i).getCompletionStatus().equalsIgnoreCase("No")) {
                    System.out.println("@@@@ response 3 " + tripDetails.get(i).getCustomerName());
                    tripDropNo.add(tripDetails.get(i).getDropNo());
                    tripDeliveryTime.add(tripDetails.get(i).getDeliveryTime());
                    tripCustName.add(tripDetails.get(i).getCustomerName());
                    tripCustAddress.add(tripDetails.get(i).getCustAddress());
                    tripCustPhnNo.add(tripDetails.get(i).getPhnNo());
                    tripCustComplitionStatus.add(tripDetails.get(i).getCompletionStatus());
                }
            }

            adapter = new CustomDeliveryAdapterHigh(getActivity(), tripDropNo, tripDeliveryTime, tripCustName, tripCustAddress, tripCustPhnNo, tripCustComplitionStatus);
            listview.setAdapter(adapter);

        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                String value = (String) adapter.getItemAtPosition(position);

                System.out.println("@@@@@ shipment details " + adapter.getItemAtPosition(position));
                System.out.println("@@@@@ shipment details position " + position);


                userShipmentSharedPref = getActivity().getSharedPreferences(DETAILS_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(DETAILS_PREFERENCES, Context.MODE_PRIVATE).edit();
                editor.putString("customerName", tripCustName.get(position));
                editor.putString("customerAddress", tripCustAddress.get(position));
                editor.putString("custPhn", tripCustPhnNo.get(position));


                int pos = tripCustName.indexOf(tripCustName.get(position));
                editor.putInt("tripDetailsPosition", pos);
                editor.apply();


                Fragment fragment = new FragmentDetailsShipment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }


}