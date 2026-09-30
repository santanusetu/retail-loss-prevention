package com.sjsu.cmpe273.lparilogisticapp.fragments;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

/** Pending drops rated high risk: the ones to watch for loss. */
public class FragmentShipmentHighAlert extends BaseShipmentListFragment {

    @Override
    protected int screenLayout() {
        return R.layout.fragment_shipment_medium;
    }

    @Override
    protected int listViewId() {
        return R.id.lvListViewMedium;
    }

    @Override
    protected int rowLayout() {
        return R.layout.list_row_delivery_high;
    }

    @Override
    protected boolean shows(TripDetail trip) {
        return !trip.isDelivered() && TripDetail.RISK_HIGH.equals(trip.getRiskLevel());
    }
}
