package com.example.labourmangement.Contractor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.Labour.JobDetails;
import com.example.labourmangement.Labour.JobStatusApproval;
import com.example.labourmangement.R;
import com.example.labourmangement.model.JobModel;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JobApplyDetails extends AppCompatActivity {
    private static final String TAG = JobApplyDetails.class.getSimpleName();

    private Button btn_approve,btn_reject;
    SessionManager session;
    SessionManagerContractor sessionManagerContractor;
    ArrayList<JobModel> mjoblist;
   // loader loader;
    CustomLoader loader;
    String jobtitle,jobdetails,jobwages,jobarea,jobid,appliedby,applieddate,laborname,contractorname;
    TextView name,destcription,area,wages,id,textmgstitle,textmessagebody,apppliedby,applied_date,labor_name,contractor_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply_details);

        getSupportActionBar().setTitle("Approve/Reject Jobs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        getIncomingIntent();
        session = new SessionManager(getApplicationContext());
        sessionManagerContractor = new SessionManagerContractor(getApplicationContext());

     //   loader = new loader(JobApplyDetails.this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


        btn_approve=(Button) findViewById(R.id.btnapprove);
        btn_reject=(Button) findViewById(R.id.btnreject);

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendrejection();
                Toast.makeText(JobApplyDetails.this, "Application Rejected", Toast.LENGTH_LONG).show();
                btn_approve.setEnabled(false);
                btn_approve.setBackground(getResources().getDrawable(R.drawable.button_not_pressed));

            }
        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendapproval();
                Toast.makeText(JobApplyDetails.this, "Application Approved", Toast.LENGTH_LONG).show();
                btn_reject.setEnabled(false);
                btn_reject.setBackground(getResources().getDrawable(R.drawable.button_not_pressed));

            }
        });

    }




    private void sendapproval() {



        HashMap<String, String> user1 = sessionManagerContractor.getUserDetails();

        // name
        String namecon = user1.get(SessionManagerContractor.KEY_NAME);

        // email
        String emailcon = user1.get(SessionManagerContractor.KEY_EMAIL);

        String role=user1.get((SessionManagerContractor.KEY_ROLE));

        Log.d(TAG, "Email: " + emailcon);
        Log.d(TAG, "NAmew: " + namecon);


        loader.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTAPPROVALREQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Inserting Response: " + response.toString());
                loader.dismiss();
//                //hideDialog();
//                Log.i("tagconvertstr", "[" + response + "]");
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(JobApplyDetails.this).create();
//                    alertDialog.setTitle("Applied Job Details ");
//                    alertDialog.setMessage(" Your Job Is Approved");
//                    alertDialog.setIcon(R.drawable.done);
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


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
                            Toast.makeText(JobApplyDetails.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("created_by", emailcon);

                Log.d(TAG, "PP: " + params);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(JobApplyDetails.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }



    private void sendrejection() {
        jobtitle = name.getText().toString();
        jobdetails = destcription.getText().toString();
        jobwages = wages.getText().toString();
        jobarea = area.getText().toString();
        jobid = id.getText().toString();
        String status = "Reject";

        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);


        HashMap<String, String> user1 = sessionManagerContractor.getUserDetails();

        // name
        String name1 = user1.get(SessionManagerContractor.KEY_NAME);

        // email
        String email1 = user1.get(SessionManagerContractor.KEY_EMAIL);

        loader.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTREJECTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Inserting Response: " + response.toString());
                loader.dismiss();
                //hideDialog();
                Log.i("tagconvertstr", "[" + response + "]");
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(),
                            jsonObject.getString("message") + response,
                            Toast.LENGTH_LONG).show();


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
                            Toast.makeText(JobApplyDetails.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(JobApplyDetails.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", jobid);
                params.put("job_title", jobtitle);
                params.put("job_details", jobdetails);
                params.put("job_wages", jobwages);
                params.put("job_area", jobarea);
                params.put("applied_by", email);
                params.put("rejected_by", email1);
                params.put("status", status);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(JobApplyDetails.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }



    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");


        if (getIntent().hasExtra("job_title") && getIntent().hasExtra("job_details")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            jobtitle = getIntent().getStringExtra("job_title");
            jobdetails = getIntent().getStringExtra("job_details");
            jobwages = getIntent().getStringExtra("job_wages");
            jobarea = getIntent().getStringExtra("job_area");
            jobid = getIntent().getStringExtra("job_id");
            appliedby = getIntent().getStringExtra("applied_by");
            applieddate = getIntent().getStringExtra("applied_date");
            laborname = getIntent().getStringExtra("labor_name");
            contractorname = getIntent().getStringExtra("contractor_name");


            setImage(jobtitle, jobdetails, jobwages, jobarea ,jobid, appliedby,applieddate,laborname,contractorname);
            Log.d(TAG, "applied dateoooooo"+applied_date);
            // setImage( image_path,product_name);
        }


    }


    private void setImage(String job_title, String job_deatils, String job_wages, String job_area, String jobid, String appliedby, String applieddate, String laborname, String contractorname) {
        {
            Log.d(TAG, "setImage: setting te image and name to widgets.");
            //Intent intent=getIntent();
            // String imagepath=intent.getStringExtra("image_path");

            name = findViewById(R.id.fetchjobtitleapplied);
            name.setText(job_title);

            destcription = findViewById(R.id.fetchjobdetailsapplied);
            destcription.setText(job_deatils);

            wages = findViewById(R.id.fetchjobwagesapplied);
            wages.setText(job_wages);

            area = findViewById(R.id.fetchjobareaapplied);
            area.setText(job_area);

            id=findViewById(R.id.id);
            id.setText(jobid);

            apppliedby =findViewById(R.id.fetchappliedbyname);
            apppliedby.setText(appliedby);

            applied_date=findViewById(R.id.fetchapplieddate);
            applied_date.setText(applieddate);

            labor_name=findViewById(R.id.fetchlaborname);
            labor_name.setText(laborname);

            contractor_name=findViewById(R.id.fetchcontractorname);
            contractor_name.setText(contractorname);



        }
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
                this.setContentView(R.layout.activity_job_apply_details);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_job_apply_details);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_job_apply_details);
                break;
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!,https://drive.google.com/file/d/1qnIAtbiBw4St_HKagdUE5-2-VFlfLlOc/view?usp=sharing");
                startActivity(shareIntent);

                break;

            case R.id.viewjob:
                Intent i3=new Intent(JobApplyDetails.this,AllJobs.class);
                startActivity(i3);
                break;


          /*  case R.id.logout:
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(LabourDashboard.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure! you want Logout?");

                alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        session.logoutUser();

                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        sendBroadcast(broadcastIntent);

                        Intent intent1 = new Intent(LabourDashboard.this, MainActivity.class);
                        intent1.putExtra("finish", true);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                    }
                });

                // Setting Negative "NO" Button
                alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog2.show();

                return true;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
