package com.ankita.mrtaxi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddDriverActivity extends AppCompatActivity {

    EditText txtDDriverName,txtDCash,txtDMedical,txtDKidsFirst,txtDSocialServices,txtDPulpMill,txtDOSBMill,txtDNamSaskMill,txtDDetox,txtDSGI;
    Button btnDAddSubmit,btnDDelete;
    String flag,d_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtDDriverName = (EditText)findViewById(R.id.txtDDriverName);
        txtDCash = (EditText)findViewById(R.id.txtDCash);
        txtDMedical = (EditText)findViewById(R.id.txtDMedical);
        txtDKidsFirst = (EditText)findViewById(R.id.txtDKidsFirst);
        txtDSocialServices = (EditText)findViewById(R.id.txtDSocialServices);
        txtDPulpMill = (EditText)findViewById(R.id.txtDPulpMill);
        txtDOSBMill = (EditText)findViewById(R.id.txtDOSBMill);
        txtDNamSaskMill = (EditText)findViewById(R.id.txtDNamSaskMill);
        txtDDetox = (EditText)findViewById(R.id.txtDDetox);
        txtDSGI = (EditText)findViewById(R.id.txtDSGI);
        btnDAddSubmit = (Button) findViewById(R.id.btnDAddSubmit);
        btnDDelete = (Button) findViewById(R.id.btnDDelete);

        flag = getIntent().getExtras().getString("flag");
        if(flag.equals("add"))
        {
            btnDDelete.setVisibility(View.GONE);
        }
        else if(flag.equals("edit"))
        {
            btnDAddSubmit.setText("update");
            btnDDelete.setText("Delete");
            btnDDelete.setVisibility(View.VISIBLE);
            d_id = getIntent().getExtras().getString("d_id");
            String d_name = getIntent().getExtras().getString("d_name");
            txtDDriverName.setText(d_name);
            String d_cash = getIntent().getExtras().getString("d_cash");
            txtDCash.setText(d_cash);
            String d_medical = getIntent().getExtras().getString("d_medical");
            txtDMedical.setText(d_medical);
            String d_kidsfirst = getIntent().getExtras().getString("d_kidsfirst");
            txtDKidsFirst.setText(d_kidsfirst);
            String d_socialservices = getIntent().getExtras().getString("d_socialservices");
            txtDSocialServices.setText(d_socialservices);
            String d_pulpmill = getIntent().getExtras().getString("d_pulpmill");
            txtDPulpMill.setText(d_pulpmill);
            String d_osbmill = getIntent().getExtras().getString("d_osbmill");
            txtDOSBMill.setText(d_osbmill);
            String d_namsaskmill = getIntent().getExtras().getString("d_namsaskmill");
            txtDNamSaskMill.setText(d_namsaskmill);
            String d_detox = getIntent().getExtras().getString("d_detox");
            txtDDetox.setText(d_detox);
            String d_sgi = getIntent().getExtras().getString("d_sgi");
            txtDSGI.setText(d_sgi);
        }

        btnDAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag.equals("add"))
                {
                    GetAddDriver addDriver = new GetAddDriver();
                    addDriver.execute();
                }
                else if(flag.equals("edit"))
                {
                    GetEditDriver editDriver = new GetEditDriver();
                    editDriver.execute();
                }
            }
        });

        btnDDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AddDriverActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.delete_item_dialog);
                dialog.setCancelable(true);

                LinearLayout llDDDialog = (LinearLayout) dialog.findViewById(R.id.llDDDialog);
                TextView txtDDCancel = (TextView)dialog.findViewById(R.id.txtDDCancel);
                TextView txtDDDelete = (TextView)dialog.findViewById(R.id.txtDDDelete);

                llDDDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtDDCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtDDDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetDeleteDriver deleteDriver = new GetDeleteDriver();
                        deleteDriver.execute();
                    }
                });

                dialog.show();

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetAddDriver extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddDriverActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("d_name",txtDDriverName.getText().toString());
                joUser.put("d_cash",txtDCash.getText().toString());
                joUser.put("d_medical",txtDMedical.getText().toString());
                joUser.put("d_kidsfirst",txtDKidsFirst.getText().toString());
                joUser.put("d_socialservices",txtDSocialServices.getText().toString());
                joUser.put("d_pulpmill",txtDPulpMill.getText().toString());
                joUser.put("d_osbmill",txtDOSBMill.getText().toString());
                joUser.put("d_namsaskmill",txtDNamSaskMill.getText().toString());
                joUser.put("d_detox",txtDDetox.getText().toString());
                joUser.put("d_sgi",txtDSGI.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"adddriver.php",joUser.toString());
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
                Intent i = new Intent(AddDriverActivity.this,DriverActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddDriverActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetEditDriver extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddDriverActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("d_id",d_id);
                joUser.put("d_name",txtDDriverName.getText().toString());
                joUser.put("d_cash",txtDCash.getText().toString());
                joUser.put("d_medical",txtDMedical.getText().toString());
                joUser.put("d_kidsfirst",txtDKidsFirst.getText().toString());
                joUser.put("d_socialservices",txtDSocialServices.getText().toString());
                joUser.put("d_pulpmill",txtDPulpMill.getText().toString());
                joUser.put("d_osbmill",txtDOSBMill.getText().toString());
                joUser.put("d_namsaskmill",txtDNamSaskMill.getText().toString());
                joUser.put("d_detox",txtDDetox.getText().toString());
                joUser.put("d_sgi",txtDSGI.getText().toString());

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"editdriver.php",joUser.toString());
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
                Toast.makeText(AddDriverActivity.this,message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddDriverActivity.this,DriverActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddDriverActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetDeleteDriver extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AddDriverActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("d_id",d_id);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"deletedriver.php",joUser.toString());
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
                Toast.makeText(AddDriverActivity.this,message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddDriverActivity.this,DriverActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(AddDriverActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
