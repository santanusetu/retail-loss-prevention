package com.sjsu.cmpe273.lparilogisticapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.SignatureActivity;
import com.sjsu.cmpe273.lparilogisticapp.adapters.ItemDeliveryRecyclerAdapter;
import com.sjsu.cmpe273.lparilogisticapp.model.DeliveryItem;

import java.util.ArrayList;


public class FragmentItemDelivery extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;

    Button deliverViewButton;
    private ArrayList<DeliveryItem> item_list;

    Fragment itemFragment;
    ItemDeliveryRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_LightMap);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View rootView = localInflater.inflate(R.layout.fragment_item_delivery_list, container, false);
        rootView.setBackgroundColor(Color.WHITE);

        itemFragment = this;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_item_delivery);
        deliverViewButton = (Button) rootView.findViewById(R.id.btReadyToDeliver);


        final int[] icons = {R.drawable.macbook, R.drawable.orange_icon, R.drawable.tablelamp};
        final String[] deliveryItemsList = {"Laptop", "Orange", " Table Lamp "};
        final String[] deliveryItemsCategory = {" Category :     Electronic Item ", " Category :     FOOD Item",
                " Category :     GLASS Item"};
        final String[] deliveryItemsInstructions = {"Handle with care - no water touch ",
                "Do not pressure much  -  refrigerator check",
                "Care Needed - easily Breakable "};


        item_list = new ArrayList<>();

        for (int i = 0; i < deliveryItemsList.length; i++) {
            DeliveryItem item = new DeliveryItem();

            item.setThumbnail(icons[i]);
            item.setTitle(deliveryItemsList[i]);
            item.setCategory(deliveryItemsCategory[i]);
            item.setInstructions(deliveryItemsInstructions[i]);
            item.setIsScanned(false);


            item_list.add(item);
        }

        adapter=new ItemDeliveryRecyclerAdapter(getActivity(), itemFragment, item_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        deliverViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(v.getContext(), "Delivery Item is ready ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), SignatureActivity.class);
                startActivity(intent);


            }
        });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(getActivity(), " Scanned ", Toast.LENGTH_SHORT).show();
        if (requestCode == 12345) {

            // Handle successful scan
            //  Toast.makeText(getActivity(), "Toast Scanned QWERTY -> ", Toast.LENGTH_SHORT).show();

            System.out.println("@@@@ adapter position " + data.getIntExtra("position", 0));
            int pos = data.getIntExtra("position",0);
            item_list.get(pos).setIsScanned(true);
            adapter.notifyDataSetChanged();

            int count = 0;
            for (int i=0; i < item_list.size(); i++){
                if(item_list.get(i).getIsScanned()){
                    count += 1;
                }
            }

            if(count >= item_list.size()){
                deliverViewButton.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        // Toast.makeText(getActivity(), "Toast Scanned 12345", Toast.LENGTH_SHORT).show();
    }
}
