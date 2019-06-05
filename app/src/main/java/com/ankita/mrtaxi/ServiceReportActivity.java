package com.ankita.mrtaxi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ServiceReportActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtSPStartDate,txtSPEndDate;
    Spinner spSPVehicleName;
    Button btnSPReport;
    String VehicleId;
    ArrayList<String> VehicleIdList = new ArrayList<>();
    ArrayList<String> VehicleNameNoList = new ArrayList<>();

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    ArrayList<String> serviceReportocost = new ArrayList<>();
    ArrayList<String> serviceReportomcost = new ArrayList<>();
    ArrayList<String> serviceReportodate = new ArrayList<>();
    ArrayList<String> serviceReportTotal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtSPStartDate = (TextView)findViewById(R.id.txtSPStartDate);
        txtSPEndDate = (TextView)findViewById(R.id.txtSPEndDate);
        spSPVehicleName = (Spinner)findViewById(R.id.spSPVehicleName);
        btnSPReport = (Button) findViewById(R.id.btnSPReport);

        txtSPStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ServiceReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtSPStartDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtSPStartDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtSPStartDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtSPStartDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        txtSPEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ServiceReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtSPEndDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtSPEndDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtSPEndDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtSPEndDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }

                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        GetSRVehicleList vehicleList = new GetSRVehicleList();
        vehicleList.execute();

        spSPVehicleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spSPVehicleName.getSelectedItemPosition();
                VehicleId = VehicleIdList.get(position_no);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSPReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtSPStartDate.getText().toString().equals("") && txtSPEndDate.getText().toString().equals(""))
                {
                    Toast.makeText(ServiceReportActivity.this,"Enter Start Date",Toast.LENGTH_SHORT).show();
                }
                else if(txtSPEndDate.getText().toString().equals(""))
                {
                    Toast.makeText(ServiceReportActivity.this,"Enter End Date",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetServiceReport serviceReport = new GetServiceReport();
                    serviceReport.execute();
                }


            }
        });
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
            Intent i = new Intent(ServiceReportActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(ServiceReportActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange_maintenance)
        {
            Intent i = new Intent(ServiceReportActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_driver)
        {
            Intent i = new Intent(ServiceReportActivity.this, DriverActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(ServiceReportActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(ServiceReportActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(ServiceReportActivity.this, EveryDayReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_report)
        {
            Intent i = new Intent(ServiceReportActivity.this, ReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.ankita.mrtaxi";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.mrtaxi"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.mrtaxi"));
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

    private class GetSRVehicleList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"vehicle.php",joUser.toString());
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

                        String v_id =jo.getString("id");
                        String v_name =jo.getString("v_name");
                        String v_no =jo.getString("v_no");

                        VehicleIdList.add(v_id);
                        VehicleNameNoList.add(v_name+" - "+v_no);
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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, VehicleNameNoList);
                spSPVehicleName.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(ServiceReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetServiceReport extends AsyncTask<String,Void,String> {

        String status,message,vehiclename,Oil_Total,Maintenance_Total,Main_Total;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(ServiceReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("o_v_id",VehicleId);
                joUser.put("startdate",txtSPStartDate.getText().toString());
                joUser.put("enddate",txtSPEndDate.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"service_report.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    Oil_Total=j.getString("Oil_Total");
                    Maintenance_Total=j.getString("Maintenance_Total");
                    Main_Total=j.getString("Main_Total");

                    JSONArray JsArry=j.getJSONArray("vehicle");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String v_name =jo.getString("v_name");
                        String v_no =jo.getString("v_no");
                        String o_cost =jo.getString("o_cost");
                        String o_m_cost =jo.getString("o_m_cost");
                        String o_date =jo.getString("o_date");
                        String Total =jo.getString("Total");

                        vehiclename = v_name+" - "+v_no;

                        serviceReportocost.add(o_cost);
                        serviceReportomcost.add(o_m_cost);
                        serviceReportodate.add(o_date);
                        serviceReportTotal.add(Total);

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
            dialog.dismiss();
            if(status.equals("1"))
            {
                Intent i = new Intent(ServiceReportActivity.this,GenerateServiceReportActivity.class);
                i.putExtra("Oil_Total",Oil_Total);
                i.putExtra("Maintenance_Total",Maintenance_Total);
                i.putExtra("Main_Total",Main_Total);
                i.putExtra("vehiclename",vehiclename);
                i.putExtra("serviceReportocost",serviceReportocost);
                i.putExtra("serviceReportomcost",serviceReportomcost);
                i.putExtra("serviceReportodate",serviceReportodate);
                i.putExtra("serviceReportTotal",serviceReportTotal);
                startActivity(i);
            }
            else
            {
                Toast.makeText(ServiceReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
