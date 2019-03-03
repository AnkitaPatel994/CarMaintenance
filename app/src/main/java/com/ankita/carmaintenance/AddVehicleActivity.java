package com.ankita.carmaintenance;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddVehicleActivity extends AppCompatActivity {

    EditText txtVehicleName,txtVehicleKilometer;
    Button btnAddVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        txtVehicleName = (EditText)findViewById(R.id.txtVehicleName);
        txtVehicleKilometer = (EditText)findViewById(R.id.txtVehicleKilometer);
        btnAddVehicle = (Button)findViewById(R.id.btnAddVehicle);

        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAddvehicle addvehicle = new GetAddvehicle();
                addvehicle.execute();
            }
        });

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
                joUser.put("v_kilometer",txtVehicleKilometer.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL,joUser.toString());
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
            if(status.equals("1"))
            {
                Toast.makeText(AddVehicleActivity.this,message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AddVehicleActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
