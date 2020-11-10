package com.example.labourmangement.Contractor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.example.labourmangement.CustomLoader;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.Labour.JobDetails;
import com.example.labourmangement.Labour.Register_labour;
import com.example.labourmangement.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PostJobs extends AppCompatActivity {
    private static final String TAG = PostJobs.class.getSimpleName();

    EditText etjobtitle,etjobdetails,etjobwages,etjobarea;
    Button btnpost;
    String jobtittleholder,jobdetailsholder,jobwagesholder,jobareaholder,Emailholder,ContractorNameHolder,roleholder;
    SessionManagerContractor sessionManagerContractor;
    int flag;
    //loader loader;
    CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jobs);
        getSupportActionBar().setTitle("Job Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        etjobtitle=(EditText)findViewById(R.id.editjobttitle);
        etjobdetails=(EditText)findViewById(R.id.editjobdetails);
        etjobwages=(EditText)findViewById(R.id.editjobwages);
        etjobarea=(EditText)findViewById(R.id.editjobarea);
        btnpost=(Button)findViewById(R.id.submijobdata);

etjobtitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {
     Log.d(TAG,"BHANU"+view.getId());
    }
});

        sessionManagerContractor=new SessionManagerContractor((getApplicationContext()));

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManagerContractor.checkLogin();
                HashMap<String, String> user = sessionManagerContractor.getUserDetails();

                // name
                String name = user.get(SessionManagerContractor.KEY_NAME);

                // email
                String email = user.get(SessionManagerContractor.KEY_EMAIL);
                String role=user.get(SessionManagerContractor.KEY_ROLE);
                Log.d(TAG, "Email "+email);



                jobtittleholder= etjobtitle.getText().toString().trim();

                                jobareaholder = etjobarea.getText().toString().trim();
                jobdetailsholder = etjobdetails.getText().toString().trim();
                jobwagesholder = etjobwages.getText().toString().trim();
                Emailholder=email;
                ContractorNameHolder=name;
                roleholder=role;


                flag=0;
                if(etjobtitle.getText().toString().length()==0){
                    etjobtitle.setError(" Enter Job Title");
                    etjobtitle.requestFocus();
                    flag=1;

                }
                flag=0;
                if(etjobarea.getText().toString().length()==0){
                    etjobarea.setError(" Enter Job Area");
                    etjobarea.requestFocus();
                    flag=1;

                }
                flag=0;
                if(etjobdetails.getText().toString().length()==0){
                    etjobdetails.setError(" Enter Job Details");
                    etjobdetails.requestFocus();
                    flag=1;

                }
                flag=0;
                if(etjobwages.getText().toString().length()==0){
                    etjobwages.setError(" Enter Job Wages");
                    etjobwages.requestFocus();
                    flag=1;

                }

                if(flag==0){
                    postnewjob();
                }
            }
        });

    }


    private void postnewjob(){
        loader.show();
        Log.d(TAG, "Inserting Response:hiiiiiiiiiiiiiii ");
        Log.d(TAG, "ROLE "+roleholder);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTJOB,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Inserting Response:hiiiiiiiiiiiiiii " + response.toString());
                loader.dismiss();
                //hideDialog();
                Log.i("bhagyaaaaaa", "["+response+"]");
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    AlertDialog alertDialog = new AlertDialog.Builder(PostJobs.this).create();
                    alertDialog.setTitle("Job Post ");
                    alertDialog.setMessage(response);
                    alertDialog.setIcon(R.drawable.done);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            System.out.println("Time Out and NoConnection...................." + error);
                            loader.dismiss();
                            // hideDialog();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(PostJobs.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(PostJobs.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(PostJobs.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(PostJobs.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(PostJobs.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_title", jobtittleholder);
                params.put("job_details", jobdetailsholder);
                params.put("job_wages", jobwagesholder);
                params.put("job_area", jobareaholder);
                params.put("created_by", Emailholder);
                params.put("contractor_name", ContractorNameHolder);
                params.put("role",roleholder);



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(PostJobs.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


        etjobarea.setText("");
        etjobdetails.setText("");
        etjobtitle.setText("");
        etjobwages.setText("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {
            case R.id.eng:
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_post_jobs);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_post_jobs);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_post_jobs);
                break;

            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!,https://drive.google.com/file/d/1qnIAtbiBw4St_HKagdUE5-2-VFlfLlOc/view?usp=sharing");
                startActivity(shareIntent);

                break;

            case R.id.viewjob:
                Intent i3=new Intent(PostJobs.this,AllJobs.class);
                startActivity(i3);
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
