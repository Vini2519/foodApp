package com.zap.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.zap.foodapp.Facebook.FacebookSignupActivity;
import com.zap.foodapp.SignUp.AppSignupActivity;

public class BasicActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_basic_activiy);

        Button login = (Button) findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.signup);
        SQLiteDB.initialize(BasicActiviy.this);

        SQLiteDB.getInstance().deleteAll();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile(Profile.getCurrentProfile());
            }
        });

        signup.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BasicActiviy.this, AppSignupActivity.class);
                startActivity(intent);

            }
        });
    }

    public void profile(Profile profile) {
        Intent intent = null;
        if (profile != null) {
            intent = new Intent(BasicActiviy.this, FacebookSignupActivity.class);

        } else if (profile == null) {
            intent = new Intent(BasicActiviy.this, LoginActivity.class);

        }
        startActivity(intent);

    }
}
