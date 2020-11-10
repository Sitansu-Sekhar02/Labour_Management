package com.example.labourmangement.Owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.labourmangement.Adapter.AppliedJobsAdapter;
import com.example.labourmangement.Adapter.AppliedJobsOwnerAdapter;
import com.example.labourmangement.Contractor.AppliedJobs;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionForOwner;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.R;
import com.example.labourmangement.model.AppliedJobsModel;
import com.example.labourmangement.model.AppliedJobsModelOwner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppliedJobOwner extends AppCompatActivity {

    RecyclerView recyclerViewjobsowner;
    RecyclerView.LayoutManager layoutManager;
    private static final String TAG = AppliedJobOwner.class.getSimpleName();
    List<AppliedJobsModelOwner> joblist;
    ProgressDialog progressDialog;
    SessionForOwner sessionForOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job_owner);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        getSupportActionBar().setTitle("Applied Jobs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewjobsowner = (RecyclerView) findViewById(R.id.recycleViewjobsowner);
        recyclerViewjobsowner.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewjobsowner.setLayoutManager(layoutManager);

        joblist = new ArrayList<AppliedJobsModelOwner>();
        sessionForOwner=new SessionForOwner((getApplicationContext()));
        progressDialog= new ProgressDialog(AppliedJobOwner.this);

        sendRequest();
    }


    public void sendRequest() {

        HashMap<String, String> user = sessionForOwner.getUserDetails();

        // name
        String name = user.get(sessionForOwner.KEY_NAME);

        // email
        String email = user.get(sessionForOwner.KEY_EMAIL);

        Log.d(TAG, "Email "+email);
        progressDialog.setMessage("Loading....Please Wait..");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GETAPPLIEDJOBS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                progressDialog.dismiss();
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d(TAG, array.toString());
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject job = array.getJSONObject(i);
                        AppliedJobsModelOwner appliedJobs = new AppliedJobsModelOwner();
                        //adding the product to product list
                        appliedJobs.setJob_area(job.getString("job_area"));
                        appliedJobs.setJob_title(job.getString("job_title"));
                        appliedJobs.setJob_details(job.getString("job_details"));
                        appliedJobs.setJob_wages(job.getString("job_wages"));
                        appliedJobs.setJob_id(job.getString("job_id"));
                        appliedJobs.setApplied_by(job.getString("applied_by"));
                        appliedJobs.setCreated_by(job.getString("created_by"));
                        appliedJobs.setApplied_date(job.getString("applied_date"));
                        appliedJobs.setLabor_name(job.getString("labor_name"));
                        appliedJobs.setContractor_name(job.getString("contractor_name"));


                        joblist.add(appliedJobs);
                    }

                    Log.d(TAG, "jobgggggggggggggg" + joblist.size());
                    //creating adapter object and setting it to recyclerview
                    AppliedJobsOwnerAdapter adapter = new AppliedJobsOwnerAdapter(AppliedJobOwner.this, joblist);
                    recyclerViewjobsowner.setAdapter(adapter);
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
                            progressDialog.dismiss();
                            // hideDialog();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(AppliedJobOwner.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            progressDialog.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(AppliedJobOwner.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server error......................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(AppliedJobOwner.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(AppliedJobOwner.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(AppliedJobOwner.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }

                }){
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("created_by", user.get(SessionForOwner.KEY_EMAIL));
                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}