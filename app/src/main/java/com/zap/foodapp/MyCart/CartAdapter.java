package com.zap.foodapp.MyCart;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zap.foodapp.R;
import com.zap.foodapp.SQLiteDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 12-08-2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemViewHolder> {

    private List<CartItem> productList = new ArrayList<CartItem>();
    private MyCart context;

    public CartAdapter(Application application, MyCart context) {

        this.context = context;
        productList = SQLiteDB.getInstance().getAllContacts();
    }


    @Override
    public CartAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cartlist, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ItemViewHolder holder, final int position) {
        holder.quanitity.setText(productList.get(position).getQuantity() + "");
        holder.itemName.setText(productList.get(position).getName());
        holder.itemcusine.setText(productList.get(position).getRate());
        holder.itemCost.setText((Float.parseFloat(productList.get(position).getBasePrice()) * productList.get(position).getQuantity()) + "");

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productList.get(position).getQuantity() >= productList.get(position).getMaxquantity()) {
                    Toast.makeText(context, "Item Exceed", Toast.LENGTH_SHORT).show();
                    return;
                }
                productList.get(position).setQuantity(productList.get(position).getQuantity() + 1);
                SQLiteDB.getInstance().quantity(productList.get(position).getId(), productList.get(position).getQuantity());
                holder.quanitity.setText(productList.get(position).getQuantity() + "");
                float a = Float.parseFloat(productList.get(position).getBasePrice()) * productList.get(position).getQuantity();
                holder.itemCost.setText(String.format("%.2f", a));


            }

        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productList.get(position).getQuantity() < 2)
                    return;
                productList.get(position).setQuantity(productList.get(position).getQuantity() - 1);
                SQLiteDB.getInstance().quantity(productList.get(position).getId(), productList.get(position).getQuantity());
                holder.quanitity.setText(productList.get(position).getQuantity() + "");
                float a = Float.parseFloat(productList.get(position).getBasePrice()) * productList.get(position).getQuantity();
                holder.itemCost.setText(String.format("%.2f", a));


            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemcusine, itemCost, quanitity, addItem, removeItem;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.name);

            itemcusine = (TextView) itemView.findViewById(R.id.cusine);

            itemCost = (TextView) itemView.findViewById(R.id.price);

            addItem = ((TextView) itemView.findViewById(R.id.add_item));

            removeItem = ((TextView) itemView.findViewById(R.id.remove_item));

            quanitity = (TextView) itemView.findViewById(R.id.iteam_amount);


        }
    }

}
