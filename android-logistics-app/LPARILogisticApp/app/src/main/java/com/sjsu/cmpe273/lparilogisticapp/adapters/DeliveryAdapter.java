package com.sjsu.cmpe273.lparilogisticapp.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.pojo.TripDetail;

import java.util.List;

/**
 * One adapter for every shipment list. The row layout decides the look (low, medium, high, completed);
 * call and notify actions are wired only when the row layout has those views.
 */
public class DeliveryAdapter extends BaseAdapter {

    private final Context context;
    private final List<TripDetail> trips;
    private final int rowLayout;

    public DeliveryAdapter(Context context, @LayoutRes int rowLayout, List<TripDetail> trips) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.trips = trips;
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public TripDetail getItem(int position) {
        return trips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(rowLayout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TripDetail trip = getItem(position);
        setText(holder.dropNo, trip.getDropNo());
        setText(holder.dropTime, trip.getDeliveryTime());
        setText(holder.customerName, trip.getCustomerName());
        setText(holder.customerAddress, trip.getCustAddress());
        setText(holder.phone, trip.getPhnNo());

        View.OnClickListener call = v -> dialCustomer(trip);
        View.OnClickListener notify = v -> messageCustomer(trip);
        setClick(holder.phone, call);
        setClick(holder.phoneIcon, call);
        setClick(holder.notifyIcon, notify);
        setClick(holder.notifyText, notify);
        return convertView;
    }

    /** Opens the dialer with the number filled in. Needs no phone permission. */
    private void dialCustomer(TripDetail trip) {
        start(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + trip.getPhnNo().trim())));
    }

    /** Opens the messaging app with the text prepared; the driver confirms before anything is sent. */
    private void messageCustomer(TripDetail trip) {
        Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + trip.getPhnNo().trim()));
        sms.putExtra("sms_body", "Hi " + trip.getCustomerName() + ", your delivery is on the way.");
        start(sms);
    }

    private void start(Intent intent) {
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.no_app_for_action, Toast.LENGTH_SHORT).show();
        }
    }

    private static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

    private static void setClick(View view, View.OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    private static final class ViewHolder {
        final TextView dropNo;
        final TextView dropTime;
        final TextView customerName;
        final TextView customerAddress;
        final TextView phone;
        final View phoneIcon;
        final View notifyIcon;
        final TextView notifyText;

        ViewHolder(View row) {
            dropNo = row.findViewById(R.id.tvDropNo);
            dropTime = row.findViewById(R.id.tvDropTime);
            customerName = row.findViewById(R.id.tvCustomerName);
            customerAddress = row.findViewById(R.id.tvCustomerAddress);
            phone = row.findViewById(R.id.tvPhoneNo1);
            phoneIcon = row.findViewById(R.id.ivDotIcon);
            notifyIcon = row.findViewById(R.id.ivArrow);
            notifyText = row.findViewById(R.id.tvNotify);
        }
    }
}
