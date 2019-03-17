package com.stimulustechnoweb.mrtaxi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DriverActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvDriverList;
    ArrayList<HashMap<String,String>> DriverListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddDriverActivity.class);
                i.putExtra("flag","add");
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

        rvDriverList = (RecyclerView)findViewById(R.id.rvDriverList);
        rvDriverList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(DriverActivity.this,LinearLayoutManager.VERTICAL,false);
        rvDriverList.setLayoutManager(manager);

        GetDriverList driverList = new GetDriverList();
        driverList.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(DriverActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(DriverActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange_maintenance)
        {
            Intent i = new Intent(DriverActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(DriverActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(DriverActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(DriverActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(DriverActivity.this, EveryDayReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_report)
        {
            Intent i = new Intent(DriverActivity.this, ReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.stimulustechnoweb.mrtaxi";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.stimulustechnoweb.mrtaxi"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.stimulustechnoweb.mrtaxi"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    private class GetDriverList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"driver.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("vehicle");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String d_id =jo.getString("d_id");
                        String d_name =jo.getString("d_name");
                        String d_licenceno =jo.getString("d_licenceno");
                        String d_gst =jo.getString("d_gst");
                        String d_commission =jo.getString("d_commission");

                        hashMap.put("d_id",d_id);
                        hashMap.put("d_name",d_name);
                        hashMap.put("d_licenceno",d_licenceno);
                        hashMap.put("d_gst",d_gst);
                        hashMap.put("d_commission",d_commission);

                        DriverListArray.add(hashMap);
                    }
                }
                else
                {
                    message=j.getString("message");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(status.equals("1"))
            {
                DriverListAdapter driverListAdapter = new DriverListAdapter(DriverActivity.this,DriverListArray);
                rvDriverList.setAdapter(driverListAdapter);
            }
            else
            {
                Toast.makeText(DriverActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
