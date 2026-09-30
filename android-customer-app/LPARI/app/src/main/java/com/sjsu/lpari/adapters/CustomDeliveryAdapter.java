package com.sjsu.lpari.adapters;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.lpari.R;


public class CustomDeliveryAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    String[] dropNodata;
    String[] dropTime;
    String[] toteCount;
    String[] custName;

    private TextView ph2;

    private static LayoutInflater inflater = null;

    public CustomDeliveryAdapter(Context context, String[] dropNodata) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.dropNodata = dropNodata;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomDeliveryAdapter(Context context, String[] dropNodata, String[] dropTime,
                                 String[] toteCount, String[] custName) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.dropNodata = dropNodata;
        this.dropTime = dropTime;
        this.toteCount = toteCount;
        this.custName = custName;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dropNodata.length;
    }

    @Override
    public Object getItem(int position) {
        return dropNodata[position];
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
            vi = inflater.inflate(R.layout.list_row_delivery, null);
        }

        TextView setDropNotext = (TextView) vi.findViewById(R.id.tvDropNo);
        setDropNotext.setText(dropNodata[position]);
        TextView setDropTime = (TextView) vi.findViewById(R.id.tvDropTime);
        setDropTime.setText(dropTime[position]);
        //  TextView setToteCount = (TextView) vi.findViewById(R.id.tvToteCount);
        // setToteCount.setText(toteCount[position]);

        TextView setCustName = (TextView) vi.findViewById(R.id.tvCustomerName);
        setCustName.setText(custName[position]);

        ImageView arrowButton = (ImageView) vi.findViewById(R.id.ivArrow);
        arrowButton.setOnClickListener(this);

       /* arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(parent.getContext(), "Arrow clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });*/

        /*LinearLayout notifyButton = (LinearLayout) vi.findViewById(R.id.llNotifyButton);

        notifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Toast.makeText(parent.getContext(), "notifyButton clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });*/

        // ImageView n1 = (ImageView) vi.findViewById(R.id.ivNotify);
        TextView n2 = (TextView) vi.findViewById(R.id.tvNotify);
        // n1.setOnClickListener(this);
        n2.setOnClickListener(this);

        //ImageView od1 = (ImageView) vi.findViewById(R.id.ivOrderDetails);
        //TextView od2 = (TextView) vi.findViewById(R.id.tvOrderDetails);

        //od1.setOnClickListener(this);
        //od2.setOnClickListener(this);

        //  ImageView ch1 = (ImageView) vi.findViewById(R.id.ivCustHistory);
        // TextView ch2 = (TextView) vi.findViewById(R.id.tvCustomerHist);

        //ch1.setOnClickListener(this);
        //ch2.setOnClickListener(this);

        ImageView ph1 = (ImageView) vi.findViewById(R.id.ivPhoneIcon);
        ph2 = (TextView) vi.findViewById(R.id.tvPhoneNo1);
        // TextView ph3 = (TextView) vi.findViewById(R.id.tvPhoneNo2);

        ph1.setOnClickListener(this);
        ph2.setOnClickListener(this);
        //ph3.setOnClickListener(this);


        return vi;
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            //  case R.id.ivNotify:
            case R.id.tvNotify:
            case R.id.ivArrow:
                Toast.makeText(v.getContext(), "Notify Clicked.", Toast.LENGTH_SHORT).show();
                break;

        /*    case R.id.ivOrderDetails:
            case R.id.tvOrderDetails:
                Toast.makeText(v.getContext(), "Order Details Clicked.", Toast.LENGTH_SHORT).show();
                break;*/

            // case R.id.ivCustHistory:
            // case R.id.tvCustomerHist:
            //   Toast.makeText(v.getContext(), "Customer History Clicked.", Toast.LENGTH_SHORT).show();
            //  break;

            case R.id.ivPhoneIcon:
            case R.id.tvPhoneNo1:
                //  case R.id.tvPhoneNo2:
                Toast.makeText(v.getContext(), "Phone Clicked." + ph2.getText(), Toast.LENGTH_SHORT).show();

                // Write your code here to execute after dialog
                String number = "tel:" + ph2.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                context.startActivity(callIntent);

                break;
        }
    }
}

