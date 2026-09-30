package com.sjsu.cmpe273.lparilogisticapp.pojo;


import java.util.ArrayList;
import java.util.List;


public class TripList {

    private List<TripDetail> TripDetails = new ArrayList<TripDetail>();


    public List<TripDetail> getTripDetails() {
        return TripDetails;
    }


    public void setTripDetails(List<TripDetail> TripDetails) {
        this.TripDetails = TripDetails;
    }

}