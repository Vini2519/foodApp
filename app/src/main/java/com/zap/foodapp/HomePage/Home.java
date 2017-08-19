package com.zap.foodapp.HomePage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zap.foodapp.DeliveryLocation.deliveryLocation;
import com.zap.foodapp.DeliveryTime;
import com.zap.foodapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Home extends Fragment {


    private TextView textView;
    private static final String URL = "http://13.59.128.132:8080/foodcourt/deliverylocation/list";
    private AutoCompleteTextView autoCompleteTextView;
    String[] data;
    List<String> idString, idTime, data1;

    public Home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = (TextView) view.findViewById(R.id.text);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autotext);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), deliveryLocation.class);
                getContext().startActivity(intent);
            }
        });

        loadRecycleData();
        return view;


    }


    private void loadRecycleData() {
        StringRequest string = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    JSONArray array = jsonObj.getJSONArray("deliveryLocations");
                    data = new String[array.length()];
                    idString = new ArrayList<>();
                    idTime =  new ArrayList<>();
                    data1 = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        String id = o.getString("id");
                        String time = o.getString("deliveryTiming");
                        idString.add(id);
                        idTime.add(time);
                        data[i] = o.getString("building");
                        data1.add(data[i]);
                    }

                    autoCompleteTextView.setAdapter(new ArrayAdapter<String>(autoCompleteTextView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, data));
                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            String d = (String)adapterView.getItemAtPosition(pos);
                            int i = data1.indexOf(d);
                            Intent intent = new Intent(view.getContext(), DeliveryTime.class);
                            intent.putExtra("DeliveryTime", idTime.get(i));
                            intent.putExtra("id", idString.get(i));
                            view.getContext().startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        request.add(string);
    }

}
