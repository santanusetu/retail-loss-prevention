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


public class CustomDeliveryAdapterMediumAlert extends BaseAdapter {

    private Context context;

    ArrayList<String> tripdropNodata, tripdropTime, tripcustName, tripCustAddress, tripCustPhnNo, tripCustComplitionStatus;

    private TextView setCustPhn;
    private static LayoutInflater inflater = null;


    public CustomDeliveryAdapterMediumAlert(Context context, ArrayList tripdropNodata, ArrayList tripdropTime,
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
            vi = inflater.inflate(R.layout.list_row_delivery_medium_alert, null);
        }



        TextView setDropNotext = (TextView) vi.findViewById(R.id.tvDropNo);
        setDropNotext.setText(tripdropNodata.get(position));

        TextView setDropTime = (TextView) vi.findViewById(R.id.tvDropTime);
        setDropTime.setText(tripdropTime.get(position));

        TextView setCustName = (TextView) vi.findViewById(R.id.tvCustomerName);
        setCustName.setText(tripcustName.get(position));

        TextView setCustAddress = (TextView) vi.findViewById(R.id.tvCustomerAddress);
        setCustAddress.setText(tripCustAddress.get(position));

        setCustPhn = (TextView) vi.findViewById(R.id.tvPhoneNo1);
        setCustPhn.setText(tripCustPhnNo.get(position));

        setCustPhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCustomer(v, position);
            }
        });

        ImageView phnIcon = (ImageView) vi.findViewById(R.id.ivDotIcon);
        phnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCustomer(v, position);
            }
        });


        ImageView notifyCust = (ImageView) vi.findViewById(R.id.ivArrow);
        notifyCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callCustomer(v, position);
                msgCustomer(v, position);
            }
        });

        TextView notifyText = (TextView) vi.findViewById(R.id.tvNotify);
        notifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callCustomer(v, position);
                msgCustomer(v, position);
            }
        });
        return vi;
    }

    private void msgCustomer(View v, int position) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String number = "tel:" + tripCustPhnNo.get(position).trim();
            String msg = "Your Shipment is on the way ";
            smsManager.sendTextMessage(number, null, msg, null, null);
            Toast.makeText(v.getContext(), "Your Shipment is on the way ", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(v.getContext(),ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }


    private void callCustomer(View v, int position) {

        Toast.makeText(v.getContext(), "Calling " + tripcustName.get(position), Toast.LENGTH_SHORT).show();
        // System.out.println("@@@ calling ABCD " + getItem(position).toString());
        System.out.println("@@@ calling ABCDEFGH " + tripCustPhnNo.get(position));

        //System.out.println("@@@ calling " + setCustPhn.getText().toString());
        // Write your code here to execute after dialog
        String number = "tel:" + tripCustPhnNo.get(position).trim();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        context.startActivity(callIntent);
    }

}

