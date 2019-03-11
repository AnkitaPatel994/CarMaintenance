package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EveryDayCashOutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtShift,txtCash,txtMedical,txtKidsFirst,txtSocialServices,txtPulpMill,txtOsbMill,txtNamsaskMill,txtdetox,txtSGI,txtOther,txtGasCreadit,txtGasCash,txtMaintenance,txtCommission,txtGst,txtCashLeft,txtTotal;
    Spinner spDriver;
    Button btnEDSubmit;
    String DriverId;
    ArrayList<String> DriverIdList = new ArrayList<>();
    ArrayList<String> DriverNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_cash_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtShift=(EditText) findViewById(R.id.txtShift);
        spDriver=(Spinner) findViewById(R.id.spDriver);
        txtCash=(EditText) findViewById(R.id.txtCash);
        txtMedical=(EditText) findViewById(R.id.txtMedical);
        txtKidsFirst=(EditText) findViewById(R.id.txtKidsFirst);
        txtSocialServices=(EditText) findViewById(R.id.txtSocialServices);
        txtPulpMill=(EditText) findViewById(R.id.txtPulpMill);
        txtOsbMill=(EditText) findViewById(R.id.txtOsbMill);
        txtNamsaskMill=(EditText) findViewById(R.id.txtNamsaskMill);
        txtdetox=(EditText) findViewById(R.id.txtdetox);
        txtSGI=(EditText) findViewById(R.id.txtSGI);
        txtOther=(EditText) findViewById(R.id.txtOther);
        txtGasCreadit=(EditText) findViewById(R.id.txtGasCreadit);
        txtGasCash=(EditText) findViewById(R.id.txtGasCash);
        txtMaintenance=(EditText) findViewById(R.id.txtMaintenance);
        txtCommission=(EditText) findViewById(R.id.txtCommission);
        txtGst=(EditText) findViewById(R.id.txtGst);
        txtCashLeft=(EditText) findViewById(R.id.txtCashLeft);
        txtTotal=(EditText) findViewById(R.id.txtTotal);
        btnEDSubmit=(Button) findViewById(R.id.btnEDSubmit);

        GetDriverName driverName = new GetDriverName();
        driverName.execute();

        spDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spDriver.getSelectedItemPosition();
                DriverId = DriverIdList.get(position_no);

                String DriverName = String.valueOf(spDriver.getSelectedItem());

                GetDriverList driverList = new GetDriverList(DriverName);
                driverList.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*String driverCash = txtCash.getText().toString();
        String gasCreadit = txtGasCreadit.getText().toString();
        String gasCash = txtGasCash.getText().toString();
        String maintenancecost = txtMaintenance.getText().toString();
        String commission = txtCommission.getText().toString();*/


        /*int total = Integer.parseInt(driverCash)+Integer.parseInt(gasCreadit)+Integer.parseInt(gasCash)+Integer.parseInt(maintenancecost);

        txtCashLeft.setText(String.valueOf(total));*/

        txtCommission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

                if(txtCommission.getText().toString().trim().length() > 0)
                {
                    int driverCash = Integer.parseInt(txtCash.getText().toString().trim());
                    int gasCreadit = Integer.parseInt(txtGasCreadit.getText().toString().trim());
                    int gasCash = Integer.parseInt(txtGasCash.getText().toString().trim());
                    int maintenancecost = Integer.parseInt(txtMaintenance.getText().toString().trim());
                    int commission = Integer.parseInt(txtCommission.getText().toString().trim());

                    int total = driverCash + gasCreadit + gasCash + maintenancecost;
                    int cashleft = total - ((total*commission)/100);
                    int gst = (cashleft*18)/100;

                    txtGst.setText(String.valueOf(gst));
                    txtCashLeft.setText(String.valueOf(cashleft));

                    int maintotal = gst+cashleft;
                    txtTotal.setText(String.valueOf(maintotal));
                }
                else
                {
                    txtGst.setText("");
                    txtCashLeft.setText("");
                    txtTotal.setText("");
                }


            }
        });


        btnEDSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDayCashOut dayCashOut = new GetDayCashOut();
                dayCashOut.execute();
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
            Intent i = new Intent(EveryDayCashOutActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_vehicle)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, VehicleActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_oilchange_maintenance)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, OilChangeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_servicereport)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, ServiceReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_driver)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, DriverActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, EveryDayReportActivity.class);
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

    private class GetDriverName extends AsyncTask<String,Void,String> {

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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(EveryDayCashOutActivity.this, android.R.layout.simple_spinner_dropdown_item, DriverNameList);
                spDriver.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(EveryDayCashOutActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDriverList extends AsyncTask<String,Void,String> {

        String status,message,driverName,d_cash,d_medical,d_kidsfirst,d_socialservices,d_sgi,d_pulpmill,d_detox,d_osbmill,d_namsaskmill;

        public GetDriverList(String driverName) {
            this.driverName = driverName;
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("d_name",driverName);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"driverlist.php",joUser.toString());
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
                        d_cash =jo.getString("d_cash");
                        d_medical =jo.getString("d_medical");
                        d_kidsfirst =jo.getString("d_kidsfirst");
                        d_socialservices =jo.getString("d_socialservices");
                        d_pulpmill =jo.getString("d_pulpmill");
                        d_osbmill =jo.getString("d_osbmill");
                        d_namsaskmill =jo.getString("d_namsaskmill");
                        d_detox =jo.getString("d_detox");
                        d_sgi =jo.getString("d_sgi");

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
                txtCash.setText(d_cash);
                txtMedical.setText(d_medical);
                txtKidsFirst.setText(d_kidsfirst);
                txtSocialServices.setText(d_socialservices);
                txtPulpMill.setText(d_pulpmill);
                txtOsbMill.setText(d_osbmill);
                txtNamsaskMill.setText(d_namsaskmill);
                txtdetox.setText(d_detox);
                txtSGI.setText(d_sgi);
            }
            else
            {
                Toast.makeText(EveryDayCashOutActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDayCashOut extends AsyncTask<String,Void,String>{

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(EveryDayCashOutActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("c_dshift",txtShift.getText().toString());
                joUser.put("c_d_id",DriverId);
                joUser.put("c_gascredit",txtGasCreadit.getText().toString());
                joUser.put("c_gascash",txtGasCash.getText().toString());
                joUser.put("c_maintenance",txtMaintenance.getText().toString());
                joUser.put("c_commission",txtCommission.getText().toString());
                joUser.put("c_total",txtTotal.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"addcashout..php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
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
                Intent i = new Intent(EveryDayCashOutActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(EveryDayCashOutActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
