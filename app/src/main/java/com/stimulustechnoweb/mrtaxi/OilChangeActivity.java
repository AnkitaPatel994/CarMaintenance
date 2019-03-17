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

public class OilChangeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rvOilChangeList;
    ArrayList<HashMap<String,String>> OilChangeListArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OilChangeActivity.this,AddOilChangeActivity.class);
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

        rvOilChangeList = (RecyclerView)findViewById(R.id.rvOilChangeList);
        rvOilChangeList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(OilChangeActivity.this,LinearLayoutManager.VERTICAL,false);
        rvOilChangeList.setLayoutManager(manager);

        GetOilChangeList oilChangeList = new GetOilChangeList();
        oilChangeList.execute();
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
            Intent i = new Intent(OilChangeActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(OilChangeActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(OilChangeActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_driver)
        {
            Intent i = new Intent(OilChangeActivity.this, DriverActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(OilChangeActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(OilChangeActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(OilChangeActivity.this, EveryDayReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_report)
        {
            Intent i = new Intent(OilChangeActivity.this, ReportActivity.class);
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

    private class GetOilChangeList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"oilchange.php",joUser.toString());
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

                        String o_id =jo.getString("o_id");
                        String v_id =jo.getString("v_id");
                        String v_name =jo.getString("v_name");
                        String v_no =jo.getString("v_no");
                        String v_kilometer =jo.getString("v_kilometer");
                        String o_cost =jo.getString("o_cost");
                        String o_maintenance =jo.getString("o_maintenance");
                        String o_m_cost =jo.getString("o_m_cost");
                        String o_date =jo.getString("o_date");

                        hashMap.put("o_id",o_id);
                        hashMap.put("v_id",v_id);
                        hashMap.put("v_name",v_name);
                        hashMap.put("v_no",v_no);
                        hashMap.put("v_kilometer",v_kilometer);
                        hashMap.put("o_cost",o_cost);
                        hashMap.put("o_maintenance",o_maintenance);
                        hashMap.put("o_m_cost",o_m_cost);
                        hashMap.put("o_date",o_date);

                        OilChangeListArray.add(hashMap);
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
                OilChangeListAdapter oilChangeListAdapter = new OilChangeListAdapter(OilChangeActivity.this,OilChangeListArray);
                rvOilChangeList.setAdapter(oilChangeListAdapter);
            }
            else
            {
                Toast.makeText(OilChangeActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
