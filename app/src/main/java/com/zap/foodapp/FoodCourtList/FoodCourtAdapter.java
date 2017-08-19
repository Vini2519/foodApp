package com.zap.foodapp.FoodCourtList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.zap.foodapp.R;
import com.zap.foodapp.StallList.Stall_list;

import java.util.ArrayList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class FoodCourtAdapter extends RecyclerView.Adapter<FoodCourtAdapter.ViewHolder> implements Filterable {

    public static final String url = "http://13.59.128.132:8080/foodcourt/foodstall/list?";
    private ArrayList<FoodListItem> listItems;
    private Context context;
    private ArrayList<FoodListItem> mFilteredList;

    public FoodCourtAdapter(ArrayList<FoodListItem> listItem, Context context) {
        listItems = listItem;
        mFilteredList = listItem;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_court_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(mFilteredList.get(position).getName());
        holder.add.setText(mFilteredList.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                uri = uri.buildUpon().appendQueryParameter("foodcourt", listItems.get(position).getId()).build();
                Intent intent = new Intent(context, Stall_list.class);

                intent.putExtra("url", uri.toString());
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

                    mFilteredList = (ArrayList<FoodListItem>) listItems;
                } else {

                    ArrayList<FoodListItem> filteredList = new ArrayList<>();

                    for (FoodListItem androidVersion : listItems) {

                        if (androidVersion.getAddress().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString)) {

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
                mFilteredList = (ArrayList<FoodListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView add;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            add = (TextView) itemView.findViewById(R.id.address);

        }
    }
}
