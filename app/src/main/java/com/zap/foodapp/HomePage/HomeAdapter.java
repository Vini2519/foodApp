package com.zap.foodapp.HomePage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.zap.foodapp.DeliveryTime;
import com.zap.foodapp.R;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by lenovo on 21-07-2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<HomeListItem> listItems;
    private Context context;
    private ArrayList<HomeListItem> mFilteredList;

    public HomeAdapter(ArrayList<HomeListItem> listitem) {
        listItems = listitem;
        mFilteredList = listitem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {


        holder.building.setText(mFilteredList.get(position).getBuilding_name());
        holder.add.setText(mFilteredList.get(position).getAddress());
        holder.postal.setText(mFilteredList.get(position).getId());


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

                    mFilteredList = listItems;
                } else {

                    ArrayList<HomeListItem> filteredList = new ArrayList<>();

                    for (HomeListItem androidVersion : listItems) {

                        if (androidVersion.getAddress().toLowerCase().contains(
                                charString) || androidVersion.getBuilding_name().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                            Log.d(charString, androidVersion.getAddress() + "   " + androidVersion.getBuilding_name());
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
                mFilteredList = (ArrayList<HomeListItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
