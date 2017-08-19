package com.zap.foodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zap.foodapp.FoodCourtList.FoodCourtList;

import org.json.JSONArray;


public class DeliveryTime extends AppCompatActivity {


    String[] items;
    public static final String url = "http://18.220.71.157:8080/foodcourt/foodcourt/list?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_time);

        final Intent i = getIntent();
        final String timeArrayString = i.getStringExtra("DeliveryTime");
        final String idStrings = i.getStringExtra("id");

        try {

            JSONArray timeArray = new JSONArray(timeArrayString);

            items = new String[timeArray.length()];
            for (int j = 0; j < timeArray.length(); j++) {
                items[j] = timeArray.getString(j);
            }
            ListView listView = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Uri uri = Uri.parse(url);
                    uri = uri.buildUpon().appendQueryParameter("deliverylocation", idStrings).build();
                    uri = uri.buildUpon().appendQueryParameter("deliverytime", items[i]).build();
                    Intent intent = new Intent(DeliveryTime.this, FoodCourtList.class);
                    intent.putExtra("url", uri.toString());
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
