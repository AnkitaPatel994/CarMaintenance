package com.ankita.carmaintenance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.util.HashMap;

public class AddOilChangeActivity extends AppCompatActivity {

    Spinner spVehicleName;
    EditText txtOilVehicleKilometer,txtVehicleOilCost,txtOilVehicleNameNo;
    Button btnAddOil;
    String VehicleId,flag,o_id;
    ArrayList<String> VehicleIdList = new ArrayList<>();
    ArrayList<String> VehicleNameNoList = new ArrayList<>();
    ArrayList<String> VehiclekmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oil_change);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spVehicleName = (Spinner)findViewById(R.id.spVehicleName);
        txtOilVehicleKilometer = (EditText) findViewById(R.id.txtOilVehicleKilometer);
        txtVehicleOilCost = (EditText) findViewById(R.id.txtVehicleOilCost);
        txtOilVehicleNameNo = (EditText) findViewById(R.id.txtOilVehicleNameNo);
        btnAddOil = (Button) findViewById(R.id.btnAddOil);

        flag = getIntent().getExtras().getString("flag");
        if(flag.equals("add"))
        {
            txtOilVehicleNameNo.setVisibility(View.GONE);
            spVehicleName.setVisibility(View.VISIBLE);
        }
        else if(flag.equals("edit"))
        {
            btnAddOil.setText("update");
            txtOilVehicleNameNo.setVisibility(View.VISIBLE);
            spVehicleName.setVisibility(View.GONE);
            o_id = getIntent().getExtras().getString("o_id");
            String v_name = getIntent().getExtras().getString("v_name");
            String v_no = getIntent().getExtras().getString("v_no");
            txtOilVehicleNameNo.setText(v_name+" - "+v_no);
            String v_kilometer = getIntent().getExtras().getString("v_kilometer");
            txtOilVehicleKilometer.setText(v_kilometer);
            String o_cost = getIntent().getExtras().getString("o_cost");
            txtVehicleOilCost.setText(o_cost);
        }

        GetVehicleList vehicleList = new GetVehicleList();
        vehicleList.execute();

        spVehicleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int position_no = spVehicleName.getSelectedItemPosition();
                VehicleId = VehicleIdList.get(position_no);
                String Vehiclekm = VehiclekmList.get(position_no);
                txtOilVehicleKilometer.setText(Vehiclekm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("add"))
                {
                    GetAddOilChangeList addOilChangeList = new GetAddOilChangeList();
                    addOilChangeList.execute();
                }
                else if(flag.equals("edit"))
                {
                    GetEditOilChangeList editOilChangeList = new GetEditOilChangeList();
                    editOilChangeList.execute();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetVehicleList extends AsyncTask<String,Void,String> {

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
                        String v_kilometer =jo.getString("v_kilometer");

                        VehicleIdList.add(v_id);
                        VehicleNameNoList.add(v_name+" - "+v_no);
                        VehiclekmList.add(v_kilometer);
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
                spVehicleName.setAdapter(spinnerArrayAdapter);
            }
            else
            {
                Toast.makeText(AddOilChangeActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetAddOilChangeList extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddOilChangeActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("o_v_id",VehicleId);
                joUser.put("o_cost",txtVehicleOilCost.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"addoilchange.php",joUser.toString());
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
                Intent i = new Intent(AddOilChangeActivity.this,OilChangeActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddOilChangeActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetEditOilChangeList extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddOilChangeActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("o_id",o_id);
                joUser.put("o_cost",txtVehicleOilCost.getText().toString());
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"editoilchange.php",joUser.toString());
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
                Intent i = new Intent(AddOilChangeActivity.this,OilChangeActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddOilChangeActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
