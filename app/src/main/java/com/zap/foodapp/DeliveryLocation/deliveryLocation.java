package com.zap.foodapp.DeliveryLocation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zap.foodapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class deliveryLocation extends AppCompatActivity {

    private static final String URL = "http://13.59.128.132:8080/foodcourt/deliverylocation/list";

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private ArrayList<ListItem> listItems;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location);


        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        loadRecycleData();

    }

    private void loadRecycleData() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest string = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray array = jsonObj.getJSONArray("deliveryLocations");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        ListItem list = new ListItem(
                                o.getString("address"),
                                o.getString("building"),
                                o.getString("postalCode"),
                                o.getString("id"),
                                o.getString("deliveryTiming")
                        );
                        listItems.add(list);
                    }
                    adapter = new MyAdapter(listItems, deliveryLocation.this);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue request = Volley.newRequestQueue(this);
        request.add(string);

    }
}
