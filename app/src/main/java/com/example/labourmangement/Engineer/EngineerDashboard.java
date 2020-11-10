package com.example.labourmangement.Engineer;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourmangement.Admin.MainActivity;
import com.example.labourmangement.Architect.JobStatusApprovalEng;
import com.example.labourmangement.DatabaseHelper.SessionForEngineer;
import com.example.labourmangement.Labour.LaborProfile;
import com.example.labourmangement.R;

import java.util.HashMap;

public class EngineerDashboard extends AppCompatActivity {

    private static final String TAG = EngineerDashboard.class.getSimpleName();

ImageButton btnengprofile,btnengjoboffer,btnengjobstatus;
ImageView btnenggetwages,btnengremark,btnlogouteng;
ProgressDialog progressDialog;
TextView engname,engid;
SessionForEngineer sessionForEngineer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_dashboard);

        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        btnengprofile=(ImageButton) findViewById(R.id.btnengprofile);
        btnengjoboffer=(ImageButton) findViewById(R.id.btnengjoboffer);
        btnengjobstatus=(ImageButton) findViewById(R.id.btnengjobstatus);
        btnlogouteng=(ImageView)findViewById(R.id.btnlogouteng);
        btnengremark=(ImageView)findViewById(R.id.btnengremark);
        btnenggetwages=(ImageView)findViewById(R.id.btnenggetwages);
        engname=(TextView)findViewById(R.id.textfetchusernameengineer);
        engid=(TextView)findViewById(R.id.fetchuserIDengineer);

        sessionForEngineer=new SessionForEngineer((getApplicationContext()));

        HashMap<String, String> user = sessionForEngineer.getUserDetails();

        // name
        String name = user.get(SessionForEngineer.KEY_NAME);

        // email
        String email = user.get(SessionForEngineer.KEY_EMAIL);

        Log.d(TAG,"Email"+email);
        Log.d(TAG,"Name"+name);

        engname.setText(name);
        engid.setText(email);


        progressDialog = new ProgressDialog(EngineerDashboard.this);

        btnengprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EngineerDashboard.this, EngProfile.class);
                startActivity(intent1);
            }
        });

        btnengjoboffer.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                Intent intent1 = new Intent(EngineerDashboard.this, EngJobOffer.class);
                startActivity(intent1);
            }
        });
        btnengjobstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EngineerDashboard.this, JobStatusApprovalEng.class);
                startActivity(intent1);
            }
        });
        btnenggetwages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EngineerDashboard.this, GetWagesEngineer.class);
                startActivity(intent1);
            }
        });

        btnlogouteng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EngineerDashboard.this, android.R.style.Theme_Translucent_NoTitleBar);
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
                        sessionForEngineer.logoutUser();
                        startActivity(new Intent(EngineerDashboard.this, MainActivity.class));
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
        btnengremark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EngineerDashboard.this, WriteReviewEngineer.class);
                startActivity(intent1);
            }
        });


    }



}