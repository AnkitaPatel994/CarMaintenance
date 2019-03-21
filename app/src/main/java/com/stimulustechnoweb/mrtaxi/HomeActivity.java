package com.stimulustechnoweb.mrtaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout llVehicle,llMaintenance,llServiceReport,llDriver,llEveryDayCashOut,llEveryDayReport;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        llVehicle = (LinearLayout)findViewById(R.id.llVehicle);
        llMaintenance = (LinearLayout)findViewById(R.id.llMaintenance);
        llServiceReport = (LinearLayout)findViewById(R.id.llServiceReport);
        llDriver = (LinearLayout)findViewById(R.id.llDriver);
        llEveryDayCashOut = (LinearLayout)findViewById(R.id.llEveryDayCashOut);
        llEveryDayReport = (LinearLayout)findViewById(R.id.llEveryDayReport);

        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(i);
            }
        });

        llMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, OilChangeActivity.class);
                startActivity(i);
            }
        });

        llServiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ServiceReportActivity.class);
                startActivity(i);
            }
        });

        llDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DriverActivity.class);
                startActivity(i);
            }
        });

        llEveryDayCashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, EveryDayCashOutActivity.class);
                startActivity(i);
            }
        });

        llEveryDayReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, EveryDayReportActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout)
        {
            session.logoutUser();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
