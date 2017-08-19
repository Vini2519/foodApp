package com.zap.foodapp.DeliveryLocation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zap.foodapp.DeliveryTime;
import com.zap.foodapp.HomePage.Home;
import com.zap.foodapp.HomePage.HomeListItem;
import com.zap.foodapp.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private ArrayList<ListItem> listItems;
    private Context context;
    private ArrayList<ListItem> mFilteredList;

    public MyAdapter(ArrayList<ListItem> listitem, Context context) {
        listItems = listitem;
        mFilteredList = listitem;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {


        holder.building.setText(mFilteredList.get(position).getBuilding_name());
        holder.add.setText(mFilteredList.get(position).getAddress());
        holder.postal.setText(mFilteredList.get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DeliveryTime.class);
                i.putExtra("DeliveryTime", mFilteredList.get(position).getTime());
                i.putExtra("id", mFilteredList.get(position).getCode());
                context.startActivity(i);


            }
        });
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView building;
        public TextView add;
        public TextView postal;

        public ViewHolder(View itemView) {
            super(itemView);

            building = (TextView) itemView.findViewById(R.id.building);
            add = (TextView) itemView.findViewById(R.id.address);
            postal = (TextView) itemView.findViewById(R.id.code);

        }
    }
}
