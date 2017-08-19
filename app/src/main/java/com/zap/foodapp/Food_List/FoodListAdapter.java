package com.zap.foodapp.Food_List;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.zap.foodapp.R;
import com.zap.foodapp.SQLiteDB;

import java.util.ArrayList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> implements Filterable {

    private ArrayList<FoodItem> listItems;
    private Context context;
    private ArrayList<FoodItem> mFilteredList;


    public FoodListAdapter(ArrayList<FoodItem> listItem, Context context) {
        listItems = listItem;
        mFilteredList = listItem;
        this.context = context;
        SQLiteDB.initialize(context);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foodlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.name.setText(mFilteredList.get(position).getName());
        holder.add.setText(mFilteredList.get(position).getRate());
        holder.no.setText(mFilteredList.get(position).getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                if (SQLiteDB.getInstance().ifExist(mFilteredList.get(position).getId())) {
                    Toast.makeText(context, mFilteredList.get(position).getName() + "Already in the cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                values.put(SQLiteDB.KEY_ID, mFilteredList.get(position).getId());

                values.put(SQLiteDB.KEY_NAME, mFilteredList.get(position).getName());

                values.put(SQLiteDB.KEY_BASEPRICE, mFilteredList.get(position).getPhone());

                values.put(SQLiteDB.KEY_TAKEAWAYPRICE, mFilteredList.get(position).getCusines());

                values.put(SQLiteDB.KEY_MAXQUANTITY, mFilteredList.get(position).getStallno());

                values.put(SQLiteDB.KEY_CUSINE, mFilteredList.get(position).getRate());

                Toast.makeText(context, "Added " + mFilteredList.get(position)
                        .getName() + " in the cart", Toast.LENGTH_SHORT).show();
                values.put(SQLiteDB.KEY_QUANTITY, 1);
                SQLiteDB.getInstance().insert(values);

                String db = String.valueOf(SQLiteDB.getInstance().countRecords());

                FoodList.getTextview().setText(db);
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

                    mFilteredList = (ArrayList<FoodItem>) listItems;
                } else {

                    ArrayList<FoodItem> filteredList = new ArrayList<>();

                    for (FoodItem androidVersion : listItems) {

                        if (androidVersion.getStallno().toLowerCase().contains(charString) || androidVersion.getRate().toLowerCase().contains(charString)) {

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
                mFilteredList = (ArrayList<FoodItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView add, no;
        public TextView cart;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.fdname);
            add = (TextView) itemView.findViewById(R.id.fdstall);
            no = (TextView) itemView.findViewById(R.id.fdphone);
            cart = (TextView) itemView.findViewById(R.id.item_count);

        }
    }
}
