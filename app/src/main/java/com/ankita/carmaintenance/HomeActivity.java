package com.ankita.carmaintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout llVehicle,llOilChange,llMaintenance,llServiceReport,llEveryDayCashOut,llEveryDayReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        llVehicle = (LinearLayout)findViewById(R.id.llVehicle);
        llOilChange = (LinearLayout)findViewById(R.id.llOilChange);
        llMaintenance = (LinearLayout)findViewById(R.id.llMaintenance);
        llServiceReport = (LinearLayout)findViewById(R.id.llServiceReport);
        llEveryDayCashOut = (LinearLayout)findViewById(R.id.llEveryDayCashOut);
        llEveryDayReport = (LinearLayout)findViewById(R.id.llEveryDayReport);

        llVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, VehicleActivity.class);
                startActivity(i);
            }
        });

        llOilChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, OilChangeActivity.class);
                startActivity(i);
            }
        });

        llMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MaintenanceActivity.class);
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
}
