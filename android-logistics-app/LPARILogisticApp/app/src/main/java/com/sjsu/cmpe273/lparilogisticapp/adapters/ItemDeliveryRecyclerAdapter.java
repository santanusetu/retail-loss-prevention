package com.sjsu.cmpe273.lparilogisticapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.cmpe273.lparilogisticapp.R;
import com.sjsu.cmpe273.lparilogisticapp.SimpleScannerActivity;
import com.sjsu.cmpe273.lparilogisticapp.model.DeliveryItem;

import java.util.ArrayList;


public class ItemDeliveryRecyclerAdapter extends  RecyclerView.Adapter<ItemDeliveryRecyclerAdapter.RecyclerViewHolderItemDelivery>{

    Context context;
    LayoutInflater inflater;
    private static ArrayList<DeliveryItem> dataDeliveryItem;

    Fragment itemFrag;

    /*public ItemDeliveryRecyclerAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }*/

    public ItemDeliveryRecyclerAdapter(Context context, Fragment itemFrag, ArrayList<DeliveryItem> item_list) {
        this.context=context;
        this.itemFrag = itemFrag;
        inflater=LayoutInflater.from(context);

        dataDeliveryItem = item_list;
    }

    @Override
    public RecyclerViewHolderItemDelivery onCreateViewHolder(ViewGroup parent, int viewType) {

       //change the card
        View v = inflater.inflate(R.layout.fragment_item_delivery_card, parent, false);

        RecyclerViewHolderItemDelivery viewHolder = new RecyclerViewHolderItemDelivery(v , itemFrag);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(RecyclerViewHolderItemDelivery holder, int position) {

        DeliveryItem fp = dataDeliveryItem.get(position);


        holder.iconDeliveryItem.setImageResource(fp.getThumbnail());
        holder.tvDeliveryItem.setText(fp.getTitle());
        holder.tvCategoryDeliveryItem.setText(fp.getCategory());
        holder.tvInstructions.setText(fp.getInstructions());


        if(fp.getIsScanned()) {
            holder.itemCard.setCardBackgroundColor(Color.parseColor("#99ffbb"));
        }else{
            //
        }

        holder.item_feed = fp;



    }




    @Override
    public int getItemCount() {
        return dataDeliveryItem.size();
    }





    // inner class to hold a reference to each item of RecyclerView
    public static class RecyclerViewHolderItemDelivery extends RecyclerView.ViewHolder {


        public ImageView iconDeliveryItem;
        public TextView tvDeliveryItem, tvCategoryDeliveryItem, tvInstructions, tvSpclIns;

        public CardView itemCard;

        public DeliveryItem item_feed;

        public RecyclerViewHolderItemDelivery(final View itemLayoutView, final Fragment fmt) {
            super(itemLayoutView);


            iconDeliveryItem = (ImageView) itemLayoutView.findViewById(R.id.ivItem1);
            tvDeliveryItem = (TextView) itemLayoutView.findViewById(R.id.title);
            tvCategoryDeliveryItem = (TextView) itemLayoutView.findViewById(R.id.tvItemCategory);
            tvInstructions = (TextView) itemLayoutView.findViewById(R.id.tvInstructions);
            tvSpclIns = (TextView) itemLayoutView.findViewById(R.id.tvCtPhoneNo);
            itemCard = (CardView) itemLayoutView.findViewById(R.id.card_view_item_delivery);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), "Delivery Item is: " + item_feed.getTitle(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), SimpleScannerActivity.class);

                    System.out.println("@@@@ adapter position "+ getAdapterPosition());
                      intent.putExtra("adapterPosition", getAdapterPosition());
                     //((Activity) v.getContext()).startActivityForResult(intent, 12345);
                    fmt.startActivityForResult(intent, 12345);

                }
            });
        }



    }
}
