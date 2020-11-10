package com.example.labourmangement.Labour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.labourmangement.Admin.MainActivity;
import com.example.labourmangement.Contractor.ContractorDashboard;
import com.example.labourmangement.Contractor.PostJobs;
import com.example.labourmangement.CustomLoader;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LabourDashboard extends AppCompatActivity {
    private static final String TAG = LabourDashboard.class.getSimpleName();
EditText labor_uname,labor_mobnum,labor_age,labor_address,labor_wages,labor_workinghours,labor_interestedarea,labor_refrename,labor_refercode,labor_interestarea1;
String labornameholder,labormobnumholder,laborageholder,laborgenderholder,laborwageholder,laborinterestedon,laboraddressholder,laborworkinghourholder,laborspinnerholder,laborinterestedareaholder,labortransportholder,laborinterestedareaholder1;
RadioGroup rg_gender,rg_interestedon;
RadioButton rb_daily,rbmothly,rb_weekly,rb_male,rb_female;
    private SessionManager session;
    Button btnsubmit;
    MaterialBetterSpinner categoryspinner,modeoftransport,wageratespinner,workinghourspinner;
    int flag;
   CustomLoader loader;
    String[] SPINNER_DATA = {"General labor",
           "RCC carpenter",
            "RCC fitter",
            "Mason - sub category - brickwork, ucr masonry, waterproofing",
            "Plaster",
           "Tile fixer",
            "Plumber",
            "Fabricator",
            "Electrician",
            "POP worker",
            "Painter",
            "Furniture carpenter"};

    String[] SPINNERWAGERATE = {"300-400","400-500","500-600","600-700","700-800","800-900"};

    String[] SPINNERWORKINGHOUR = {"9AM-5PM","9:30AM-10:30PM","10AM-6PM","10:30AM-6:30PM"};

    String[] SPINNER_DATA_FORMODEOFTRANSPORT = {"Walk","Two Wheeler","Bus","Auto","Cab"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_dashboard);


        getSupportActionBar().setTitle("Labor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        labor_uname = (EditText) findViewById(R.id.etlabour_username);
        labor_mobnum = (EditText) findViewById(R.id.etlabour_mobilenum);
        labor_age = (EditText) findViewById(R.id.etlabour_age);

        labor_address = (EditText) findViewById(R.id.etlabour_postaladdress);
        wageratespinner = (MaterialBetterSpinner) findViewById(R.id.wageratespinner);

        workinghourspinner = (MaterialBetterSpinner) findViewById(R.id.workinghourspinner);
        labor_interestedarea = (EditText) findViewById(R.id.etlabour_workingarea1);
        labor_interestarea1 = (EditText) findViewById(R.id.etlabour_workingarea2);
        labor_refrename = (EditText) findViewById(R.id.edit_refrename);
        labor_refercode = (EditText) findViewById(R.id.edit_refrcode);

        modeoftransport = (MaterialBetterSpinner) findViewById(R.id.category_modeoftransport);
        btnsubmit = (Button) findViewById(R.id.button_submitlabordata);
        rg_gender = (RadioGroup) findViewById(R.id.radiogroupgender);
        rg_interestedon = (RadioGroup) findViewById(R.id.radiogroupinterestworkon);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        getAllData();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                flag = 0;
                if (labor_uname.getText().toString().equals("")) {
                    labor_uname.setError("Enter Username");
                    labor_uname.requestFocus();
                    flag = 1;
                }

                if (labor_address.getText().toString().length() == 0) {
                    labor_address.setError(" Enter Address");
                    labor_address.requestFocus();
                    flag = 1;
                }

                if (labor_age.getText().toString().length() == 0) {
                    labor_age.setError(" Enter Age");
                    labor_age.requestFocus();
                    flag = 1;
                }


                if (labor_interestedarea.getText().toString().length() == 0) {
                    labor_interestedarea.setError(" Enter Interested Area of work");
                    labor_interestedarea.requestFocus();
                    flag = 1;
                }
                if (flag == 0) {
                    getandset();


                }
                // getandset();
            }
        });

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        String ref_name=user.get(SessionManager.KEY_REFNAME);

        String ref_code=user.get(SessionManager.KEY_REFCODE);

        labor_uname.setText(name);
        labor_mobnum.setText(email);
        labor_refercode.setText(ref_code);
        labor_refrename.setText(ref_name);

        categoryspinner = (MaterialBetterSpinner) findViewById(R.id.category_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        categoryspinner.setAdapter(adapter);

        ArrayAdapter<String> adaptermodeoftransport = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA_FORMODEOFTRANSPORT);

        modeoftransport.setAdapter(adaptermodeoftransport);


        ArrayAdapter<String> adapterwagerate = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNERWAGERATE);

        wageratespinner.setAdapter(adapterwagerate);

        ArrayAdapter<String> adapterwokinghour = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNERWORKINGHOUR);

        workinghourspinner.setAdapter(adapterwokinghour);


        categoryspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                laborspinnerholder = (String) adapterView.getItemAtPosition(i);
            }
        });

        workinghourspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                laborworkinghourholder = (String) adapterView.getItemAtPosition(i);
            }
        });
        wageratespinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                laborwageholder = (String) adapterView.getItemAtPosition(i);
            }
        });

        modeoftransport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                labortransportholder = (String) adapterView.getItemAtPosition(i);
            }
        });

    }
    private void getandset(){
        GetValueFromEditText();


        Log.d(TAG, "name  "+labornameholder);
        Log.d(TAG, "email  "+labormobnumholder);
        Log.d(TAG, "Age "+laborageholder);
        Log.d(TAG, "Area "+laborinterestedareaholder);
        Log.d(TAG, "Address "+laboraddressholder);
        Log.d(TAG, "Interest "+laborinterestedon);
        Log.d(TAG, "gender "+laborgenderholder);
        Log.d(TAG, "wages "+laborwageholder);
        Log.d(TAG, "transport "+labortransportholder);
        Log.d(TAG, "category "+laborspinnerholder);


        if(labor_uname.getText().toString().length()==0){
            labor_uname.setError(" Enter Valid Name");
            labor_uname.requestFocus();

        }


        if(labor_age.getText().toString().length()==0){
            labor_age.setError(" Enter Age");
            labor_age.requestFocus();

        }

        if(labor_address.getText().toString().length()==0){
            labor_address.setError(" Enter address");
            labor_address.requestFocus();

        }

       loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTLABORDATA,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Inserting Response: " + response.toString());
                        loader.dismiss();
                        //hideDialog();
                        Log.i("tagconvertstr", "["+response+"]");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            AlertDialog alertDialog = new AlertDialog.Builder(LabourDashboard.this).create();
                            alertDialog.setTitle("Success ");
                            alertDialog.setMessage(" Data Stored Successfull");
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
                            Toast.makeText(LabourDashboard.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("labor_name", labornameholder);
                params.put("labor_mobnum", labormobnumholder);
                params.put("labor_gender", laborgenderholder);
                params.put("labor_age", laborageholder);
                params.put("labor_address", laboraddressholder);
                params.put("labor_category", laborspinnerholder);
                params.put("labor_wagerate", laborwageholder);
                params.put("transport_mode", labortransportholder);
                params.put("interest_work", laborinterestedon);
                params.put("labor_workinghour",laborworkinghourholder);
                params.put("particular_area", laborinterestedareaholder);
                params.put("particular_area1", laborinterestedareaholder1);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LabourDashboard.this);
        requestQueue.add(stringRequest);

    }

// Creating method to get value from EditText.
public void GetValueFromEditText(){
    labornameholder = labor_uname.getText().toString().trim();
    labormobnumholder = labor_mobnum.getText().toString().trim();
    laborageholder = labor_age.getText().toString().trim();
    laborinterestedareaholder = labor_interestedarea.getText().toString().trim();
    laborinterestedareaholder1 = labor_interestarea1.getText().toString().trim();
    laboraddressholder =labor_address.getText().toString().trim();
    laborinterestedon = ((RadioButton) findViewById(rg_interestedon.getCheckedRadioButtonId())).getText().toString();
    laborgenderholder = ((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString();

}


    private void getAllData(){
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);


        loader.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GETALLDATAOFLABOR,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Inserting Response: " + response.toString());
                loader.dismiss();
                //hideDialog();
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d(TAG, array.toString());
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject job = array.getJSONObject(i);
                        labor_age.setText(job.getString("labor_age").toString());
                        labor_address.setText(job.getString("labor_address").toString());
                        categoryspinner.setText(job.getString("labor_category").toString());
                        wageratespinner.setText(job.getString("labor_wagerate").toString());
                        modeoftransport.setText(job.getString("transport_mode").toString());
                        workinghourspinner.setText(job.getString("labor_workinghour").toString());
                        labor_interestedarea.setText(job.getString("particular_area").toString());
                        labor_interestarea1.setText(job.getString("particular_area1").toString());

                    }


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
                            Toast.makeText(LabourDashboard.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("labor_mobnum", email);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LabourDashboard.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

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
                this.setContentView(R.layout.activity_labour_dashboard);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_labour_dashboard);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_labour_dashboard);
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
