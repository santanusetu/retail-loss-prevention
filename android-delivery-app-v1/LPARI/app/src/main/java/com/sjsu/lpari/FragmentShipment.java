package com.sjsu.lpari;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sjsu.lpari.adapters.CustomDeliveryAdapter;

public class FragmentShipment extends Fragment {

    ListView listview;
    private FragmentActivity faActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View rootView = inflater.inflate(R.layout.fragment_shipment, container, false);
        // listView = (ListView) rootView.findViewById(R.id.listView);
        // return rootView;

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        // inflate the layout using the cloned inflater, not default inflater
        //return localInflater.inflate(R.layout.yourLayout, container, false);


        View rootView = localInflater.inflate(R.layout.fragment_shipment, container, false);
        faActivity = (FragmentActivity) super.getActivity();
        listview = (ListView) rootView.findViewById(R.id.lvListView);


        String[] dropNo = new String[]{"Drop 1", "Drop 2", "Drop 3", "Drop 4", "Drop 5",
                "Drop 6", "Drop 7"};
        String[] deliveryTime = new String[]{"7:00 AM - 9:00 AM", "7:00 AM - 9:00 AM", "8:00 AM - 10:00 AM",
                "9:00 AM - 11:00 AM", "9:00 AM - 11:00 AM", "9:00 AM - 11:00 AM", "10:00 AM - 11:55 AM"};
        String[] toteCount = new String[]{"4 Pkgs", "11 Pkgs", "8 Pkgs", "3 Pkgs",
                "5 Pkgs", "2 Pkgs", "9 Pkgs"};
        String[] custName = new String[]{"Santanu Chakraborty", "Dan Harkey", "Barack Obama",
                "Sawan Chakraborty", "Elon Mask", "Bill Gates", "Sachin Tendulkar"};

       // listview.setAdapter(new CustomDeliveryAdapter(this, dropNo, deliveryTime, toteCount, custName));

        listview.setAdapter(new CustomDeliveryAdapter(getActivity(), dropNo, deliveryTime, toteCount, custName));


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                String value = (String) adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
           //     Toast.makeText(v.getContext(), "ListView Clicked "+value, Toast.LENGTH_SHORT).show();

                Fragment fragment = new FragmentDetailsShipment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return  rootView;
    }


}