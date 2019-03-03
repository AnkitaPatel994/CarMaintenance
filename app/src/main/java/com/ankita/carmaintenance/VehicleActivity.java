package com.ankita.carmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class VehicleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvVehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VehicleActivity.this,AddVehicleActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvVehicleList = (RecyclerView)findViewById(R.id.rvVehicleList);
        rvVehicleList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(VehicleActivity.this,LinearLayoutManager.VERTICAL,false);
        rvVehicleList.setLayoutManager(manager);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(VehicleActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange)
        {
            Intent i = new Intent(VehicleActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_maintenance)
        {
            Intent i = new Intent(VehicleActivity.this, MaintenanceActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(VehicleActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(VehicleActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(VehicleActivity.this, EveryDayReportActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
