package com.sjsu.cmpe273.lparilogisticapp.fragments;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

/** Pending drops rated low risk. */
public class FragmentShipmentLowCaution extends BaseShipmentListFragment {

    @Override
    protected int screenLayout() {
        return R.layout.fragment_shipment_low;
    }

    @Override
    protected int listViewId() {
        return R.id.lvListViewLow;
    }

    @Override
    protected int rowLayout() {
        return R.layout.list_row_delivery_low_caution;
    }

    @Override
    protected boolean shows(TripDetail trip) {
        return !trip.isDelivered() && TripDetail.RISK_LOW.equals(trip.getRiskLevel());
    }
}
