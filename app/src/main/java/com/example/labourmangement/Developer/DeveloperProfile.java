package com.example.labourmangement.Developer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.labourmangement.Architect.ArchitechProfile;
import com.example.labourmangement.DatabaseHelper.SessionForArch;
import com.example.labourmangement.DatabaseHelper.SessionForDeveloper;
import com.example.labourmangement.DatabaseHelper.SessionForOwner;
import com.example.labourmangement.Owner.OwnerProfile;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;

public class DeveloperProfile extends AppCompatActivity {
    private static final String TAG = OwnerProfile.class.getSimpleName();

    EditText editText_uname,editText_mobnum,editText_address,editText_areaofoperation,editText_workinghour;
    Button btnsubmitcontractor,logout;
    RadioGroup rg_interestedon;
    String etunameholder,etmobnumholder,etaddressholder,etareaofoperationholder,etworkinghourholder,etradiogroholder;
    int flag;
    MaterialBetterSpinner category_workinghours;
    ProgressDialog progressDialog;
SessionForDeveloper sessionForDeveloper;
    String[] SPINNERWORKINGHOUR = {"9AM-5PM","10AM-6PM","9:30AM-5:30PM","10:30AM-6:30PM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profile);

        getSupportActionBar().setTitle("Developer Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));



        editText_uname = (EditText) findViewById(R.id.etdev_username);
        editText_mobnum = (EditText) findViewById(R.id.etdev_mobilenum);
        editText_address=(EditText)findViewById(R.id.etdev_postaladdress);
        editText_areaofoperation=(EditText)findViewById(R.id.etdev_areaofoperation);
        category_workinghours=(MaterialBetterSpinner)findViewById(R.id.category_workinghoursofdev);
        rg_interestedon=(RadioGroup)findViewById(R.id.rg_interestedonworkingDEV);
        btnsubmitcontractor=(Button)findViewById(R.id.button_submitdeveloper);


        ArrayAdapter<String> adapterworkinghours = new ArrayAdapter<String>(DeveloperProfile.this, android.R.layout.simple_dropdown_item_1line, SPINNERWORKINGHOUR);

        category_workinghours.setAdapter(adapterworkinghours);


        category_workinghours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etworkinghourholder = (String) adapterView.getItemAtPosition(i);
            }
        });

        //logout=(Button)findViewById(R.id.btn_logoutcontractor);
        sessionForDeveloper = new SessionForDeveloper(getApplicationContext());
        sessionForDeveloper.checkLogin();
        HashMap<String, String> user = sessionForDeveloper.getUserDetails();

        // name
        String name = user.get(SessionForDeveloper.KEY_NAME);

        // email
        String email = user.get(SessionForDeveloper.KEY_EMAIL);

        editText_uname.setText(name);
        editText_mobnum.setText(email);
        Log.d(TAG, "name contractor555 "+name);
        Log.d(TAG, "emaillllll contractor555 "+email);

        progressDialog = new ProgressDialog(DeveloperProfile.this);
        btnsubmitcontractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=0;
                if(editText_uname.getText().toString().equals("")){
                    editText_uname.setError("Enter Username");
                    editText_uname.requestFocus();
                    flag=1;
                }

                if(editText_address.getText().toString().length()==0){
                    editText_address.setError(" Enter Address");
                    editText_address.requestFocus();
                    flag=1;
                }

                if(editText_areaofoperation.getText().toString().length()==0){
                    editText_areaofoperation.setError(" Enter Area Of Operation");
                    editText_areaofoperation.requestFocus();
                    flag=1;
                }
                if(flag==0){
                    // savedata();
                }

            }
        });



    }
}