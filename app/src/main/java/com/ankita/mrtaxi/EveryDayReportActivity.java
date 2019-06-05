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

public class EveryDayReportActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtEDDate;
    Spinner spEDDriverName;
    Button btnEDReport;
    ArrayList<String> DriverIdList = new ArrayList<>();
    ArrayList<String> DriverNameList = new ArrayList<>();
    String driverId;
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtEDDate = (TextView)findViewById(R.id.txtEDDate);
        spEDDriverName = (Spinner)findViewById(R.id.spEDDriverName);
        btnEDReport = (Button) findViewById(R.id.btnEDReport);

        txtEDDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EveryDayReportActivity.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;

                        if(selectedmonth < 10 && selectedday < 10)
                        {
                            txtEDDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + "0"+selectedday);
                        }
                        else if(selectedmonth < 10)
                        {
                            txtEDDate.setText(selectedyear + "-" + "0"+selectedmonth + "-" + selectedday);
                        }
                        else if(selectedday < 10)
                        {
                            txtEDDate.setText(selectedyear + "-" + selectedmonth + "-" + "0"+selectedday);
                        }
                        else
                        {
                            txtEDDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        GetDriverNameList driverNameList = new GetDriverNameList();
        driverNameList.execute();

        spEDDriverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spEDDriverName.getSelectedItemPosition();
                driverId = DriverIdList.get(position_no);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnEDReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtEDDate.getText().toString().equals(""))
                {
                    Toast.makeText(EveryDayReportActivity.this,"Enter Date",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    GetDailyReport dailyReport = new GetDailyReport();
                    dailyReport.execute();
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
            Intent i = new Intent(EveryDayReportActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(EveryDayReportActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange_maintenance)
        {
            Intent i = new Intent(EveryDayReportActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(EveryDayReportActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_driver)
        {
            Intent i = new Intent(EveryDayReportActivity.this, DriverActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(EveryDayReportActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydaycashout)
        {
            Intent i = new Intent(EveryDayReportActivity.this, EveryDayCashOutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_report)
        {
            Intent i = new Intent(EveryDayReportActivity.this, ReportActivity.class);
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

    private class GetDriverNameList extends AsyncTask<String,Void,String> {

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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(EveryDayReportActivity.this, android.R.layout.simple_spinner_dropdown_item, DriverNameList);
                spEDDriverName.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(EveryDayReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDailyReport extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;
        String c_id,c_dshift,d_name,client_name,c_other,c_cash,c_gascredit,c_gascash,c_maintenance,c_commission,c_gst,c_cashleft,c_total,c_date;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(EveryDayReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("c_d_id",driverId);
                joUser.put("c_date",txtEDDate.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"daily_report.php",joUser.toString());
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

                        c_id =jo.getString("c_id");
                        c_dshift =jo.getString("c_dshift");
                        d_name =jo.getString("d_name");
                        client_name =jo.getString("client_name");
                        c_other =jo.getString("c_other");
                        c_cash =jo.getString("c_cash");
                        c_gascredit =jo.getString("c_gascredit");
                        c_gascash =jo.getString("c_gascash");
                        c_maintenance =jo.getString("c_maintenance");
                        c_commission =jo.getString("c_commission");
                        c_gst =jo.getString("c_gst");
                        c_cashleft =jo.getString("c_cashleft");
                        c_total =jo.getString("c_total");
                        c_date =jo.getString("c_date");

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
                Intent i = new Intent(EveryDayReportActivity.this,GenerateDayReportActivity.class);
                i.putExtra("c_id",c_id);
                i.putExtra("c_dshift",c_dshift);
                i.putExtra("d_name",d_name);
                i.putExtra("client_name",client_name);
                i.putExtra("c_other",c_other);
                i.putExtra("c_cash",c_cash);
                i.putExtra("c_gascredit",c_gascredit);
                i.putExtra("c_gascash",c_gascash);
                i.putExtra("c_maintenance",c_maintenance);
                i.putExtra("c_commission",c_commission);
                i.putExtra("c_gst",c_gst);
                i.putExtra("c_cashleft",c_cashleft);
                i.putExtra("c_total",c_total);
                i.putExtra("c_date",c_date);
                startActivity(i);
            }
            else
            {
                Toast.makeText(EveryDayReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
