package com.example.labourmangement.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.labourmangement.Architect.CheckPaymentStatus;
import com.example.labourmangement.Architect.Register_Architect;
import com.example.labourmangement.Contractor.ContractorProfile;
import com.example.labourmangement.Contractor.Register_contractor;
import com.example.labourmangement.Developer.Register_developer;
import com.example.labourmangement.Engineer.Register_Engineer;
import com.example.labourmangement.Labour.Register_labour;
import com.example.labourmangement.Owner.Register_Owner;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Locale;

public class UpdateMain extends AppCompatActivity {
    ConstraintLayout animContractor;
    ConstraintLayout animOwner;
    ConstraintLayout animArchitect;
    ImageView shareApp;

    private static final String TAG = MainActivity.class.getSimpleName();
    RelativeLayout relContractor;
    RelativeLayout relLabourer;
    RelativeLayout relArchitect;
    RelativeLayout relowner;
    RelativeLayout reldeveloper;
    RelativeLayout relengineer;
    LinearLayout openLink;
    //Spinner langauge;
    ArrayAdapter<String> spinnerArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.update_main_activity);

        getSupportActionBar().hide();

        animContractor=findViewById(R.id.contract);
        animOwner=findViewById(R.id.owner);
        animArchitect=findViewById(R.id.archi);
        openLink=findViewById(R.id.open_site);
        //langauge=findViewById(R.id.spin_language);

        relContractor=findViewById(R.id.relContractor);
        relLabourer=findViewById(R.id.relLabourer);
        relArchitect=findViewById(R.id.relArchitect);
        relowner=findViewById(R.id.relowner);
        reldeveloper=findViewById(R.id.reldeveloper);
        relengineer=findViewById(R.id.relengineer);

        MaterialBetterSpinner spinner = findViewById(R.id.spin_language);
        shareApp=findViewById(R.id.shareApp);

       /* SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();
*/
       /* final String lang = settings.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources()
                    .getDisplayMetrics());
        }*/

        // Array of choices
        String language[] = {"English","Hindi","Marathi"};


        // Application of the Array to the Spinner
        spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_dropdown_item_1line, language);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        /*SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();
*/
       /* final String lang = settings.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources()
                    .getDisplayMetrics());
        }*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2 ,long arg3 ) {
                String item_position = String.valueOf(arg0);

                int positonInt = Integer.valueOf(item_position);

                Toast.makeText(UpdateMain.this, "value is "+ positonInt, Toast.LENGTH_SHORT).show();

              /*  switch (arg2) {
                    case 0:
                        Locale locale = new Locale("en"); // where 'hi' is Language code, set this as per your Spinner Item selected
                        Locale.setDefault(locale);
                        Configuration config = getBaseContext().getResources().getConfiguration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        break;
                    case 1:
                        Locale loc = new Locale("hi"); // where 'hi' is Language code, set this as per your Spinner Item selected
                        Locale.setDefault(loc);
                        Configuration config1 = getBaseContext().getResources().getConfiguration();
                        config1.locale = loc;
                        getBaseContext().getResources().updateConfiguration(config1,
                                getBaseContext().getResources().getDisplayMetrics());
                        finish();
                        break;
                    case 2:
                        Locale local = new Locale("mar"); // where 'hi' is Language code, set this as per your Spinner Item selected
                        Locale.setDefault(local);
                        Configuration config2 = getBaseContext().getResources().getConfiguration();
                        config2.locale = local;
                        getBaseContext().getResources().updateConfiguration(config2,
                                getBaseContext().getResources().getDisplayMetrics());
                        finish();
                        break;
                }*/
                //getResources().updateConfiguration(config, null);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://drive.google.com/file/d/1qnIAtbiBw4St_HKagdUE5-2-VFlfLlOc/view?usp=sharing";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        animContractor.startAnimation(inFromLeftAnimation());
        animOwner.startAnimation(inFromLeftAnimation());
        animArchitect.startAnimation(inFromRightAnimation());
      /*  overridePendingTransition(0,0);
        View relativeLayout=findViewById(R.id.login_container);
        Animation animation= AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);*/

        relContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateMain.this,
                        Register_contractor.class);
                startActivity(i);

            }
        });
        openLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        OpenWebsite.class);
                startActivity(i);
            }
        });

        relLabourer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_labour.class);
                 startActivity(i);
            }
        });
        relLabourer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_labour.class);
                 startActivity(i);

            }
        });


        relowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_Owner.class);
                startActivity(i);

            }
        });

        relArchitect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_Architect.class);
                startActivity(i);

            }
        });

        relengineer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_Engineer.class);
                startActivity(i);

            }
        });

        reldeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_developer.class);
                startActivity(i);
            }
        });


    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(700);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(700);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eng:
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
