package com.ankita.mrtaxi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    static String BASE_URL = "http://demo.stimulustechnoweb.com/mrtaxiapi/";
    LinearLayout lnSnackbar;
    SessionManager session;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);

        session = new SessionManager(getApplicationContext());
        flag = session.checkLogin();

        lnSnackbar.setVisibility(View.GONE);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            Thread background = new Thread()
            {
                public void run()
                {
                    try {
                        sleep(2*1000);

                        if (flag == 1)
                        {
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                            finish();
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        }
        else
        {
            lnSnackbar.setVisibility(View.VISIBLE);
            lnSnackbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}
