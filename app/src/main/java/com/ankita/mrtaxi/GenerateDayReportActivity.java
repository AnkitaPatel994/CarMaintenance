package com.ankita.mrtaxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class GenerateDayReportActivity extends AppCompatActivity {

    String c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_day_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        LinearLayout llOther = (LinearLayout)findViewById(R.id.llOther);
        TextView txtGDName = (TextView)findViewById(R.id.txtGDName);
        TextView txtGDDate = (TextView)findViewById(R.id.txtGDDate);
        TextView txtGDShift = (TextView)findViewById(R.id.txtGDShift);
        TextView txtGDClientName = (TextView)findViewById(R.id.txtGDClientName);
        TextView txtGDOther = (TextView)findViewById(R.id.txtGDOther);
        TextView txtGDCash = (TextView)findViewById(R.id.txtGDCash);
        TextView txtGDGastype = (TextView)findViewById(R.id.txtGDGastype);
        TextView txtGDGascash = (TextView)findViewById(R.id.txtGDGascash);
        TextView txtGDMaintenance = (TextView)findViewById(R.id.txtGDMaintenance);
        TextView txtGDCommission = (TextView)findViewById(R.id.txtGDCommission);
        TextView txtGDGST = (TextView)findViewById(R.id.txtGDGST);
        TextView txtGDCashleft = (TextView)findViewById(R.id.txtGDCashleft);
        TextView txtGDTotal = (TextView)findViewById(R.id.txtGDTotal);

        c_id = getIntent().getExtras().getString("c_id");

        String c_dshift = getIntent().getExtras().getString("c_dshift");
        txtGDShift.setText(c_dshift);

        String d_name = getIntent().getExtras().getString("d_name");
        txtGDName.setText(d_name);

        String client_name = getIntent().getExtras().getString("client_name");
        txtGDClientName.setText(client_name);

        String c_other = getIntent().getExtras().getString("c_other");
        if(c_other.equals(""))
        {
            llOther.setVisibility(View.GONE);
        }
        txtGDOther.setText(c_other);

        String c_cash = getIntent().getExtras().getString("c_cash");
        txtGDCash.setText(c_cash);

        String c_gastype = getIntent().getExtras().getString("c_gastype");
        txtGDGastype.setText("Gas "+c_gastype+" :");

        String c_gascash = getIntent().getExtras().getString("c_gascash");
        txtGDGascash.setText(c_gascash);

        String c_maintenance = getIntent().getExtras().getString("c_maintenance");
        txtGDMaintenance.setText(c_maintenance);

        String c_commission = getIntent().getExtras().getString("c_commission");
        txtGDCommission.setText(c_commission);

        String c_gst = getIntent().getExtras().getString("c_gst");
        txtGDGST.setText(c_gst);

        String c_cashleft = getIntent().getExtras().getString("c_cashleft");
        txtGDCashleft.setText(c_cashleft);

        String c_total = getIntent().getExtras().getString("c_total");
        txtGDTotal.setText(c_total);

        String c_date = getIntent().getExtras().getString("c_date");
        txtGDDate.setText(c_date);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        if (id == R.id.menu_delete)
        {
            /*Intent i = new Intent(getApplicationContext(),ClientActivity.class);
            startActivity(i);*/
            GetDeleteReport deleteReport = new GetDeleteReport();
            deleteReport.execute();
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetDeleteReport extends AsyncTask<String,Void,String> {
        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(GenerateDayReportActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("c_id",c_id);

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(MainActivity.BASE_URL+"deleteReport.php",joUser.toString());
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
                Intent i = new Intent(GenerateDayReportActivity.this,HomeActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(GenerateDayReportActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
