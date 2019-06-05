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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ReportActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtRStartDate,txtREndDate;
    Spinner spRDriverName;
    Button btnRReport;
    String driverId;
    ArrayList<String> DriverIdList = new ArrayList<>();
    ArrayList<String> DriverNameList = new ArrayList<>();

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    ArrayList<String> reportDShiftArray = new ArrayList<>();
    ArrayList<String> reportClientNameArray = new ArrayList<>();
    ArrayList<String> reportCashArray = new ArrayList<>();
    ArrayList<String> reportGascreditArray = new ArrayList<>();
    ArrayList<String> reportGascashArray = new ArrayList<>();
    ArrayList<String> reportMaintenanceArray = new ArrayList<>();
    ArrayList<String> reportCommissionArray = new ArrayList<>();
    ArrayList<String> reportGSTArray = new ArrayList<>();
    ArrayList<String> reportCashleftArray = new ArrayList<>();
    ArrayList<String> reportTotalArray = new ArrayList<>();
    ArrayList<String> reportDateArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtRStartDate = (TextView)findViewById(R.id.txtRStartDate);
        txtRStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtRStartDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtRStartDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtRStartDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtRStartDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        txtREndDate = (TextView)findViewById(R.id.txtREndDate);
        txtREndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(ReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtREndDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtREndDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtREndDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtREndDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        spRDriverName = (Spinner) findViewById(R.id.spRDriverName);
        GetDriverList driverList = new GetDriverList();
        driverList.execute();

        spRDriverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spRDriverName.getSelectedItemPosition();
                driverId = DriverIdList.get(position_no);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRReport = (Button) findViewById(R.id.btnRReport);
        btnRReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtRStartDate.getText().toString().equals("") && txtREndDate.getText().toString().equals(""))
                {
                    Toast.makeText(ReportActivity.this,"Enter Start Date",Toast.LENGTH_SHORT).show();
                }
                else if(txtREndDate.getText().toString().equals(""))
                {
                    Toast.makeText(ReportActivity.this,"Enter End Date",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetReport report = new GetReport();
                    report.execute();
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(ReportActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(ReportActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange_maintenance)
        {
            Intent i = new Intent(ReportActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(ReportActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_driver)
        {
            Intent i = new Intent(ReportActivity.this, DriverActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(ReportActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(ReportActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(ReportActivity.this, EveryDayReportActivity.class);
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

    private class GetDriverList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"drivername.php",joUser.toString());
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

                        String d_id =jo.getString("d_id");
                        String d_name =jo.getString("d_name");

                        DriverIdList.add(d_id);
                        DriverNameList.add(d_name);
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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(ReportActivity.this, android.R.layout.simple_spinner_dropdown_item, DriverNameList);
                spRDriverName.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(ReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetReport extends AsyncTask<String,Void,String>  {

        String status,message,d_name;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(ReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("c_d_id",driverId);
                joUser.put("startdate",txtRStartDate.getText().toString());
                joUser.put("enddate",txtREndDate.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"report.php",joUser.toString());
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

                        String c_dshift =jo.getString("c_dshift");
                        d_name =jo.getString("d_name");
                        String client_name =jo.getString("client_name");
                        String c_cash =jo.getString("c_cash");
                        String c_gascredit =jo.getString("c_gascredit");
                        String c_gascash =jo.getString("c_gascash");
                        String c_maintenance =jo.getString("c_maintenance");
                        String c_commission =jo.getString("c_commission");
                        String c_gst =jo.getString("c_gst");
                        String c_cashleft =jo.getString("c_cashleft");
                        String c_total =jo.getString("c_total");
                        String c_date =jo.getString("c_date");

                        reportDShiftArray.add(c_dshift);
                        reportClientNameArray.add(client_name);
                        reportCashArray.add(c_cash);
                        reportGascreditArray.add(c_gascredit);
                        reportGascashArray.add(c_gascash);
                        reportMaintenanceArray.add(c_maintenance);
                        reportCommissionArray.add(c_commission);
                        reportGSTArray.add(c_gst);
                        reportCashleftArray.add(c_cashleft);
                        reportTotalArray.add(c_total);
                        reportDateArray.add(c_date);

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
                Intent i = new Intent(ReportActivity.this,GenerateReportActivity.class);
                i.putExtra("d_name",d_name);
                i.putExtra("reportDShiftArray",reportDShiftArray);
                i.putExtra("reportClientNameArray",reportClientNameArray);
                i.putExtra("reportCashArray",reportCashArray);
                i.putExtra("reportGascreditArray",reportGascreditArray);
                i.putExtra("reportGascashArray",reportGascashArray);
                i.putExtra("reportMaintenanceArray",reportMaintenanceArray);
                i.putExtra("reportCommissionArray",reportCommissionArray);
                i.putExtra("reportGSTArray",reportGSTArray);
                i.putExtra("reportCashleftArray",reportCashleftArray);
                i.putExtra("reportTotalArray",reportTotalArray);
                i.putExtra("reportDateArray",reportDateArray);
                startActivity(i);

            }
            else
            {
                Toast.makeText(ReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
