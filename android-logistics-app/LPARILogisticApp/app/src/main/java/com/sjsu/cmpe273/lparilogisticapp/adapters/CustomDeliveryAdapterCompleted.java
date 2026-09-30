package com.sjsu.cmpe273.lparilogisticapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.cmpe273.lparilogisticapp.R;

import java.util.ArrayList;


public class CustomDeliveryAdapterCompleted extends BaseAdapter {

    private Context context;

    ArrayList<String> tripdropNodata, tripdropTime, tripcustName, tripCustAddress, tripCustPhnNo, tripCustComplitionStatus;

    private TextView setCustPhn;
    private static LayoutInflater inflater = null;


    public CustomDeliveryAdapterCompleted(Context context, ArrayList tripdropNodata, ArrayList tripdropTime,
                                          ArrayList tripcustName, ArrayList tripCustAddress, ArrayList tripCustPhnNo, ArrayList tripCustComplitionStatus) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.tripdropNodata = tripdropNodata;
        this.tripdropTime = tripdropTime;
        this.tripcustName = tripcustName;
        this.tripCustAddress = tripCustAddress;
        this.tripCustPhnNo = tripCustPhnNo;
        this.tripCustComplitionStatus = tripCustComplitionStatus;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (tripdropNodata == null)
            return 0;

        return tripdropNodata.size();
    }

    @Override
    public Object getItem(int position) {
        return tripdropNodata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_row_delivery_completed, null);
        }


        TextView setDropNotext = (TextView) vi.findViewById(R.id.tvDropNo);
        setDropNotext.setText(tripdropNodata.get(position));


        TextView setCustName = (TextView) vi.findViewById(R.id.tvCustomerName);
        setCustName.setText(tripcustName.get(position));

        TextView setCustAddress = (TextView) vi.findViewById(R.id.tvCustomerAddress);
        setCustAddress.setText(tripCustAddress.get(position));

        setCustPhn = (TextView) vi.findViewById(R.id.tvPhoneNo1);
        setCustPhn.setText(tripCustPhnNo.get(position));


        return vi;
    }


}

