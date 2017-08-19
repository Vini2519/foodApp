package com.zap.foodapp.SignUp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zap.foodapp.Navigation;
import com.zap.foodapp.R;
import com.zap.foodapp.SQLiteDB;

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

public class AppSignupActivity extends AppCompatActivity {

    private static final String URL = "http://13.59.128.132:8080/foodcourt/customer/register";

    private TextView tvname, tvemail, tvphone, tvpassword, conpassword;
    private Button btregister;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_signup);

        tvname = (TextView) findViewById(R.id.name);
        tvemail = (TextView) findViewById(R.id.email);
        tvphone = (TextView) findViewById(R.id.no);
        tvpassword = (TextView) findViewById(R.id.passw);
        conpassword = (TextView) findViewById(R.id.repassword);
        btregister = (Button) findViewById(R.id.signup);

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute(URL);
                appData();

            }
        });
    }

    public void appData() {
        SQLiteDB.getInstance().insertAppData(tvname.getText().toString(),
                tvemail.getText().toString(),
                tvphone.getText().toString(),
                tvpassword.getText().toString()
        );

    }


    public static String POST(String URL, UserDetails userDetail) throws UnsupportedEncodingException {
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
            jsonObject.accumulate("password", userDetail.getPassword());

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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            userDetails = new UserDetails();
            userDetails.setName(tvname.getText().toString());
            userDetails.setEmail(tvemail.getText().toString());
            userDetails.setPhone(tvphone.getText().toString());
            userDetails.setPassword(tvpassword.getText().toString());

            try {
                return POST(urls[0], userDetails);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (validate() == false || tvpassword == conpassword) {
                Toast.makeText(getBaseContext(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AppSignupActivity.this, Navigation.class);
                startActivity(i);
                AppSignupActivity.this.finish();
            }
        }
    }

    private boolean validate() {
        String name = tvname.getText().toString().trim();
        String email = tvemail.getText().toString().trim();
        String password = tvpassword.getText().toString();
        String phone = tvphone.getText().toString();
        String repassword = conpassword.getText().toString();

        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Please enter Name!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter Email!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(phone)) {

            Toast.makeText(this, "Please enter Phone!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter Password!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(repassword)) {

            Toast.makeText(this, "Please enter ReType Password!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "Password do not match!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
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
}

