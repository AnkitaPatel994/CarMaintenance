package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddVehicleActivity extends AppCompatActivity {

    EditText txtVehicleName,txtVehicleKilometer,txtVehicleNo;
    Button btnAddVehicle;
    String flag,v_id,v_name,v_no,v_kilometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtVehicleName = (EditText)findViewById(R.id.txtVehicleName);
        txtVehicleNo = (EditText)findViewById(R.id.txtVehicleNo);
        txtVehicleKilometer = (EditText)findViewById(R.id.txtVehicleKilometer);
        btnAddVehicle = (Button)findViewById(R.id.btnAddVehicle);

        flag = getIntent().getExtras().getString("flag");
        if(flag.equals("add"))
        {

        }
        else if(flag.equals("edit"))
        {
            btnAddVehicle.setText("Update Vehicle");
            v_id = getIntent().getExtras().getString("v_id");
            v_name = getIntent().getExtras().getString("v_name");
            v_no = getIntent().getExtras().getString("v_no");
            v_kilometer = getIntent().getExtras().getString("v_kilometer");

            txtVehicleName.setText(v_name);
            txtVehicleNo.setText(v_no);
            txtVehicleKilometer.setText(v_kilometer);

        }

        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag.equals("add"))
                {
                    GetAddvehicle addvehicle = new GetAddvehicle();
                    addvehicle.execute();
                }
                else if(flag.equals("edit"))
                {
                    GetEditvehicle editvehicle = new GetEditvehicle();
                    editvehicle.execute();
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

    private class GetAddvehicle extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddVehicleActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("v_name",txtVehicleName.getText().toString());
                joUser.put("v_no",txtVehicleNo.getText().toString());
                joUser.put("v_kilometer",txtVehicleKilometer.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"addvehicle.php",joUser.toString());
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
                Intent i = new Intent(AddVehicleActivity.this,VehicleActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddVehicleActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetEditvehicle extends AsyncTask<String,Void,String>  {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddVehicleActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("v_id",v_id);
                joUser.put("v_name",txtVehicleName.getText().toString());
                joUser.put("v_no",txtVehicleNo.getText().toString());
                joUser.put("v_kilometer",txtVehicleKilometer.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"editvehicle.php",joUser.toString());
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
                Intent i = new Intent(AddVehicleActivity.this,VehicleActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddVehicleActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
