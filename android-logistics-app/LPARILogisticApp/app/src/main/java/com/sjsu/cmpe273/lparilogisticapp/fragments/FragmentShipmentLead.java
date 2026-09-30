package com.sjsu.cmpe273.lparilogisticapp.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjsu.cmpe273.lparilogisticapp.R;

public class FragmentShipmentLead extends Fragment {
    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public FragmentShipmentLead() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.TabText);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View rootView = localInflater.inflate(R.layout.fragment_tabs, container, false);

        //View rootView = localInflater.inflate(R.layout.fragment_tabs,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);


        mTabHost.addTab(mTabHost.newTabSpec("fragmentlowcaution").setIndicator("Low Caution"),
                FragmentShipmentLowCaution.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentmediumalert").setIndicator("Medium Alert"),
                FragmentShipmentMediumAlert.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmenthighalert").setIndicator("High Alert"),
                FragmentShipmentHighAlert.class, null);


        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#4CAF50"));
        mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#FFE0B2"));
        mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#F8BBD0"));


        return rootView;
    }
}
