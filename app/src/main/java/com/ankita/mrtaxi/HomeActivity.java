package com.ankita.mrtaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout llVehicle,llOilChangeMaintenance,llServiceReport,llDriver,llEveryDayCashOut,llEveryDayReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        llVehicle = (LinearLayout)findViewById(R.id.llVehicle);
        llOilChangeMaintenance = (LinearLayout)findViewById(R.id.llOilChangeMaintenance);
        llServiceReport = (LinearLayout)findViewById(R.id.llServiceReport);
        llDriver = (LinearLayout)findViewById(R.id.llDriver);
        llEveryDayCashOut = (LinearLayout)findViewById(R.id.llEveryDayCashOut);
        llEveryDayReport = (LinearLayout)findViewById(R.id.llEveryDayReport);

        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, VehicleActivity.class);
                startActivity(i);
            }
        });

        llOilChangeMaintenance.setOnClickListener(new View.OnClickListener() {
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
}
