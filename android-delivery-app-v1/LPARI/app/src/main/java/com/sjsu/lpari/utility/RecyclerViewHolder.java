package com.sjsu.lpari.utility;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.lpari.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tv1,tv2, tv3, tv4;
    ImageView imageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        tv1= (TextView) itemView.findViewById(R.id.title);
        tv2= (TextView) itemView.findViewById(R.id.tvItemCategory);
        tv3= (TextView) itemView.findViewById(R.id.tvInstructions);
        tv4= (TextView) itemView.findViewById(R.id.tvCtPhoneNo);
        imageView= (ImageView) itemView.findViewById(R.id.ivItem1);




    }
}