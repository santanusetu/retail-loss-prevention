package com.sjsu.cmpe273.lparilogisticapp.fragments;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

/** Drops already delivered and signed for. The 2016 version filtered for pending drops here by mistake. */
public class FragmentShipmentCompleted extends BaseShipmentListFragment {

    @Override
    protected int screenLayout() {
        return R.layout.fragment_shipment_completed;
    }

    @Override
    protected int listViewId() {
        return R.id.lvListView;
    }

    @Override
    protected int rowLayout() {
        return R.layout.list_row_delivery_completed;
    }

    @Override
    protected boolean shows(TripDetail trip) {
        return trip.isDelivered();
    }

    @Override
    protected boolean opensDetails() {
        return false;
    }
}
