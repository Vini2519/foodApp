package com.zap.foodapp.MyCart;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zap.foodapp.Payment;
import com.zap.foodapp.R;
import com.zap.foodapp.SQLiteDB;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    private static RecyclerView recyclerView;
    TextView fee, total,totalfee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button check = (Button) findViewById(R.id.checkout);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        fee = (TextView) findViewById(R.id.fee);
        total = (TextView) findViewById(R.id.total);
        totalfee=(TextView)findViewById(R.id.totalfee);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartAdapter shoppinListAdapter = new CartAdapter(getApplication(), this);


        recyclerView.setAdapter(shoppinListAdapter);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCart.this, Payment.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
