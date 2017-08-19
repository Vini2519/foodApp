package com.zap.foodapp.StallList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zap.foodapp.Food_List.FoodList;
import com.zap.foodapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class StallAdapter extends RecyclerView.Adapter<StallAdapter.ViewHolder> implements Filterable {

    public static final String urls = "http://18.220.71.157:8080/foodcourt/fooditem/list?";
    private ArrayList<StallListItem> listItems;
    private Context context;
    private ArrayList<StallListItem> mFilteredList;


    public StallAdapter(ArrayList<StallListItem> listItem, Context context) {
        listItems = listItem;
        mFilteredList = listItem;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stall_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.building.setText(mFilteredList.get(position).getName());
        holder.address.setText(mFilteredList.get(position).getStallno());
        try {
            String postal = "";
            JSONArray postalArray = new JSONArray(mFilteredList.get(position).getRate());
            for (int i = 0; i < postalArray.length(); i++) {
                postal += postalArray.getString(i);
                postal += ", ";
            }
            postal = postal.substring(0, postal.length() - 2);
            holder.postal.setText(postal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.ratingBar.setRating(Float.parseFloat(mFilteredList.get(position).getCusines()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse(urls);
                uri = uri.buildUpon().appendQueryParameter("foodstall", listItems.get(position).getEmail()).build();
                Intent intent = new Intent(context, FoodList.class);
                intent.putExtra("urls", uri.toString());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = (ArrayList<StallListItem>) listItems;
                } else {

                    ArrayList<StallListItem> filteredList = new ArrayList<>();

                    for (StallListItem androidVersion : listItems) {

                        if (androidVersion.getStallno().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<StallListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView building;
        public TextView postal;
        public TextView address;
        public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            building = (TextView) itemView.findViewById(R.id.stname);
            postal = (TextView) itemView.findViewById(R.id.stphone);
            address = (TextView) itemView.findViewById(R.id.address);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);

        }
    }
}
