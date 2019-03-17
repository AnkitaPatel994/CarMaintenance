package com.stimulustechnoweb.mrtaxi;

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

    EditText txtDDriverName,txtDLicenceno,txtDGST,txtDCommission;
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
        txtDLicenceno = (EditText)findViewById(R.id.txtDLicenceno);
        txtDGST = (EditText)findViewById(R.id.txtDGST);
        txtDCommission = (EditText)findViewById(R.id.txtDCommission);
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
            String d_licenceno = getIntent().getExtras().getString("d_licenceno");
            txtDLicenceno.setText(d_licenceno);
            String d_gst = getIntent().getExtras().getString("d_gst");
            txtDGST.setText(d_gst);
            String d_commission = getIntent().getExtras().getString("d_commission");
            txtDCommission.setText(d_commission);
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
                joUser.put("d_licenceno",txtDLicenceno.getText().toString());
                joUser.put("d_gst",txtDGST.getText().toString());
                joUser.put("d_commission",txtDCommission.getText().toString());

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
                joUser.put("d_licenceno",txtDLicenceno.getText().toString());
                joUser.put("d_gst",txtDGST.getText().toString());
                joUser.put("d_commission",txtDCommission.getText().toString());

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
