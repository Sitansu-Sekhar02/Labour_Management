package com.example.labourmangement.Engineer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.labourmangement.Contractor.ContractorDashboard;
import com.example.labourmangement.CustomLoader;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionForEngineer;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EngProfile extends AppCompatActivity {

    private static final String TAG = EngProfile.class.getSimpleName();
    EditText eng_uname,eng_mobnum,eng_areaofoperation,edit_ref_name,edit_ref_code,eng_age,eng_address,eng_wages,eng_workinghours,eng_interestedarea;
    String engnameholder,engmobnumholder,engageholder,enggenderholder,engwageholder,enginterestedon,engaddressholder,engworkinghourholder,engspinnerholder,enginterestedareaholder,engtransportholder;
    RadioGroup rg_gender,rg_interestedon;
    RadioButton rb_daily,rbmothly,rb_weekly,rb_male,rb_female;
private SessionForEngineer sessionForEngineer;
    Button btnsubmit;
    MaterialBetterSpinner categoryspinnerENG,modeoftransportENG,wageratespinnerENG,workinghourspinnerENG;
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
        setContentView(R.layout.activity_eng_profile);

        getSupportActionBar().setTitle(" Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        eng_uname = (EditText) findViewById(R.id.eteng_username);
            eng_mobnum = (EditText) findViewById(R.id.eteng_mobilenum);
        eng_areaofoperation = (EditText) findViewById(R.id.eteng_areaofoperation);
        edit_ref_name=(EditText)findViewById(R.id.et_ref_nameE);
        edit_ref_code=(EditText)findViewById(R.id.et_ref_codeE);

        eng_address = (EditText) findViewById(R.id.eteng_postaladdress);
        wageratespinnerENG = (MaterialBetterSpinner) findViewById(R.id.spinn_eng_spinn);

        btnsubmit = (Button) findViewById(R.id.btn_submiteng);
        rg_gender = (RadioGroup) findViewById(R.id.radiogroupgender);
        rg_interestedon = (RadioGroup) findViewById(R.id.radiogroupinterestworkon);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        //getAllData();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                flag = 0;
                if (eng_uname.getText().toString().equals("")) {
                    eng_uname.setError("Enter Username");
                    eng_uname.requestFocus();
                    flag = 1;
                }

                if (eng_address.getText().toString().length() == 0) {
                    eng_address.setError(" Enter Address");
                    eng_address.requestFocus();
                    flag = 1;
                }

                if (eng_age.getText().toString().length() == 0) {
                    eng_age.setError(" Enter Age");
                    eng_age.requestFocus();
                    flag = 1;
                }


                if (eng_interestedarea.getText().toString().length() == 0) {
                    eng_interestedarea.setError(" Enter Interested Area of work");
                    eng_interestedarea.requestFocus();
                    flag = 1;
                }
                if (flag == 0) {
                    getandset();


                }
                // getandset();
            }
        });

        sessionForEngineer = new SessionForEngineer(getApplicationContext());
        sessionForEngineer.checkLogin();
        HashMap<String, String> user = sessionForEngineer.getUserDetails();

        // name
        String name = user.get(SessionForEngineer.KEY_NAME);

        // email
        String email = user.get(SessionForEngineer.KEY_EMAIL);

        eng_uname.setText(name);
        eng_mobnum.setText(email);

        categoryspinnerENG = (MaterialBetterSpinner) findViewById(R.id.spinn_eng_spinn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EngProfile.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        categoryspinnerENG.setAdapter(adapter);

        ArrayAdapter<String> adaptermodeoftransport = new ArrayAdapter<String>(EngProfile.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA_FORMODEOFTRANSPORT);

        modeoftransportENG.setAdapter(adaptermodeoftransport);


        ArrayAdapter<String> adapterwagerate = new ArrayAdapter<String>(EngProfile.this, android.R.layout.simple_dropdown_item_1line, SPINNERWAGERATE);

        wageratespinnerENG.setAdapter(adapterwagerate);

        ArrayAdapter<String> adapterwokinghour = new ArrayAdapter<String>(EngProfile.this, android.R.layout.simple_dropdown_item_1line, SPINNERWORKINGHOUR);

        workinghourspinnerENG.setAdapter(adapterwokinghour);


        categoryspinnerENG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                engspinnerholder = (String) adapterView.getItemAtPosition(i);
            }
        });

        workinghourspinnerENG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                engworkinghourholder = (String) adapterView.getItemAtPosition(i);
            }
        });
        wageratespinnerENG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                engwageholder = (String) adapterView.getItemAtPosition(i);
            }
        });

        modeoftransportENG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                engtransportholder = (String) adapterView.getItemAtPosition(i);
            }
        });
        
        
    }

    private void getandset(){
        GetValueFromEditText();


        Log.d(TAG, "name  "+engnameholder);
        Log.d(TAG, "email  "+engmobnumholder);
        Log.d(TAG, "Age "+engageholder);
        Log.d(TAG, "Area "+enginterestedareaholder);
        Log.d(TAG, "Address "+engaddressholder);
        Log.d(TAG, "Interest "+enginterestedon);
        Log.d(TAG, "gender "+enggenderholder);
        Log.d(TAG, "wages "+engageholder);
        Log.d(TAG, "transport "+engtransportholder);
        Log.d(TAG, "category "+engspinnerholder);


        if(eng_uname.getText().toString().length()==0){
            eng_uname.setError(" Enter Valid Name");
            eng_uname.requestFocus();

        }


        if(eng_age.getText().toString().length()==0){
            eng_age.setError(" Enter Age");
            eng_age.requestFocus();

        }

        if(eng_address.getText().toString().length()==0){
            eng_address.setError(" Enter address");
            eng_address.requestFocus();

        }
        // Showing progress dialog at user registration time.

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

                           /* Toast.makeText(getApplicationContext(),
                                    jsonObject.getString("message")+response,
                                    Toast.LENGTH_LONG).show();*/

                    AlertDialog alertDialog = new AlertDialog.Builder(EngProfile.this).create();
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
                            Toast.makeText(EngProfile.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("labor_name", engnameholder);
                params.put("labor_mobnum", engmobnumholder);
                params.put("labor_gender", enggenderholder);
                params.put("labor_age", engageholder);
                params.put("labor_address", engaddressholder);
                params.put("labor_category", engspinnerholder);
                params.put("labor_wagerate", engwageholder);
                params.put("transport_mode", engtransportholder);
                params.put("interest_work", enginterestedon);
                params.put("particular_area", enginterestedareaholder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(EngProfile.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    public void GetValueFromEditText(){

        engnameholder = eng_uname.getText().toString().trim();
        engmobnumholder = eng_mobnum.getText().toString().trim();
        engageholder = eng_age.getText().toString().trim();
        enginterestedareaholder = eng_interestedarea.getText().toString().trim();
        engaddressholder =eng_address.getText().toString().trim();
        enginterestedon = ((RadioButton) findViewById(rg_interestedon.getCheckedRadioButtonId())).getText().toString();
        enggenderholder = ((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString();


    }
    private void getdata(){

        sessionForEngineer=new SessionForEngineer(this);

        HashMap<String, String> user = sessionForEngineer.getUserDetails();

        // name
        String name = user.get(SessionForEngineer.KEY_NAME);

        // email
        String email = user.get(SessionForEngineer.KEY_EMAIL);
        String role =user.get(SessionForEngineer.KEY_ROLE);


        // loader.setMessage("Loading...Please Wait..");
        loader.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GETCONTRACTORDATA,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("geetttttttt", response);


                loader.dismiss();
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d(TAG, array.toString());
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject job = array.getJSONObject(i);
                        eng_uname.setText(job.getString("contractor_name").toString());
                        eng_mobnum.setText(job.getString("contractor_mobnum").toString());
                        eng_address.setText(job.getString("contractor_address").toString());
                        eng_interestedarea.setText(job.getString("contractor_areaofoperation").toString());
                        eng_workinghours.setText(job.getString("working_hours").toString());
                        rg_interestedon.getCheckedRadioButtonId();
                        //rg_interestedon.(job.getString("interested_on").toString());



                    }


                } catch (JSONException e) {

                    Log.e("testerroor",e.toString());
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
                            Toast.makeText(EngProfile.this, "Connection Time Out.. Please Check Your Internet Connection", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            loader.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Your Are Not Authrized..", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Server Error", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Please Check Your Internet Connection", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            loader.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(EngProfile.this, "Error While Data Parsing", duration).show();

                            //TODO
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {



                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("contractor_mobnum", email);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(EngProfile.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}