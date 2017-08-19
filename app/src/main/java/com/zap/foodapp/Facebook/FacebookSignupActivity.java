package com.zap.foodapp.Facebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.zap.foodapp.LoginActivity;
import com.zap.foodapp.Navigation;
import com.zap.foodapp.R;
import com.zap.foodapp.SQLiteDB;
import com.zap.foodapp.StaticData;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class FacebookSignupActivity extends AppCompatActivity {

    private TextView names, emails, phone;
    private static final String URL = "http://18.220.71.157:8080/foodcourt/customer/register";
    private Button button, login;
    FbUserDetails fbUserDetails;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_facebook_signup);

        button = (Button) findViewById(R.id.out);
        login = (Button) findViewById(R.id.sign_in);
        names = (TextView) findViewById(R.id.name);
        emails = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        final SharedPreferences intent = getSharedPreferences(StaticData.USER_PREF, MODE_PRIVATE);
        final String name = intent.getString("name", "");
        String email = intent.getString("email", "");
        id = intent.getString("id", "");
        names.setText(name);
        emails.setText(email);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(FacebookSignupActivity.this, LoginActivity.class);
                startActivity(i);
                FacebookSignupActivity.this.finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                new FacebookSignupActivity.HttpAsyncTask().execute(URL);
                data();


            }
        });
    }

    public void data() {
        SQLiteDB.getInstance().insertFbData(id,
                names.getText().toString(),
                emails.getText().toString(),
                phone.getText().toString()
                );

    }

    public static String POST(String URL, FbUserDetails userDetail) throws UnsupportedEncodingException {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", userDetail.getName());
            jsonObject.accumulate("email", userDetail.getEmail());
            jsonObject.accumulate("phone", userDetail.getPhone());
            jsonObject.accumulate("id", userDetail.getId());

            String query =

                    json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            fbUserDetails = new FbUserDetails();
            fbUserDetails.setName(names.getText().toString());
            fbUserDetails.setEmail(emails.getText().toString());
            fbUserDetails.setPhone(phone.getText().toString());
            fbUserDetails.setId((id));

            try {
                return POST(urls[0], fbUserDetails);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (validate() == false) {
                Toast.makeText(getBaseContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FacebookSignupActivity.this, Navigation.class);
                startActivity(i);
                FacebookSignupActivity.this.finish();
            }
        }
    }

    private boolean validate() {
        String name = names.getText().toString().trim();
        String email = emails.getText().toString().trim();
        String phones = phone.getText().toString();

        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Please enter Name!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter Email!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(phones)) {

            Toast.makeText(this, "Please enter Phone!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}


