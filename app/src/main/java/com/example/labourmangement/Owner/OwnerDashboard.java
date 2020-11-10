package com.example.labourmangement.Owner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourmangement.Admin.MainActivity;
import com.example.labourmangement.Contractor.AppliedJobs;
import com.example.labourmangement.Contractor.ContractorDashboard;
import com.example.labourmangement.Contractor.ContractorProfile;
import com.example.labourmangement.Contractor.JobWages;
import com.example.labourmangement.Contractor.PostJobs;
import com.example.labourmangement.Contractor.TrackLabor;
import com.example.labourmangement.DatabaseHelper.SessionForOwner;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.Labour.LaborProfile;
import com.example.labourmangement.R;

import java.util.HashMap;

public class OwnerDashboard extends AppCompatActivity {
    ImageView btn_profile,btn_paystatus,btn_confirmation,btn_joboffer,btn_logout,btn_wages;
    SessionForOwner sessionForOwner;
    ProgressDialog progressDialog;
    TextView contractorname,contractorid;
    private static final String TAG = OwnerDashboard.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));


        btn_profile=(ImageView)findViewById(R.id.btnownerprofile);
        btn_confirmation=(ImageView)findViewById(R.id.btnownerjobstatus);
        btn_joboffer=(ImageView)findViewById(R.id.btnownerjoboffer);
        btn_logout=(ImageView)findViewById(R.id.btnlogoutowner);
      //  btn_tracking=(ImageView)findViewById(R.id.btnlivetracklabor);
        btn_wages=(ImageView)findViewById(R.id.btnownergetwages);
        contractorname=(TextView)findViewById(R.id.textfetchusernameOwner);
        contractorid=(TextView)findViewById(R.id.fetchuserIDOwner);
        btn_paystatus=(ImageView) findViewById(R.id.btnownerremark);

        sessionForOwner=new SessionForOwner((getApplicationContext()));

        HashMap<String, String> user = sessionForOwner.getUserDetails();

        // name
        String name = user.get(SessionForOwner.KEY_NAME);

        // email
        String email = user.get(SessionForOwner.KEY_EMAIL);

        Log.d(TAG,"Email"+email);
        Log.d(TAG,"Name"+name);

       contractorname.setText(name);
        contractorid.setText(email);


        progressDialog = new ProgressDialog(OwnerDashboard.this);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                Intent intent1 = new Intent(OwnerDashboard.this, OwnerProfile.class);
                startActivity(intent1);
            }
        });
        btn_joboffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OwnerDashboard.this, PostJobOwner.class);
                startActivity(intent1);
            }
        });
        btn_paystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OwnerDashboard.this, PaymentStatus.class);
                startActivity(intent1);
            }
        });

        btn_wages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OwnerDashboard.this, WagesByOwner.class);
                startActivity(intent1);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(OwnerDashboard.this, android.R.style.Theme_Translucent_NoTitleBar);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alertyesno);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);


                //findId
                TextView tvYes = (TextView) dialog.findViewById(R.id.tvOk);
                TextView tvCancel = (TextView) dialog.findViewById(R.id.tvcancel);
                TextView tvReason = (TextView) dialog.findViewById(R.id.textView22);
                TextView tvAlertMsg = (TextView) dialog.findViewById(R.id.tvAlertMsg);

                //set value
                tvAlertMsg.setText("Confirmation Alert..!!!");
                tvReason.setText("Are you sure want to logout?");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                //set listener
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        sessionForOwner.logoutUser();
                        startActivity(new Intent(OwnerDashboard.this, MainActivity.class));
                        finishAffinity();
                        dialog.dismiss();
                    }
                });


                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }

        });
        btn_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OwnerDashboard.this, AppliedJobOwner.class);
                startActivity(intent1);
            }
        });

    }
}