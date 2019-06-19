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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class EveryDayCashOutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText txtCash,txtGasCredit,txtGasCash,txtMaintenance,txtCommission,txtGst,txtCashLeft,txtTotal;
    Spinner spDriver,spDriverShift;
    float ride;
    Button btnEDSubmit;
    String DriverShift;
    String DriverId,ClientName,ClientCost;
    ArrayList<String> DriverIdList = new ArrayList<>();
    ArrayList<String> DriverNameList = new ArrayList<>();
    ArrayList<String> ClientNameArrayList = new ArrayList<>();
    ArrayList<String> ClientCostArrayList = new ArrayList<>();
    TextView tvCommission,tvGst;
    TextView txtKidsFirst,txtSocialService,txtDetox,txtMadical,txtOSB,txtPulpMill,txtProsecution;
    EditText txtKidsFirstCost,txtSocialServiceCost,txtDetoxCost,txtMadicalCost,txtOSBCost,txtPulpMillCost,txtProsecutionCost,txtOther,txtOtherCost;

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

        tvCommission = (TextView)findViewById(R.id.tvCommission);
        tvGst = (TextView)findViewById(R.id.tvGst);

        //txtShift=(EditText) findViewById(R.id.txtShift);
        spDriver=(Spinner) findViewById(R.id.spDriver);
        spDriverShift=(Spinner) findViewById(R.id.spDriverShift);

        txtCash=(EditText) findViewById(R.id.txtCash);

        txtOther=(EditText) findViewById(R.id.txtOther);

        txtGasCredit=(EditText) findViewById(R.id.txtGasCredit);
        txtGasCash=(EditText) findViewById(R.id.txtGasCash);
        txtMaintenance=(EditText) findViewById(R.id.txtMaintenance);
        txtCommission=(EditText) findViewById(R.id.txtCommission);
        txtGst=(EditText) findViewById(R.id.txtGst);
        txtCashLeft=(EditText) findViewById(R.id.txtCashLeft);
        txtTotal=(EditText) findViewById(R.id.txtTotal);
        btnEDSubmit=(Button) findViewById(R.id.btnEDSubmit);

        txtKidsFirst = (TextView)findViewById(R.id.txtKidsFirst);
        txtSocialService = (TextView)findViewById(R.id.txtSocialService);
        txtDetox = (TextView)findViewById(R.id.txtDetox);
        txtMadical = (TextView)findViewById(R.id.txtMadical);
        txtOSB = (TextView)findViewById(R.id.txtOSB);
        txtPulpMill = (TextView)findViewById(R.id.txtPulpMill);
        txtProsecution = (TextView)findViewById(R.id.txtProsecution);

        txtKidsFirstCost = (EditText) findViewById(R.id.txtKidsFirstCost);
        txtSocialServiceCost = (EditText) findViewById(R.id.txtSocialServiceCost);
        txtDetoxCost = (EditText) findViewById(R.id.txtDetoxCost);
        txtMadicalCost = (EditText) findViewById(R.id.txtMadicalCost);
        txtOSBCost = (EditText) findViewById(R.id.txtOSBCost);
        txtPulpMillCost = (EditText) findViewById(R.id.txtPulpMillCost);
        txtProsecutionCost = (EditText) findViewById(R.id.txtProsecutionCost);
        txtOtherCost = (EditText) findViewById(R.id.txtOtherCost);

        GetDriverName driverName = new GetDriverName();
        driverName.execute();

        spDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spDriver.getSelectedItemPosition();
                DriverId = DriverIdList.get(position_no);

                txtGasCash.setText("");

                String DriverName = String.valueOf(spDriver.getSelectedItem());

                GetDriverList driverList = new GetDriverList(DriverName);
                driverList.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*GetClientName clientName = new GetClientName();
        clientName.execute();*/

        /*spClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spClient.getSelectedItemPosition();
                ClientId = ClientIdList.get(position_no);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        txtGasCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

                if(txtGasCash.getText().toString().trim().length() > 0)
                {
                    int commission = Integer.parseInt(tvCommission.getText().toString());
                    int GST = Integer.parseInt(tvGst.getText().toString().trim());

                    float KidsFirstCost = Float.parseFloat(txtKidsFirstCost.getText().toString().trim());
                    float SocialServiceCost = Float.parseFloat(txtSocialServiceCost.getText().toString().trim());
                    float DetoxCost = Float.parseFloat(txtDetoxCost.getText().toString().trim());
                    float MadicalCost = Float.parseFloat(txtMadicalCost.getText().toString().trim());
                    float OSBCost = Float.parseFloat(txtOSBCost.getText().toString().trim());
                    float PulpMillCost = Float.parseFloat(txtPulpMillCost.getText().toString().trim());
                    float ProsecutionCost = Float.parseFloat(txtProsecutionCost.getText().toString().trim());
                    float OtherCost;
                    if(!txtOther.getText().toString().trim().equals(""))
                    {
                        OtherCost = Float.parseFloat(txtOtherCost.getText().toString().trim());
                    }
                    else
                    {
                        OtherCost = 0;
                    }

                    ride = KidsFirstCost + SocialServiceCost + DetoxCost + MadicalCost + OSBCost + PulpMillCost + ProsecutionCost + OtherCost;

                    float driverCash = Float.parseFloat(txtCash.getText().toString().trim());

                    float gasCash = Float.parseFloat(txtGasCash.getText().toString().trim());

                    float maintenancecost = Float.parseFloat(txtMaintenance.getText().toString().trim());

                    float tot = ride + driverCash;
                    float gst = (tot*GST)/100;
                    float total = ride + driverCash+gst;
                    float co = (total*commission)/100;
                    float comm = co - ((co*GST)/100);

                    float cashleft = driverCash - comm - gasCash;

                    Log.d("commission", ""+gst+"/"+comm+"/"+cashleft+"/"+total);

                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.UP);

                    txtCommission.setText(String.valueOf(df.format(comm)));
                    txtGst.setText(String.valueOf(df.format(gst)));
                    txtCashLeft.setText(String.valueOf(df.format(cashleft)));
                    txtTotal.setText(String.valueOf(df.format(total)));
                }
                else
                {
                    txtCommission.setText("");
                    txtGst.setText("");
                    txtCashLeft.setText("");
                    txtTotal.setText("");
                }


            }
        });


        btnEDSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!txtKidsFirstCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtKidsFirst.getText().toString());
                    ClientCostArrayList.add(txtKidsFirstCost.getText().toString());
                }

                if (!txtSocialServiceCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtSocialService.getText().toString());
                    ClientCostArrayList.add(txtSocialServiceCost.getText().toString());
                }

                if (!txtDetoxCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtDetox.getText().toString());
                    ClientCostArrayList.add(txtDetoxCost.getText().toString());
                }

                if (!txtMadicalCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtMadical.getText().toString());
                    ClientCostArrayList.add(txtMadicalCost.getText().toString());
                }

                if (!txtOSBCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtOSB.getText().toString());
                    ClientCostArrayList.add(txtOSBCost.getText().toString());
                }

                if (!txtPulpMillCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtPulpMill.getText().toString());
                    ClientCostArrayList.add(txtPulpMillCost.getText().toString());
                }

                if (!txtProsecutionCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add(txtProsecution.getText().toString());
                    ClientCostArrayList.add(txtProsecutionCost.getText().toString());
                }

                if (!txtOtherCost.getText().toString().equals("0"))
                {
                    ClientNameArrayList.add("Other_"+txtOther.getText().toString());
                    ClientCostArrayList.add(txtOtherCost.getText().toString());
                }

                ClientName = "";
                ClientCost = "";

                for (String ss : ClientNameArrayList)
                {
                    if(ClientName == ""){
                        ClientName += ss;
                    }else{
                        ClientName += "," + ss;
                    }
                }

                for (String ss : ClientCostArrayList)
                {
                    if(ClientCost == ""){
                        ClientCost += ss;
                    }else{
                        ClientCost += "," + ss;
                    }
                }

                DriverShift = spDriverShift.getSelectedItem().toString();

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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cashout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_client)
        {
            Intent i = new Intent(getApplicationContext(),ClientActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }*/

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
        else if (id == R.id.nav_client)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, ClientActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_everydayreport)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, EveryDayReportActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_report)
        {
            Intent i = new Intent(EveryDayCashOutActivity.this, ReportActivity.class);
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

        String status,message,driverName,d_gst,d_commission;

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
                        String d_licenceno =jo.getString("d_licenceno");
                        d_gst =jo.getString("d_gst");
                        d_commission =jo.getString("d_commission");

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
                tvCommission.setText(d_commission);
                tvGst.setText(d_gst);
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
                joUser.put("c_dshift",DriverShift);
                joUser.put("c_d_id",DriverId);
                joUser.put("c_c_name",ClientName);
                joUser.put("c_cost",ClientCost);
                joUser.put("c_cash",txtCash.getText().toString());
                joUser.put("c_gascredit",txtGasCredit.getText().toString());
                joUser.put("c_gascash",txtGasCash.getText().toString());
                joUser.put("c_maintenance",txtMaintenance.getText().toString());
                joUser.put("c_commission",txtCommission.getText().toString());
                joUser.put("c_gst",txtGst.getText().toString());
                joUser.put("c_cashleft",txtCashLeft.getText().toString());
                joUser.put("c_total",txtTotal.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"addcashout.php",joUser.toString());
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

    /*private class GetClientName extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"Client.php",joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("client");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        String client_id =jo.getString("client_id");
                        String client_name =jo.getString("client_name");

                        ClientIdList.add(client_id);
                        ClientNameList.add(client_name);
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
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(EveryDayCashOutActivity.this, android.R.layout.simple_spinner_dropdown_item, ClientNameList);
                spClient.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(EveryDayCashOutActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
