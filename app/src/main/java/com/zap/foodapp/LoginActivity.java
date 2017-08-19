package com.zap.foodapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zap.foodapp.Facebook.FacebookSignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Button sign_in_button;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private TextView forgot, phonenumber, password;
    String idString, nameString, emailString;
    private String appPhoneNumber, appPassword;

    String url = "http://18.220.71.157:8080/foodcourt/customer/login";

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.fb);
        sign_in_button = (Button) findViewById(R.id.sign_in_button);
        forgot = (TextView) findViewById(R.id.forgot);
        phonenumber = (TextView) findViewById(R.id.phonenumber);
        password = (TextView) findViewById(R.id.password);

        builder = new AlertDialog.Builder(LoginActivity.this);


        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appPhoneNumber = phonenumber.getText().toString().trim();
                    appPassword = password.getText().toString().trim();

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    String URL = "http://18.220.71.157:8080/foodcourt/customer/login";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("phone",appPhoneNumber);
                    jsonBody.put("password",appPassword);
                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("LOG_VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");

                            } catch (UnsupportedEncodingException uee) {

                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Contact_us.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        if (LoginActivity.CheckNetwork.isInternetAvailable(LoginActivity.this)) //returns true if internet available
        {
            FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {

                                        String id = object.getString("id");
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        LoginActivity.this.idString = (id);
                                        LoginActivity.this.nameString = (name);
                                        LoginActivity.this.emailString = (email);
                                        Intent intent = new Intent(LoginActivity.this, FacebookSignupActivity.class);
                                        getSharedPreferences(StaticData.USER_PREF, MODE_PRIVATE).edit()
                                                .putString("id", idString)
                                                .putString("name", nameString)
                                                .putString("email", emailString)
                                                .apply();
                                        startActivity(intent);
                                        LoginActivity.this.finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(LoginActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
                }
            };
            loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
            loginButton.registerCallback(callbackManager, callback);
        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public static class CheckNetwork {

        private static final String TAGS = LoginActivity.CheckNetwork.class.getSimpleName();

        public static boolean isInternetAvailable(Context context) {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null) {
                Log.d(TAGS, "no internet connection");
                return false;
            } else {
                if (info.isConnected()) {
                    Log.d(TAGS, " internet connection available...");
                    return true;
                } else {
                    Log.d(TAGS, " internet connection");
                    return true;
                }

            }
        }
    }
}
