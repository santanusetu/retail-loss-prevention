package com.sjsu.cmpe273.lparilogisticapp.fragments;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

/** Pending drops rated medium risk. */
public class FragmentShipmentMediumAlert extends BaseShipmentListFragment {

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
        return R.layout.list_row_delivery_medium_alert;
    }

    @Override
    protected boolean shows(TripDetail trip) {
        return !trip.isDelivered() && TripDetail.RISK_MEDIUM.equals(trip.getRiskLevel());
    }
}
