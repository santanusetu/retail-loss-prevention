package com.sjsu.cmpe273.lparilogisticapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.adapters.DeliveryAdapter;
import com.sjsu.cmpe273.lparilogisticapp.data.TripRepository;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared behaviour for every shipment list: load trips, keep the ones this screen shows,
 * and open the shipment details on tap. Subclasses only choose the filter and the layouts.
 */
public abstract class BaseShipmentListFragment extends Fragment {

    private ListView listView;

    @LayoutRes
    protected abstract int screenLayout();

    @IdRes
    protected abstract int listViewId();

    @LayoutRes
    protected abstract int rowLayout();

    /** Whether a trip belongs on this screen. */
    protected abstract boolean shows(TripDetail trip);

    /** Whether tapping a row opens the delivery flow. Completed drops are read-only. */
    protected boolean opensDetails() {
        return true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context themed = new ContextThemeWrapper(requireActivity(), R.style.AppTheme_Light);
        View root = inflater.cloneInContext(themed).inflate(screenLayout(), container, false);
        listView = root.findViewById(listViewId());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload on every return, so a delivery just completed moves to the Completed list
        TripRepository.getInstance().getTrips(requireContext(), new TripRepository.TripsCallback() {
            @Override
            public void onTrips(List<TripDetail> trips) {
                if (isAdded()) {
                    show(trips);
                }
            }

            @Override
            public void onError(String message) {
                if (isAdded()) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void show(List<TripDetail> allTrips) {
        final List<TripDetail> visible = new ArrayList<>();
        for (TripDetail trip : allTrips) {
            if (shows(trip)) {
                visible.add(trip);
            }
        }
        listView.setAdapter(new DeliveryAdapter(requireContext(), rowLayout(), visible));
        if (opensDetails()) {
            listView.setOnItemClickListener((parent, view, position, id) -> openDetails(visible.get(position)));
        }
    }

    private void openDetails(TripDetail trip) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, FragmentDetailsShipment.newInstance(trip))
                .addToBackStack(null)
                .commit();
    }
}
