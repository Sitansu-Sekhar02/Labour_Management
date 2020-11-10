package com.example.labourmangement.Developer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionForDeveloper;
import com.example.labourmangement.DatabaseHelper.SessionForEngineer;
import com.example.labourmangement.Engineer.EngineerDashboard;
import com.example.labourmangement.Engineer.LoginEngineer;
import com.example.labourmangement.Engineer.Register_Engineer;
import com.example.labourmangement.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register_developer extends AppCompatActivity {
    EditText engname,engphone,engpass,engconpass,et_referralcode,et_referralname;
    Button btn_registeteng;
    TextView alredyexist;
    ProgressDialog pDialog;
    SessionForDeveloper sessionForDeveloper;
    String  namePattern = "[a-zA-Z]+";
    String MobilePattern = "[0-9]{10}";
    private static final String TAG = Register_developer.class.getSimpleName();
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_developer);
        getSupportActionBar().hide();

        alredyexist=findViewById(R.id.developerexit);

        engname=findViewById(R.id.regi_devname);
        engphone=findViewById(R.id.regi_devphone);
        engpass=findViewById(R.id.regi_devpass);
        engconpass=findViewById(R.id.regi_devconpass);
        btn_registeteng=findViewById(R.id.btn_registerdev);
        et_referralcode=(EditText)findViewById(R.id.editrefercodeD);
        et_referralname=(EditText)findViewById(R.id.editrefrednameD);



        alredyexist.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginDeveloper.class);
                startActivity(i);
                finish();
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        sessionForDeveloper = new SessionForDeveloper(getApplicationContext());

        if (sessionForDeveloper.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Register_developer.this,
                    DeveloperDashboard.class);
            startActivity(intent);
            finish();
        }

        btn_registeteng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = engname.getText().toString().trim();
                String email = engphone.getText().toString().trim();
                String password = engpass.getText().toString().trim();
                String cpassword = engconpass.getText().toString().trim();
                String refName = et_referralname.getText().toString().trim();
                String refCode = et_referralcode.getText().toString().trim();


                flag=0;
                if(engname.getText().toString().length()==0 || engname.getText().toString().trim().matches(namePattern)){
                    engname.setError(" User Name should be valid");
                    engname.requestFocus();
                    flag=1;

                }
                flag=0;
                if(engpass.getText().toString().length()< 4 || engpass.length()>10){
                    engpass.setError(" Password should be between 4 to 10 character");
                    engpass.requestFocus();
                    flag=1;

                }

                flag=0;
                if(engphone.getText().toString().matches(MobilePattern)){
                    engphone.setError(" Mobile number should be valid");
                    engphone.requestFocus();
                    flag=1;

                }
                flag=0;
                if(cpassword.isEmpty() || !cpassword.equals(password))
                {

                    Toast.makeText(Register_developer.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    flag=1;
                }
                if(flag==0){
                    registerUser(name,email,password,refName,refCode);
                }

            }
        });

    }

    private void registerUser(final String name, final String email,
                              final String password, final String refname,final String refcode ) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String role="developer";
        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTERCONTRACTOR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String role = user.getString("role");
                        //String role=user.getString("role");
                        String created_at = user
                                .getString("created_at");

                        String refName = user.getString("ref_name");
                        String refCode = user.getString("ref_code");

                        sessionForDeveloper.createUserLoginSession(email,name,role,refName,refCode);
                        Log.d(TAG, "Email contractor"+email);
                        Log.d(TAG, "name contractor "+name);

                        // Inserting row in users table
                        // db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                Register_developer.this,
                                LoginDeveloper.class);

                        startActivity(intent);
                        finish();
                        // Launch login activity
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    System.out.println("Time Out and NoConnection...................." + error);
                    pDialog.dismiss();
                    // hideDialog();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_developer.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    System.out.println("AuthFailureError.........................." + error);
                    // hideDialog();
                    pDialog.dismiss();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_developer.this, "Your Are Not Authrized..", duration).show();
                } else if (error instanceof ServerError) {
                    System.out.println("server erroer......................." + error);
                    //hideDialog();
                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_developer.this, "Server Error", duration).show();
                    //TODO
                } else if (error instanceof NetworkError) {
                    System.out.println("NetworkError........................." + error);
                    //hideDialog();
                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_developer.this, "Please Check Your Internet Connection", duration).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    System.out.println("parseError............................." + error);
                    //hideDialog();
                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_developer.this, "Error While Data Parsing", duration).show();

                    //TODO
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("role",role);
                params.put("ref_name",refname);
                params.put("ref_code",refcode);


                return params;
            }

        };

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        // requestQueue.add(strReq);
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }
}